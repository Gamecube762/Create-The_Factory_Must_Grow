package com.drmangotea.createindustry.base.creative_mode_tabs;

import java.util.Collection;

import com.drmangotea.createindustry.CreateTFMG;
import com.simibubi.create.compat.jei.CreateJEI;
import com.tterrag.registrate.util.entry.RegistryEntry;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.NonNullList;
import net.minecraft.core.Registry;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public abstract class TFMGCreativeModeTab extends CreativeModeTab {
    public TFMGCreativeModeTab(String id) {
        super(CreateTFMG.MOD_ID + "." + id);
    }

    @Override
    public void fillItemList(NonNullList<ItemStack> items) {
        addItems(items, true);
        addBlocks(items);
        addItems(items, false);
    }

    protected Collection<RegistryEntry<Item>> registeredItems() {
        return CreateTFMG.REGISTRATE.getAll(Registry.ITEM.key());
    }

    public void addBlocks(NonNullList<ItemStack> items) {
        for (RegistryEntry<Item> entry : registeredItems())
            if (entry.get() instanceof BlockItem blockItem)
                blockItem.fillItemCategory(this, items);
    }

    public void addItems(NonNullList<ItemStack> items, boolean specialItems) {
        ItemRenderer itemRenderer = Minecraft.getInstance()
                .getItemRenderer();

        for (RegistryEntry<Item> entry : registeredItems()) {
            Item item = entry.get();
            if (item instanceof BlockItem)
                continue;
            ItemStack stack = new ItemStack(item);
            BakedModel model = itemRenderer.getModel(stack, null, null, 0);
            if (model.isGui3d() == specialItems)
                item.fillItemCategory(this, items);
        }
    }
}
