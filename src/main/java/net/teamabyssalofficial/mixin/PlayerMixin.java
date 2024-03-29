package net.teamabyssalofficial.mixin;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.player.Player;
import net.teamabyssalofficial.constants.PlayerVariables;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Player.class)
public class PlayerMixin implements PlayerVariables {
    @Unique
    private SynchedEntityData dataTracker(){
        return ((Player) (Object) this).getEntityData();
    }
    @Unique
    private static final EntityDataAccessor<Integer> KILLS;
    @Inject(at = @At("HEAD"), method = "addAdditionalSaveData")
    private void addAdditionalSaveData(CompoundTag nbt, CallbackInfo ci) {
        nbt.putInt("KillData", this.IgetKills());
    }

    @Inject(at = @At("HEAD"), method = "readAdditionalSaveData")
    private void readAdditionalSaveData(CompoundTag nbt, CallbackInfo ci) {
        if (nbt.contains("KillData")){
            this.IsetKills(nbt.getInt("KillData"));
        }
    }

    @Inject(at = @At("HEAD"), method = "defineSynchedData")
    private void defineSynchedData(CallbackInfo ci){
        this.dataTracker().define(KILLS, 0);
    }
    @Override
    public void IsetKills(int i){
        this.dataTracker().set(KILLS, i);
    }

    @Override
    public int IgetKills(){
        return this.dataTracker().get(KILLS);
    }

    static {
        KILLS = SynchedEntityData.defineId(Player.class, EntityDataSerializers.INT);
    }
}
