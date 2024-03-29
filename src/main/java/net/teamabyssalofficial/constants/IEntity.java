package net.teamabyssalofficial.constants;

import net.minecraft.client.model.*;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Monster;

public class IEntity {

    public void coolEntitiesForAnimationPurposes() {
        IronGolemModel ironGolemModel;
        RavagerModel ravagerModel;
        WitchModel witchModel;
        WolfModel wolfModel;
        WardenModel wardenModel;
        SkeletonModel skeletonModel;

    }

    public static boolean isChangingPos(LivingEntity entity, float limbSwingAmount) {
        return !(limbSwingAmount > -0.15F && limbSwingAmount < 0.15F);
    }
    public static boolean isAggressive(Monster monster) {
        return monster.isAggressive();
    }
    public static boolean isHurt(LivingEntity entity) {
        return entity.hurtTime > 0;
    }
    public static boolean isSwinging(LivingEntity entity) {
        return entity.swinging;
    }
    public static boolean isAttacking(Monster entity) {
        return entity.attackAnim > 0;
    }
    public static boolean isDying(LivingEntity entity) {
        return entity.isDeadOrDying();
    }
    public static boolean isSwimming(LivingEntity entity) {
        return entity.isSwimming();
    }
    public static boolean isFlying(LivingEntity entity) {
        return !entity.onGround() && entity.isFallFlying();
    }
    public static boolean hasTarget(Monster monster) {
        return monster.getTarget() != null;
    }

}
