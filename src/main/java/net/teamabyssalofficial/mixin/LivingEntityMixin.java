package net.teamabyssalofficial.mixin;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.LivingEntity;
import net.teamabyssalofficial.constants.PossibleMutated;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public class LivingEntityMixin implements PossibleMutated {
    @Unique
    private SynchedEntityData dataTracker(){
        return ((LivingEntity) (Object) this).getEntityData();
    }
    @Unique
    private static final EntityDataAccessor<Integer> ASSIMILATION_PROGRESS;
    @Inject(at = @At("HEAD"), method = "addAdditionalSaveData")
    private void addAdditionalSaveData(CompoundTag nbt, CallbackInfo ci) {
        nbt.putInt("AssimilationProgress", this.IgetAssimilationProgress());
    }

    @Inject(at = @At("HEAD"), method = "readAdditionalSaveData")
    private void readAdditionalSaveData(CompoundTag nbt, CallbackInfo ci) {
        if (nbt.contains("AssimilationProgress")){
            this.IsetAssimilationProgress(nbt.getInt("AssimilationProgress"));
        }
    }

    @Inject(at = @At("HEAD"), method = "defineSynchedData")
    private void defineSynchedData(CallbackInfo ci){
        this.dataTracker().define(ASSIMILATION_PROGRESS, 0);
    }
    @Override
    public void IsetAssimilationProgress(int i){
        this.dataTracker().set(ASSIMILATION_PROGRESS, i);
    }

    @Override
    public int IgetAssimilationProgress(){
        return this.dataTracker().get(ASSIMILATION_PROGRESS);
    }

    static {
        ASSIMILATION_PROGRESS = SynchedEntityData.defineId(LivingEntity.class, EntityDataSerializers.INT);
    }

}
