package me.exz.omniocular.handler;

import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import mcp.mobius.waila.api.IWailaDataProvider;
import mcp.mobius.waila.api.IWailaRegistrar;
import me.exz.omniocular.util.NBTHelper;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.script.Invocable;
import javax.script.ScriptException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TileEntityHandler implements IWailaDataProvider {
    private int lastHash;

    @SuppressWarnings("UnusedDeclaration")
    public static void callbackRegister(IWailaRegistrar registrar) {
        TileEntityHandler instance = new TileEntityHandler();
        registrar.registerSyncedNBTKey("*", Block.class);
        registrar.registerBodyProvider(instance, Block.class);

    }

    @Override
    public ItemStack getWailaStack(IWailaDataAccessor accessor, IWailaConfigHandler config) {
        return null;
    }

    @Override
    public List<String> getWailaHead(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config) {
        return currenttip;
        //TODO :allow modify waila head part 
    }

    @Override
    public List<String> getWailaBody(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config) {
        NBTTagCompound n = accessor.getNBTData();
        if (n != null) {
            if (n.hashCode() != lastHash) {
                lastHash = n.hashCode();
                ConfigHandler.lastTips.clear();
                //LogHelper.info(NBTHelper.NBT2json(n));
                try {
                    String json = "var nbt=" + NBTHelper.NBT2json(n) + ";";
                    JSHandler.engine.eval(json);
                } catch (ScriptException e) {
                    e.printStackTrace();
                }
                //TODO: Get n getstring(id), search in index return index.
                for (Node entry : ConfigHandler.tileEntityConfigList) {
                    Pattern pattern = Pattern.compile(entry.getAttributes().getNamedItem("id").getTextContent());
                    Matcher matcher = pattern.matcher(n.getString("id"));
                    if (matcher.matches()) {
                        Element item = (Element) entry;
                        if (item.getElementsByTagName("head").getLength() > 0) {
                            Node head = item.getElementsByTagName("head").item(0);
                        }
                        if (item.getElementsByTagName("line").getLength() > 0) {
                            String tip;
                            NodeList lines = item.getElementsByTagName("line");
                            for (int i = 0; i < lines.getLength(); i++) {
                                Node line = lines.item(i);
                                tip = "";
                                if (line.getAttributes().getNamedItem("displayname") != null) {
                                    tip += line.getAttributes().getNamedItem("displayname").getTextContent();
                                }
                                String functionContent = line.getTextContent();
                                String hash = "S" + NBTHelper.MD5(functionContent);
                                if (!JSHandler.scriptSet.contains(hash)) {
                                    JSHandler.scriptSet.add(hash);
                                    String script = "function " + hash + "()" + "{" + functionContent + "}";
                                    try {
                                        JSHandler.engine.eval(script);
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                                Invocable invoke = (Invocable) JSHandler.engine;
                                try {
                                    tip += String.valueOf(invoke.invokeFunction(hash, ""));
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                currenttip.add(tip);
                                ConfigHandler.lastTips.add(tip);
                            }
                        }
                    }
                }
            } else {
                currenttip.addAll(ConfigHandler.lastTips);
            }
        }
        return currenttip;
    }

    @Override
    public List<String> getWailaTail(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config) {
        return currenttip;
    }
}