package net.teamabyssalofficial.constants;

import net.minecraft.util.Mth;
import net.minecraft.world.level.block.SoundType;
import net.teamabyssalofficial.registry.SoundRegistry;

/*
@Author = ASEStefan
@Purpose = To create your own block with custom sounds easier. It also works with items and different things.
 */

public class SoundTypeLibrary {

    // private final SoundEvent breakSound;
    // private final SoundEvent stepSound;
    // private final SoundEvent placeSound;
    // private final SoundEvent hitSound;
    // private final SoundEvent fallSound;

    /*
    Each sound represents the certain block triggers in order, the first is the break, the second is the step and so on...
     */

    private static final float volume = 1.0F;
    private static final float pitch = 1.05F;
    private float lerpingSound = Mth.lerp(volume * pitch, volume + 0, volume + Mth.PI * 2);
    public static final SoundType HIVE_SOUNDS = new SoundType(volume, pitch - 0.10F, SoundRegistry.HIVE_SOUNDS.get(), SoundRegistry.HIVE_SOUNDS.get(), SoundRegistry.HIVE_SOUNDS.get(), SoundRegistry.HIVE_SOUNDS.get(), SoundRegistry.HIVE_SOUNDS.get());


}
