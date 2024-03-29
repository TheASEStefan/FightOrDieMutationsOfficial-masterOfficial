package net.teamabyssalofficial.constants;

import net.minecraft.util.Mth;

/*
@Author = ASEStefan
@Name = Velocity and Probe Variables
@Calculator = ChatGPT
 */

public class IMathHelper {

    public static final float PI = 3.14F;
    public static final float DELTA = (PI * 3 * Mth.floor(PI)) / 4;
    public static final float OMEGA = DELTA - PI + Mth.clamp(10, PI, DELTA);
    public static final float LAMBDA = Mth.lerp(DELTA, PI, OMEGA) + (float) Math.atan(DELTA - PI);
    public static final float SENTINEL  = Math.abs(OMEGA - DELTA) + ((float) PI / 2 * LAMBDA) - (float) (LAMBDA / 4);
    public static final float RELAT  = (Math.min(LAMBDA, SENTINEL) + Math.max(LAMBDA, SENTINEL)) / 6 + (PI * OMEGA - Mth.ceil(OMEGA)) / 3;
    public static final float HEX  = ((PI + DELTA + OMEGA) * 3) / Mth.floor(RELAT);
    public static final float SIGMA  = ((LAMBDA - OMEGA) * 3) + Math.abs(SENTINEL - LAMBDA) + Mth.lerp(DELTA, LAMBDA, RELAT) - HEX / 2;

    // HEX = 3.026
    // PI = 3.14
    // DELTA = 7.065
    // OMEGA = 11.99
    // RELAT = 22.881
    // LAMBDA = 34.076
    // SIGMA = 36.734
    // SENTINEL = 49.86



}
