package me.exz.omniocular.handler;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.util.HashSet;

public class JSHandler {
    //TODO: add inventory/item name support
    //TODO: add translation support
    //TODO: add special characters support
    public static ScriptEngineManager manager =new ScriptEngineManager(null);
    public static ScriptEngine engine=manager.getEngineByName("javascript");
    public static HashSet<String> scriptSet=new HashSet<String>();

}
