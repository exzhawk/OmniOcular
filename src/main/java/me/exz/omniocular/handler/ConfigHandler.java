package me.exz.omniocular.handler;

import cpw.mods.fml.common.Loader;
import me.exz.omniocular.OmniOcular;
import me.exz.omniocular.reference.Reference;
import me.exz.omniocular.util.LogHelper;
import org.apache.commons.io.Charsets;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@SuppressWarnings("CanBeFinal")
public class ConfigHandler {
    public static File minecraftConfigDirectory;
    public static String mergedConfig = "";
    private static File configDir;
    public static Map<Pattern, Node> entityPattern = new HashMap<Pattern, Node>();
    public static Map<Pattern, Node> tileEntityPattern = new HashMap<Pattern, Node>();
    public static Map<Pattern, Node> tooltipPattern = new HashMap<Pattern, Node>();
    public static Map<String, String> settingList = new HashMap<String, String>();

    public static void initConfigFiles() {
        configDir = new File(minecraftConfigDirectory, Reference.MOD_ID);
        if (!configDir.exists()) {
            if (!configDir.mkdir()) {
                LogHelper.fatal("Can't create config folder");
            } else {
                LogHelper.info("Config folder created");
            }
        }

    }

    public static void releasePreConfigFiles() throws IOException {
        Pattern p = Pattern.compile("[\\\\/:*?\"<>|]");
        File jar = new File(OmniOcular.class.getProtectionDomain().getCodeSource().getLocation().getPath());
        JarFile jarFile = new JarFile(jar);
        final Enumeration<JarEntry> entries = jarFile.entries(); //gives ALL entries in jar
        while (entries.hasMoreElements()) {
            final String name = entries.nextElement().getName();
            if (name.startsWith("assets/omniocular/config/")) { //filter according to the path
                LogHelper.info(name);
            }
        }
        List<String> configList = IOUtils.readLines(OmniOcular.class.getClassLoader().getResourceAsStream("assets/omniocular/config"), Charsets.UTF_8);
        Set<String> modList = Loader.instance().getIndexedModList().keySet();
        for (String configFileNamet : configList) {
            System.out.println(configFileNamet);
        }

        for (String configFileName : configList) {
            for (String modID : modList) {
                Matcher m = p.matcher(modID);
                if (m.replaceAll("").equals(StringUtils.substring(configFileName, 0, -4)) || configFileName.equals("minecraft")) {
                    File targetFile = new File(configDir, configFileName);
                    if (!targetFile.exists()) {
//                    ResourceLocation resourceLocation = new ResourceLocation(Reference.MOD_ID.toLowerCase(), "config/" + configFileName);
//                    IResource resource = Minecraft.getMinecraft().getResourceManager().getResource(resourceLocation);
//                    FileUtils.copyInputStreamToFile(resource.getInputStream(), targetFile);
                        InputStream resource = OmniOcular.class.getClassLoader().getResourceAsStream("assets/omniocular/config/" + configFileName);
                        FileUtils.copyInputStreamToFile(resource, targetFile);
                        LogHelper.info("Release pre-config file : " + configFileName);
                    }
                }
            }
        }
    }

    public static void mergeConfig() {
        mergedConfig = "";
        File configDir = new File(minecraftConfigDirectory, Reference.MOD_ID);
        File[] configFiles = configDir.listFiles();
        if (configFiles != null) {
            for (File configFile : configFiles) {
                if (configFile.isFile()) {
                    try {
                        List<String> lines = Files.readAllLines(configFile.toPath(), Charset.forName("UTF-8"));
                        for (String line : lines) {
                            mergedConfig += line;
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        mergedConfig = "<root>" + mergedConfig + "</root>";
    }

    public static void parseConfigFiles() {
//      System.out.println(mergedConfig);
        try {
            JSHandler.initEngine();
            entityPattern.clear();
            tileEntityPattern.clear();
            tooltipPattern.clear();
            settingList.clear();
            JSHandler.scriptSet.clear();
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(new InputSource(new StringReader(mergedConfig)));
            doc.getDocumentElement().normalize();
            Element root = doc.getDocumentElement();
            NodeList ooList = root.getElementsByTagName("oo");
            for (int i = 0; i < ooList.getLength(); i++) {
                NodeList entityList = ((Element) ooList.item(i)).getElementsByTagName("entity");
                for (int j = 0; j < entityList.getLength(); j++) {
                    Node node = entityList.item(j);
                    entityPattern.put(Pattern.compile(node.getAttributes().getNamedItem("id").getTextContent()), node);
                }
                NodeList tileEntityList = ((Element) ooList.item(i)).getElementsByTagName("tileentity");
                for (int j = 0; j < tileEntityList.getLength(); j++) {
                    Node node = tileEntityList.item(j);
                    tileEntityPattern.put(Pattern.compile(node.getAttributes().getNamedItem("id").getTextContent()), node);
                }
                NodeList tooltipList = ((Element) ooList.item(i)).getElementsByTagName("tooltip");
                for (int j = 0; j < tooltipList.getLength(); j++) {
                    Node node = tooltipList.item(j);
                    tooltipPattern.put(Pattern.compile(node.getAttributes().getNamedItem("id").getTextContent()), node);
                }
                NodeList initList = ((Element) ooList.item(i)).getElementsByTagName("init");
                for (int j = 0; j < initList.getLength(); j++) {
                    Node node = initList.item(j);
                    JSHandler.engine.eval(node.getTextContent());
                }
                NodeList configList = ((Element) ooList.item(i)).getElementsByTagName("setting");
                for (int j = 0; j < configList.getLength(); j++) {
                    Node node = configList.item(j);
                    String settingText = node.getTextContent();
                    try {
                        String settingResult = JSHandler.engine.eval(settingText.trim()).toString();
                        settingList.put(node.getAttributes().getNamedItem("id").getTextContent(), settingResult);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}