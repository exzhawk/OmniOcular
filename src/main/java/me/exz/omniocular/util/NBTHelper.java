package me.exz.omniocular.util;

import cpw.mods.fml.relauncher.ReflectionHelper;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Map;

public class NBTHelper {
    public static String NBT2json(NBTBase n) {
        try {
            switch (n.getId()) {
                case 0:
                case 3:
                case 7:
                case 11:
                    return "\"" + n.toString() + "\"";
                case 1:
                case 2:
                case 4:
                case 5:
                case 6:
                    return "\"" + StringUtils.substring(n.toString(), 0, -1) + "\"";
                case 8:
                    return n.toString();
                case 9:
                    NBTTagList nl = (NBTTagList) n;
                    String s = "[";
                    int tagType = nl.func_150303_d();
                    for (int i = 1; i < nl.tagCount(); i++) {
                        switch (tagType) {
                            case 10:
                                s += NBT2json(nl.getCompoundTagAt(i));
                                break;
                            case 11:
                                s += Arrays.toString(nl.func_150306_c(i));
                                break;
                            case 6:
                                s += "\"" + String.valueOf(nl.func_150309_d(i)) + "\"";
                                break;
                            case 5:
                                s += "\"" + String.valueOf(nl.func_150308_e(i)) + "\"";
                                break;
                            case 8:
                                s += nl.getStringTagAt(i);
                            default:
                        }
                        s += ",";
                    }
                    s = StringUtils.substring(s, 0, -1);
                    s += "]";
                    return s;
                case 10:
                    NBTTagCompound nc = (NBTTagCompound) n;
                    String st = "{";
                    Map<String, NBTBase> tagMap = getMap(nc);
                    for (Map.Entry<String, NBTBase> entry : tagMap.entrySet()) {
                        st += "\"" + entry.getKey() + "\":";
                        st += NBT2json(entry.getValue());
                        st += ",";
                    }
                    st = StringUtils.substring(st, 0, -1);
                    st += "}";
                    return st;
                default:
                    return "__ERROR__";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }

    public static Map<String, NBTBase> getMap(NBTTagCompound tag) {
        //noinspection unchecked
        return (Map) ReflectionHelper.getPrivateValue(NBTTagCompound.class, tag, 1);
    }

}

