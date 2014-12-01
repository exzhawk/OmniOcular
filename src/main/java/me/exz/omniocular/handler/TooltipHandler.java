package me.exz.omniocular.handler;

import codechicken.nei.guihook.GuiContainerManager;
import codechicken.nei.guihook.IContainerTooltipHandler;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import java.util.List;

public class TooltipHandler implements IContainerTooltipHandler {

    @Override
    public List<String> handleTooltip(GuiContainer guiContainer, int i, int i2, List<String> strings) {
        return strings;
    }

    @Override
    public List<String> handleItemDisplayName(GuiContainer guiContainer, ItemStack itemStack, List<String> strings) {

        return strings;
    }

    @Override
    public List<String> handleItemTooltip(GuiContainer guiContainer, ItemStack itemStack, int i, int i2, List<String> currenttip) {
        if (guiContainer != null && GuiContainerManager.shouldShowTooltip(guiContainer) && itemStack != null) {
            NBTTagCompound n = itemStack.getTagCompound();

            //accessor.getTileEntity().writeToNBT(n);
            if (n != null) {
                currenttip.addAll(1, JSHandler.getBody(ConfigHandler.tooltipPattern, n, Item.itemRegistry.getNameForObject(itemStack.getItem()),guiContainer.mc.thePlayer));

            }
        }
        return currenttip;
    }
}
