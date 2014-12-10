package me.exz.omniocular.handler;

import mcp.mobius.waila.api.IWailaFMPAccessor;
import net.minecraft.nbt.NBTTagCompound;

import java.util.List;

public class FMPHandler {

    @SuppressWarnings("UnusedDeclaration")
    public static List<String> getWailaBody(List<String> currenttip, IWailaFMPAccessor accessor) {
        NBTTagCompound n = accessor.getNBTData();
        //accessor.getTileEntity().writeToNBT(n);
        if (n != null) {
            currenttip.addAll(JSHandler.getBody(ConfigHandler.tileEntityPattern, n, n.getString("id"), accessor.getPlayer()));
        }
        return currenttip;
    }
}
