package com.entisy.techniq.common.block;

import com.entisy.techniq.core.capabilities.energy.EnergyStorageImpl;
import com.entisy.techniq.core.capabilities.fluid.CapabilityFluid;
import com.entisy.techniq.core.capabilities.fluid.FluidStorageImpl;
import com.entisy.techniq.core.capabilities.fluid.IFluidStorage;
import com.entisy.techniq.core.init.TechniqConfig;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class MachineTileEntity extends TileEntity {

    public static final int maxEnergy = TechniqConfig.MAX_ENERGY.get();
    public static final int maxFluid = TechniqConfig.MAX_FLUID.get();

    public int maxEnergyReceive = 0;
    public int maxEnergyExtract = 0;
    public static int slots = 0;
    public final EnergyStorageImpl energyStorage;
    public final LazyOptional<IEnergyStorage> energy;
    public final FluidStorageImpl fluidStorage;
    public final LazyOptional<IFluidStorage> fluid;
    public ITextComponent name;
    public int currentSmeltTime = 0;
    public MachineBlockItemHandler inventory;
    public int currentEnergy = 0;
    public int currentFluid = 0;

    public MachineTileEntity(int slots, int maxReceive, int maxExtract, TileEntityType<?> type) {
        super(type);
        MachineTileEntity.slots = slots;
        this.maxEnergyReceive = maxReceive;
        this.maxEnergyExtract = maxExtract;
        inventory = new MachineBlockItemHandler(slots);
        energyStorage = createEnergy(maxEnergy);
        energy = LazyOptional.of(() -> energyStorage);
        fluidStorage = createFluid(maxFluid);
        fluid = LazyOptional.of(() -> fluidStorage);
    }

    @Override
    public void load(BlockState state, CompoundNBT nbt) {
        super.load(state, nbt);
        if (nbt.contains("CustomName", Constants.NBT.TAG_STRING)) {
            name = ITextComponent.Serializer.fromJson(nbt.getString("CustomName"));
        }
        NonNullList<ItemStack> inv = NonNullList.<ItemStack>withSize(this.inventory.getSlots(), ItemStack.EMPTY);
        ItemStackHelper.loadAllItems(nbt, inv);
        inventory.setNonNullList(inv);
        currentSmeltTime = nbt.getInt("CurrentSmeltTime");
        currentEnergy = nbt.getInt("EnergyStored");
        energyStorage.setEnergyDirectly(currentEnergy);
        currentFluid = nbt.getInt("FluidStored");
        fluidStorage.setFluidDirectly(currentFluid);
    }

    @Override
    public CompoundNBT save(CompoundNBT nbt) {
        super.save(nbt);
        if (name != null) {
            nbt.putString("CustomName", ITextComponent.Serializer.toJson(name));
        }
        ItemStackHelper.saveAllItems(nbt, inventory.toNonNullList());
        nbt.putInt("CurrentSmeltTime", currentSmeltTime);
        energy.ifPresent(iEnergyStorage -> {
            nbt.putInt("EnergyStored", iEnergyStorage.getEnergyStored());
        });
        fluid.ifPresent(iFluidStorage -> {
            nbt.putInt("FluidStored", iFluidStorage.getFluidStored());
        });
        return nbt;
    }

    @Nullable
    @Override
    public SUpdateTileEntityPacket getUpdatePacket() {
        CompoundNBT nbt = new CompoundNBT();
        save(nbt);
        return new SUpdateTileEntityPacket(getBlockPos(), 0, nbt);
    }

    @Override
    public CompoundNBT getUpdateTag() {
        CompoundNBT nbt = new CompoundNBT();
        save(nbt);
        return nbt;
    }

    public MachineBlockItemHandler getInventory() {
        return inventory;
    }

    private EnergyStorageImpl createEnergy(int capacity) {
        return new EnergyStorageImpl(capacity, maxEnergyReceive, maxEnergyExtract, this);
    }

    private FluidStorageImpl createFluid(int capacity) {
        return new FluidStorageImpl(capacity, maxEnergyReceive, maxEnergyExtract, this);
    }

    @Override
    public void onDataPacket(NetworkManager manager, SUpdateTileEntityPacket packet) {
        deserializeNBT(packet.getTag());
    }

    @Override
    public void handleUpdateTag(BlockState state, CompoundNBT nbt) {
        deserializeNBT(nbt);
    }

    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> capability, Direction side) {
        if (capability == CapabilityEnergy.ENERGY) {
            return energy.cast();
        } else if (capability == CapabilityFluid.FLUID) {
            return fluid.cast();
        }
        return super.getCapability(capability, side);
    }

    @Override
    protected void invalidateCaps() {
        super.invalidateCaps();
        energy.invalidate();
    }

    public static Set<IRecipe<?>> findRecipesByType(IRecipeType<?> typeIn, World world) {
        return world != null ? world.getRecipeManager().getRecipes().stream()
                .filter(recipe -> recipe.getType() == typeIn).collect(Collectors.toSet()) : Collections.emptySet();
    }

    @OnlyIn(Dist.CLIENT)
    public static Set<IRecipe<?>> findRecipesByType(IRecipeType<?> typeIn) {
        ClientWorld world = Minecraft.getInstance().level;
        return world != null ? world.getRecipeManager().getRecipes().stream()
                .filter(recipe -> recipe.getType() == typeIn).collect(Collectors.toSet()) : Collections.emptySet();
    }

    public static Set<ItemStack> getAllRecipeInputs(IRecipeType<?> typeIn, World worldIn) {
        Set<ItemStack> inputs = new HashSet<ItemStack>();
        Set<IRecipe<?>> recipes = findRecipesByType(typeIn, worldIn);
        for (IRecipe<?> recipe : recipes) {
            NonNullList<Ingredient> ingredients = recipe.getIngredients();
            ingredients.forEach(ingredient -> {
                for (ItemStack stack : ingredient.getItems()) {
                    inputs.add(stack);
                }
            });
        }
        return inputs;
    }
}
