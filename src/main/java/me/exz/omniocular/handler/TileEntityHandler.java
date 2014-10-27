package me.exz.omniocular.handler;

import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import mcp.mobius.waila.api.IWailaDataProvider;
import mcp.mobius.waila.api.IWailaRegistrar;
import me.exz.omniocular.util.LogHelper;
import me.exz.omniocular.util.NBTHelper;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import org.w3c.dom.Node;

import javax.script.ScriptException;
import java.util.List;

public class TileEntityHandler implements IWailaDataProvider {
    private int lastHash;

    @SuppressWarnings("UnusedDeclaration")
    public static void callbackRegister(IWailaRegistrar registrar) {
        TileEntityHandler instance = new TileEntityHandler();
        registrar.registerSyncedNBTKey("*", Block.class);
        registrar.registerBodyProvider(instance, Block.class);

    }

    @Override
    public ItemStack getWailaStack(IWailaDataAccessor accessor, IWailaConfigHandler config) {
        return null;
    }

    @Override
    public List<String> getWailaHead(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config) {
        return currenttip;
    }

    @Override
    public List<String> getWailaBody(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config) {
        if (accessor.getPlayer().getEntityWorld().getTotalWorldTime() % 10 == 0) {
            NBTTagCompound n = accessor.getNBTData();
            if (n != null) {
                if (n.hashCode() != lastHash) {
                    lastHash = n.hashCode();
                    //LogHelper.info(NBTHelper.NBT2json(n));
                    try {
                        JSHandler.engine.eval(NBTHelper.NBT2json(n));
                    } catch (ScriptException e) {
                        e.printStackTrace();
                    }
                    for (Node entry:ConfigHandler.tileEntityConfigList){

                    }
                }
            }
        }
        return currenttip;
    }

    @Override
    public List<String> getWailaTail(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config) {
        return currenttip;
    }
}
