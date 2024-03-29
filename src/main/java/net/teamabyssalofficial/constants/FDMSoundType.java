package net.teamabyssalofficial.constants;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.block.SoundType;
import net.teamabyssalofficial.registry.SoundRegistry;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public class FDMSoundType {
    public static final SoundType HIVE_SOUNDS = new SoundType(1.0F, 1.0F, SoundRegistry.HIVE_SOUNDS.get(), SoundRegistry.HIVE_SOUNDS.get(), SoundRegistry.HIVE_SOUNDS.get(), SoundRegistry.HIVE_SOUNDS.get(), SoundRegistry.HIVE_SOUNDS.get());

}
