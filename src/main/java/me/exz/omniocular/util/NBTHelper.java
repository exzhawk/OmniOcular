package me.exz.omniocular.util;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@SuppressWarnings({"unchecked", "CanBeFinal"})
public class NBTHelper {
    public static LoadingCache<Integer, NBTTagCompound> NBTCache = CacheBuilder.newBuilder().maximumSize(1000).build(
            new CacheLoader<Integer, NBTTagCompound>() {
                @Override
                public NBTTagCompound load(Integer key) throws Exception {
                    return new NBTTagCompound();
                }
            }
    );
    static Gson gson = new GsonBuilder().registerTypeHierarchyAdapter(NBTBase.class, new NBTSerializer()).create();

    public static String NBT2json(NBTBase n) {
        try {
            return gson.toJson(n);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "__ERROR__";
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

