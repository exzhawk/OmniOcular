package me.exz.omniocular.handler;

import me.exz.omniocular.util.LogHelper;
import me.exz.omniocular.util.NBTHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.StatCollector;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@SuppressWarnings({"CanBeFinal", "UnusedDeclaration"})
public class JSHandler {
    private static final ScriptEngineManager manager = new ScriptEngineManager(null);
    private static ScriptEngine engine = manager.getEngineByName("javascript");
    public static HashSet<String> scriptSet = new HashSet<String>();
    private static List<String> lastTips = new ArrayList<String>();
    private static int lastHash;

    public static List<String> getBody(Map<Pattern, Node> patternMap, NBTTagCompound n, String id) {
        if (n.hashCode() != lastHash) {
            lastHash = n.hashCode();
            lastTips.clear();
            //LogHelper.info(NBTHelper.NBT2json(n));
            try {
                String json = "var nbt=" + NBTHelper.NBT2json(n) + ";";
                JSHandler.engine.eval(json);
            } catch (ScriptException e) {
                e.printStackTrace();
            }
            for (Map.Entry<Pattern, Node> entry : patternMap.entrySet()) {
                Matcher matcher = entry.getKey().matcher(id);
                if (matcher.matches()) {
                    Element item = (Element) entry.getValue();
//                    if (item.getElementsByTagName("head").getLength() > 0) {
//                        Node head = item.getElementsByTagName("head").item(0);
//                    }
                    if (item.getElementsByTagName("line").getLength() > 0) {
                        String tip;
                        NodeList lines = item.getElementsByTagName("line");
                        for (int i = 0; i < lines.getLength(); i++) {
                            Node line = lines.item(i);
                            tip = "";
                            //TODO: support translation in displayname
                            if (line.getAttributes().getNamedItem("displayname") != null && !line.getAttributes().getNamedItem("displayname").getTextContent().trim().isEmpty()) {
                                if (patternMap == ConfigHandler.tooltipPattern) {
                                    tip += "\u00A77" + line.getAttributes().getNamedItem("displayname").getTextContent() + ": \u00A7f";
                                } else {
                                    tip += line.getAttributes().getNamedItem("displayname").getTextContent() + "\u00A4\u00A4a\u00A4\u00A4b\u00A7f";
                                }
                            }
                            String functionContent = line.getTextContent();
                            String hash = "S" + NBTHelper.MD5(functionContent);
                            if (!JSHandler.scriptSet.contains(hash)) {
                                JSHandler.scriptSet.add(hash);
                                //TODO: automatic add "return" if no return found to simplify config file
                                String script = "function " + hash + "()" + "{" + functionContent + "}";
                                try {
                                    JSHandler.engine.eval(script);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                            Invocable invoke = (Invocable) JSHandler.engine;
                            try {
                                String r = String.valueOf(invoke.invokeFunction(hash, ""));
                                if (r.equals("__ERROR__")) {
                                    continue;
                                }
                                tip += String.valueOf(invoke.invokeFunction(hash, ""));
                            } catch (Exception e) {
                                continue;
                                //e.printStackTrace();
                            }
                            if (tip.equals("__ERROR__")) {
                                continue;
                            }
                            lastTips.add(tip);
                        }
                    }
                }
            }
        }
        return lastTips;
    }

    public static void initEngine() {
        setSpecialChar();
        /** java 8 work around */
        try {
            engine.eval("load(\"nashorn:mozilla_compat.js\");");
        } catch (ScriptException e) {
            //e.printStackTrace();
        }
        try {
            engine.eval("importClass(Packages.me.exz.omniocular.handler.JSHandler);");
            engine.eval("function translate(t){return Packages.me.exz.omniocular.handler.JSHandler.translate(t)}");
            engine.eval("function name(n){return Packages.me.exz.omniocular.handler.JSHandler.getDisplayName(n.hashCode)}");

        } catch (ScriptException e) {
            e.printStackTrace();
        }
    }

    public static String translate(String t) {
        return StatCollector.translateToLocal(t);
    }


    private static void setSpecialChar() {
        String MCStyle = "\u00A7";
        engine.put("BLACK", MCStyle + "0");
        engine.put("DBLUE", MCStyle + "1");
        engine.put("DGREEN", MCStyle + "2");
        engine.put("DAQUA", MCStyle + "3");
        engine.put("DRED", MCStyle + "4");
        engine.put("DPURPLE", MCStyle + "5");
        engine.put("GOLD", MCStyle + "6");
        engine.put("GRAY", MCStyle + "7");
        engine.put("DGRAY", MCStyle + "8");
        engine.put("BLUE", MCStyle + "9");
        engine.put("GREEN", MCStyle + "a");
        engine.put("AQUA", MCStyle + "b");
        engine.put("RED", MCStyle + "c");
        engine.put("LPURPLE", MCStyle + "d");
        engine.put("YELLOW", MCStyle + "e");
        engine.put("WHITE", MCStyle + "f");

        engine.put("OBF", MCStyle + "k");
        engine.put("BOLD", MCStyle + "l");
        engine.put("STRIKE", MCStyle + "m");
        engine.put("UNDER", MCStyle + "n");
        engine.put("ITALIC", MCStyle + "o");
        engine.put("RESET", MCStyle + "r");
        String WailaStyle = "\u00A4";
        String WailaIcon = "\u00A5";
        engine.put("TAB", WailaStyle + WailaStyle + "a");
        engine.put("ALIGNRIGHT", WailaStyle + WailaStyle + "b");
        engine.put("ALIGNCENTER", WailaStyle + WailaStyle + "c");
        engine.put("HEART", WailaStyle + WailaIcon + "a");
        engine.put("HHEART", WailaStyle + WailaIcon + "b");
        engine.put("EHEART", WailaStyle + WailaIcon + "c");
        LogHelper.info("Special Char loaded");
    }

    public static String getDisplayName(String hashCode) {
        try {
            NBTTagCompound nc = NBTHelper.mapNBT.get(Integer.valueOf(hashCode));
            ItemStack is = ItemStack.loadItemStackFromNBT(nc);
            return is.getDisplayName();
        } catch (Exception e) {


            return "__ERROR__";
        }
    }


}
