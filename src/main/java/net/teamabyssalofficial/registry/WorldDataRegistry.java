package net.teamabyssalofficial.registry;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.saveddata.SavedData;
import net.teamabyssalofficial.config.FightOrDieMutationsConfig;
import net.teamabyssalofficial.fight_or_die.FightOrDieMutations;
import org.jetbrains.annotations.NotNull;

public class WorldDataRegistry extends SavedData {

    private int score;
    private int phase;
    private boolean cnt0 = false;
    private boolean cnt1 = false;
    private boolean cnt2 = false;
    private boolean cnt3 = false;
    private boolean cnt4 = false;
    private boolean cnt5 = false;
    private boolean metal_sound_event = false;
    private boolean violin_event = false;
    private boolean wood_croak_event = false;


    public boolean isCnt0() {
        setDirty();
        return cnt0;
    }

    public void setCnt0(boolean cnt0) {
        this.cnt0 = cnt0;
        setDirty();
    }
    public boolean isCnt1() {
        setDirty();
        return cnt1;
    }

    public void setCnt1(boolean cnt1) {
        this.cnt1 = cnt1;
        setDirty();
    }
    public boolean isCnt2() {
        setDirty();
        return cnt2;
    }

    public void setCnt2(boolean cnt2) {
        this.cnt2 = cnt2;
        setDirty();
    }
    public boolean isCnt3() {
        setDirty();
        return cnt3;
    }

    public void setCnt3(boolean cnt3) {
        this.cnt3 = cnt3;
        setDirty();
    }
    public boolean isCnt4() {
        setDirty();
        return cnt4;
    }

    public void setCnt4(boolean cnt4) {
        this.cnt4 = cnt4;
        setDirty();
    }
    public boolean isCnt5() {
        setDirty();
        return cnt5;
    }

    public void setCnt5(boolean cnt5) {
        this.cnt5 = cnt5;
        setDirty();
    }

    public boolean hadMetalEvent() {
        setDirty();
        return metal_sound_event;
    }

    public boolean hadViolinEvent() {
        setDirty();
        return violin_event;
    }

    public boolean hadWoodCroakEvent() {
        setDirty();
        return wood_croak_event;
    }
    public void setHadMetalEvent(boolean had) {
        metal_sound_event = had;
        setDirty();
    }
    public void setHadViolinEvent(boolean had) {
        violin_event = had;
        setDirty();
    }
    public void setHadWoodCroakEvent(boolean had) {
        wood_croak_event = had;
        setDirty();
    }

    public boolean canShowPhases() {
        return !this.isCnt0() || !this.isCnt1() || !this.isCnt2() || !this.isCnt3() || !this.isCnt4() || !this.isCnt5();
    }

    public int getScore() {
        setDirty();
        return score;
    }

    public void setScore(int score) {
        if (score < 0) {
            this.score = 0;
        }
        else {
            this.score = score;
        }
        setDirty();
    }


    public int getPhase() {
        setDirty();
        if (getScore() >= 0 && getScore() < FightOrDieMutationsConfig.DATAGEN.phase1_points.get()) {
            phase = 0;
        }
        else if (getScore() >= FightOrDieMutationsConfig.DATAGEN.phase1_points.get() && getScore() < FightOrDieMutationsConfig.DATAGEN.phase2_points.get()) {
            phase = 1;
        }
        else if (getScore() >= FightOrDieMutationsConfig.DATAGEN.phase2_points.get() && getScore() < FightOrDieMutationsConfig.DATAGEN.phase3_points.get()) {
            phase = 2;
        }
        else if (getScore() >= FightOrDieMutationsConfig.DATAGEN.phase3_points.get() && getScore() < FightOrDieMutationsConfig.DATAGEN.phase4_points.get()) {
            phase = 3;
        }
        else if (getScore() >= FightOrDieMutationsConfig.DATAGEN.phase4_points.get() && getScore() < FightOrDieMutationsConfig.DATAGEN.phase5_points.get()) {
            phase = 4;
        }
        else if (getScore() >= FightOrDieMutationsConfig.DATAGEN.phase5_points.get()) {
            phase = 5;
        }
        return phase;
    }

    public void setPhase(int phase) {
        if (phase == 0) {
            setScore(0);
        }
        else if (phase == 1) {
            setScore(FightOrDieMutationsConfig.DATAGEN.phase1_points.get());
        }
        else if (phase == 2) {
            setScore(FightOrDieMutationsConfig.DATAGEN.phase2_points.get());
        }
        else if (phase == 3) {
            setScore(FightOrDieMutationsConfig.DATAGEN.phase3_points.get());
        }
        else if (phase == 4) {
            setScore(FightOrDieMutationsConfig.DATAGEN.phase4_points.get());
        }
        else if (phase == 5) {
            setScore(FightOrDieMutationsConfig.DATAGEN.phase5_points.get());
        }
        else {
            setScore(0);
        }
        this.phase = phase;
        setDirty();
    }


