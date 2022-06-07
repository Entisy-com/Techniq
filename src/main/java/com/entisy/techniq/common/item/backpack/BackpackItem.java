package com.entisy.techniq.common.item.backpack;

import com.entisy.techniq.Techniq;
import com.entisy.techniq.common.item.SimpleItemHandler;
import com.entisy.techniq.common.item.energy.EnergyItem;
import net.minecraft.client.renderer.texture.ITickable;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;
import net.minecraftforge.items.IItemHandler;

import javax.annotation.Nullable;

public class BackpackItem extends EnergyItem implements ITickable, INamedContainerProvider, IInventory {

    private static final int slots = 36;
    private static NonNullList<ItemStack> items;
    private static SimpleItemHandler inventory;
    private static boolean dirty = false;
    private static ItemStack stack;

    public BackpackItem() {
        super(3000);
        items = NonNullList.withSize(slots, ItemStack.EMPTY);
        inventory = new SimpleItemHandler(slots);
    }

    @Override
    public boolean isChargable() {
        return true;
    }

    @Override
    public ActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        stack = player.getItemInHand(hand);

        load(stack.getOrCreateTag());

        if (world != null && !world.isClientSide()) {
            NetworkHooks.openGui((ServerPlayerEntity) player, this);
        }

        return ActionResult.success(stack);
    }

    @Override
    public void tick() {

        if (dirty) {
            save(stack.getTag());
        }
    }

    @Override
    public ITextComponent getDisplayName() {
        return new TranslationTextComponent("container." + Techniq.MOD_ID + ".backpack");
    }

    @Nullable
    @Override
    public Container createMenu(int windowId, PlayerInventory items, PlayerEntity player) {
        return new BackpackContainer(windowId, items, this);
    }

    @Override
    public int getContainerSize() {
        return slots;
    }

    @Override
    public boolean isEmpty() {
        return inventory.isEmpty();
    }

    @Override
    public ItemStack getItem(int index) {
        return inventory.getStackInSlot(index);
    }

    @Override
    public ItemStack removeItem(int index, int amount) {
        return inventory.extractItem(index, amount, false);
    }

    @Override
    public ItemStack removeItemNoUpdate(int index) {
        return inventory.extractItem(index, inventory.getItem(index).getCount(), false);
    }

    @Override
    public void setItem(int index, ItemStack stack) {
        inventory.insertItem(index, stack, false);
    }

    @Override
    public void setChanged() {}

    @Override
    public boolean stillValid(PlayerEntity player) {
        return true;
    }

    @Override
    public void clearContent() {}

    public IItemHandler getInventory() {
        return inventory;
    }

    public void load(CompoundNBT nbt) {
        NonNullList<ItemStack> inv = NonNullList.withSize(inventory.getSlots(), ItemStack.EMPTY);
        ItemStackHelper.loadAllItems(nbt, inv);
        inventory.setNonNullList(inv);
    }

    public void save(CompoundNBT nbt) {
        ItemStackHelper.saveAllItems(nbt, inventory.toNonNullList());
    }

    public void dirty(boolean dirty) {
        this.dirty = dirty;
    }
}
