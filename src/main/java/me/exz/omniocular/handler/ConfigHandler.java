package me.exz.omniocular.handler;

import me.exz.omniocular.reference.Reference;
import me.exz.omniocular.util.LogHelper;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.List;

public class ConfigHandler {
    public static File minecraftConfigDirectory;
    public static String mergedConfig="";

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
        //TODO: merge config files in to one config string
        mergedConfig="";
        File configDir = new File(minecraftConfigDirectory, Reference.MOD_ID);
        File[] configFiles = configDir.listFiles();
        if (configFiles != null) {
            for (File configFile : configFiles) {
                if (configFile.isFile()) {
                    try {
                        List<String> lines = Files.readAllLines(configFile.toPath(), Charset.forName("UTF-8"));
                        for (String line: lines){
                            mergedConfig += line;
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public static void parseConfigFiles() {

    }
}