    public void phaseHandlerEvent(ServerLevel world, ServerPlayer player) {
        if (getPhase() == 0 && !cnt0) {
            cnt0 = true;
            player.sendSystemMessage(Component.literal("0"));
            player.level().playSound((Player) null, player.blockPosition(), SoundRegistry.PHASE0.get(), SoundSource.MASTER, 1.2F, 1.0F);

        }
        else if (getPhase() == 1 && !cnt1) {
            cnt1 = true;
            player.sendSystemMessage(Component.literal("1"));
            player.level().playSound((Player) null, player.blockPosition(), SoundRegistry.PHASE1.get(), SoundSource.MASTER, 1.6F, 1.0F);

        }
        else if (getPhase() == 2 && !cnt2) {
            cnt2 = true;
            player.sendSystemMessage(Component.literal("2"));
            player.level().playSound((Player) null, player.blockPosition(), SoundRegistry.PHASE2.get(), SoundSource.MASTER, 1.6F, 1.0F);

        }
        else if (getPhase() == 3 && !cnt3) {
            cnt3 = true;
            player.sendSystemMessage(Component.literal("3"));
            player.level().playSound((Player) null, player.blockPosition(), SoundRegistry.PHASE3.get(), SoundSource.MASTER, 1.2F, 1.0F);

        }
        else if (getPhase() == 4 && !cnt4) {
            cnt4 = true;
            player.sendSystemMessage(Component.literal("4"));
            player.level().playSound((Player) null, player.blockPosition(), SoundRegistry.PHASE4.get(), SoundSource.MASTER, 1.4F, 1.0F);

        }
        else if (getPhase() == 5 && !cnt5) {
            cnt5 = true;
            player.sendSystemMessage(Component.literal("5"));
            player.level().playSound((Player) null, player.blockPosition(), SoundRegistry.PHASE5.get(), SoundSource.MASTER, 1.4F, 1.0F);

        }
        this.setDirty();
    }


    @Override
    public @NotNull CompoundTag save(CompoundTag nbt) {
        nbt.putInt("score", this.score);
        nbt.putInt("phase", this.phase);
        nbt.putBoolean("cnt0", this.cnt0);
        nbt.putBoolean("cnt1", this.cnt1);
        nbt.putBoolean("cnt2", this.cnt2);
        nbt.putBoolean("cnt3", this.cnt3);
        nbt.putBoolean("cnt4", this.cnt4);
        nbt.putBoolean("cnt5", this.cnt5);
        nbt.putBoolean("metal_sound_event", this.metal_sound_event);
        nbt.putBoolean("violin_event", this.violin_event);
        nbt.putBoolean("wood_croak_event", this.wood_croak_event);
        return nbt;
    }

    public static WorldDataRegistry fromNBT(CompoundTag nbt){
        WorldDataRegistry worldDataRegistry = new WorldDataRegistry();
        worldDataRegistry.score = nbt.getInt("score");
        worldDataRegistry.phase = nbt.getInt("phase");
        worldDataRegistry.cnt0 = nbt.getBoolean("cnt0");
        worldDataRegistry.cnt1 = nbt.getBoolean("cnt1");
        worldDataRegistry.cnt2 = nbt.getBoolean("cnt2");
        worldDataRegistry.cnt3 = nbt.getBoolean("cnt3");
        worldDataRegistry.cnt4 = nbt.getBoolean("cnt4");
        worldDataRegistry.cnt5 = nbt.getBoolean("cnt5");
        worldDataRegistry.metal_sound_event = nbt.getBoolean("metal_sound_event");
        worldDataRegistry.violin_event = nbt.getBoolean("violin_event");
        worldDataRegistry.wood_croak_event = nbt.getBoolean("wood_croak_event");
        return worldDataRegistry;
    }

    public static WorldDataRegistry getWorldDataRegistry(ServerLevel world) {
        WorldDataRegistry WorldDataRegistry = world.getDataStorage().computeIfAbsent(net.teamabyssalofficial.registry.WorldDataRegistry::fromNBT, WorldDataRegistry::new, FightOrDieMutations.MODID);
        WorldDataRegistry.setDirty();
        return WorldDataRegistry;
    }




}
