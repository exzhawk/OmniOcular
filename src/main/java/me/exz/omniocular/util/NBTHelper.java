package me.exz.omniocular.util;

import cpw.mods.fml.relauncher.ReflectionHelper;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagByteArray;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import org.apache.commons.lang3.StringUtils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
@SuppressWarnings({"unchecked", "CanBeFinal"})
public class NBTHelper {
    public static Map<Integer, NBTTagCompound> mapNBT = new HashMap<Integer, NBTTagCompound>();

    public static String NBT2json(NBTBase n) {
        try {
            switch (n.getId()) {
                case 0:
                case 11:
                    return "\"" + n.toString() + "\"";
                case 1:
                case 2:
                case 4:
                case 5:
                case 6:
                    return StringUtils.substring(n.toString(), 0, -1);
                case 3:
                    return n.toString();
                case 8:
                    return "\"" + StringUtils.substring(n.toString(), 1, -1).replace("\\", "\\\\").replace("\"", "\\\"") + "\"";
                case 7:
                    return "\"" + Arrays.toString(((NBTTagByteArray) n).func_150292_c()) + "\"";
                case 9:
                    NBTTagList nl = (NBTTagList) n;
                    String s = "[";
                    int tagType = nl.func_150303_d();
                    for (int i = 0; i < nl.tagCount(); i++) {
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
                    if (s.endsWith(",")) {
                        s = StringUtils.substring(s, 0, -1);
                    }
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
                    int hashCode = nc.hashCode();
                    if(mapNBT.size()>1000){
                        mapNBT.clear();
                    }
                    if (!mapNBT.containsKey(hashCode)) {
                        mapNBT.put(hashCode, nc);
                    }
                    st += "hashCode:\"" + hashCode+"\"";
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

    private static Map<String, NBTBase> getMap(NBTTagCompound tag) {
        //noinspection unchecked
        return (Map) ReflectionHelper.getPrivateValue(NBTTagCompound.class, tag, 1);
    }

    public static String MD5(String string) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(string.getBytes());
            byte[] digest = md.digest();
            StringBuilder sb = new StringBuilder();
            for (byte b : digest) {
                sb.append(String.format("%02x", b & 0xff));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }
}

