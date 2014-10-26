package me.exz.omniocular.util;

import net.minecraft.nbt.NBTBase;
import org.apache.commons.lang3.StringUtils;

public class NBTHelper {
    //TODO: convert nbt into json string format
    public static String NBT2json(NBTBase n) {
        switch (n.getId()) {
            case 0:
            case 3:
            case 7:
            case 11:
                return "\""+n.toString()+"\"";
            case 1:
            case 2:
            case 4:
            case 5:
            case 6:
                return "\""+StringUtils.substring(n.toString(), 0, -1)+"\"";
            case 8:
                return n.toString();
            case 9:
                String s = "[";
            default:
                return "__ERROR__";

        }
    }
}