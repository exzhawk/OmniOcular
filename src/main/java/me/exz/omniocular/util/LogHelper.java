package me.exz.omniocular.util;

import cpw.mods.fml.common.FMLLog;
import me.exz.omniocular.reference.Reference;
import org.apache.logging.log4j.Level;

@SuppressWarnings("UnusedDeclaration")
public class LogHelper {
    private static void log(Level logLevel, Object object) {
        FMLLog.log(Reference.MOD_NAME, logLevel, "%s", String.valueOf(object));
    }

    public static void all(Object object) {
        log(Level.ALL, object);
    }

    public static void debug(Object object) {
        log(Level.DEBUG, object);
    }

    public static void error(Object object) {
        log(Level.ERROR, object);
    }

    public static void fatal(Object object) {
        log(Level.FATAL, object);
    }

    public static void info(Object object) {
        log(Level.INFO, object);
    }

    public static void off(Object object) {
        log(Level.OFF, object);
    }

    public static void trace(Object object) {
        log(Level.TRACE, object);
    }

    public static void warn(Object object) {
        log(Level.WARN, object);
    }
}
