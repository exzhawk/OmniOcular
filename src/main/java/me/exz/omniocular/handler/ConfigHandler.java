package me.exz.omniocular.handler;

import me.exz.omniocular.reference.Reference;
import me.exz.omniocular.util.LogHelper;

import java.io.File;

public class ConfigHandler {
    public static File minecraftConfigDirectory;
    public static String mergedConfig;

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

    public static void mergeConfig(String mergedConfig) {
        //TODO: merge config files in to one config string
    }

    public static void parseConfigFiles() {

    }
}
