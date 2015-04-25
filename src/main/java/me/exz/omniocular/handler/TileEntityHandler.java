package me.exz.omniocular.handler;

import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import mcp.mobius.waila.api.IWailaDataProvider;
import mcp.mobius.waila.api.IWailaRegistrar;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import java.util.List;

public class TileEntityHandler implements IWailaDataProvider {

    @SuppressWarnings("UnusedDeclaration")
    public static void callbackRegister(IWailaRegistrar registrar) {
        TileEntityHandler instance = new TileEntityHandler();
//        registrar.registerSyncedNBTKey("*", Block.class);
//        registrar.registerSyncedNBTKey("*", TileEntity.class);
//        registrar.registerBodyProvider(instance, Block.class);
        registrar.registerBodyProvider(instance, TileEntity.class);
        registrar.registerNBTProvider(instance, Block.class);
        registrar.registerNBTProvider(instance, TileEntity.class);

    }

    @Override
    public ItemStack getWailaStack(IWailaDataAccessor accessor, IWailaConfigHandler config) {
        return null;
    }

    @Override
    public List<String> getWailaHead(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config) {
        return currenttip;
    }
    //TODO workaround for drops / support drops
    @Override
    public List<String> getWailaBody(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config) {
        NBTTagCompound n = accessor.getNBTData();
        if (n != null) {
            currenttip.addAll(JSHandler.getBody(ConfigHandler.tileEntityPattern, n, n.getString("id"), accessor.getPlayer()));
        }
        return currenttip;
    }

    @Override
    public List<String> getWailaTail(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config) {
        return currenttip;
    }

    @Override
    public NBTTagCompound getNBTData(EntityPlayerMP player, TileEntity te, NBTTagCompound tag, World world, int x, int y, int z) {
        if (te != null)
            te.writeToNBT(tag);
        return tag;
    }
}