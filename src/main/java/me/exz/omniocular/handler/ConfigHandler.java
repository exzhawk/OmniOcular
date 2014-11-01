package me.exz.omniocular.handler;

import me.exz.omniocular.reference.Reference;
import me.exz.omniocular.util.LogHelper;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.io.StringReader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class ConfigHandler {
    public static File minecraftConfigDirectory;
    public static String mergedConfig = "";
    public static List<Node> entityConfigList= new ArrayList<Node>();
    public static List<Node> tileEntityConfigList= new ArrayList<Node>();
    public static List<Node> tooltipConfigList= new ArrayList<Node>();

    public static void initConfigFiles() {
        File configDir = new File(minecraftConfigDirectory, Reference.MOD_ID);
        if (!configDir.exists()) {
            if (!configDir.mkdir()) {
                LogHelper.fatal("Can't create config folder");
            }
        }
        LogHelper.info("Config folder created");
        if (!releasePreConfigFiles(configDir)) {
            LogHelper.error("Can't release pre-config files");
        }
        LogHelper.info("Pre-config files released");
    }

    public static boolean releasePreConfigFiles(File configDir) {
        //TODO: release pre-config files
        return false;
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
        mergedConfig="<root>"+mergedConfig+"</root>";
    }

    public static void parseConfigFiles() {
//      System.out.println(mergedConfig);
        try {
            entityConfigList.clear();
            tileEntityConfigList.clear();
            tooltipConfigList.clear();
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(new InputSource(new StringReader(mergedConfig)));
            doc.getDocumentElement().normalize();
            Element root = doc.getDocumentElement();
            NodeList ooList = root.getElementsByTagName("oo");
            for (int i = 0; i < ooList.getLength(); i++) {
                NodeList entityList = ((Element) ooList.item(i)).getElementsByTagName("entity");
                for (int j = 0; j < entityList.getLength(); j++) {
                    entityConfigList.add(entityList.item(j));
                }
                NodeList tileEntityList = ((Element) ooList.item(i)).getElementsByTagName("tileentity");
                for (int j = 0; j < tileEntityList.getLength(); j++) {
                    tileEntityConfigList.add(tileEntityList.item(j));
                }
                NodeList tooltipList = ((Element) ooList.item(i)).getElementsByTagName("tooltip");
                for (int j = 0; j < tooltipList.getLength(); j++) {
                    tooltipConfigList.add(tooltipList.item(j));
                }
            }
//TODO: make a index of configuration list to boost efficiency
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}