package me.exz.omniocular.handler;

import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaEntityAccessor;
import mcp.mobius.waila.api.IWailaEntityProvider;
import mcp.mobius.waila.api.IWailaRegistrar;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

import java.util.List;

public class EntityHandler implements IWailaEntityProvider {
    @SuppressWarnings("UnusedDeclaration")
    public static void callbackRegister(IWailaRegistrar registrar) {
        EntityHandler instance = new EntityHandler();
//        registrar.registerSyncedNBTKey("*", Entity.class);
        registrar.registerBodyProvider(instance, Entity.class);
        registrar.registerNBTProvider(instance, Entity.class);

    }

    @Override
    public Entity getWailaOverride(IWailaEntityAccessor accessor, IWailaConfigHandler config) {
        return null;
    }

    @Override
    public List<String> getWailaHead(Entity entity, List<String> currenttip, IWailaEntityAccessor accessor, IWailaConfigHandler config) {
        return currenttip;
    }

    @Override
    public List<String> getWailaBody(Entity entity, List<String> currenttip, IWailaEntityAccessor accessor, IWailaConfigHandler config) {
        NBTTagCompound n = accessor.getNBTData();
        if (n != null) {
            currenttip.addAll(JSHandler.getBody(ConfigHandler.entityPattern, n, EntityList.getEntityString(accessor.getEntity()), accessor.getPlayer()));
        }
        return currenttip;
    }

    @Override
    public List<String> getWailaTail(Entity entity, List<String> currenttip, IWailaEntityAccessor accessor, IWailaConfigHandler config) {
        return currenttip;
    }

    @Override
    public NBTTagCompound getNBTData(EntityPlayerMP player, Entity ent, NBTTagCompound tag, World world) {
        if (ent != null)
            ent.writeToNBT(tag);
        return tag;

    }
}
