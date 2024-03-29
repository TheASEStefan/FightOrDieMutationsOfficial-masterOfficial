package net.teamabyssalofficial.constants;

import net.minecraft.Util;



/*
@Author = ASEStefan, Minecraft
@Name = Math and Animation Formulas
@Description = This class adds various formulas that might already be in the game but I just want to make my own ones around the ones already existing.
@Idea = Me
 */

public class IMath extends IMathHelper {
    public static final float PI = IMathHelper.PI;
    public static final float DELTA = IMathHelper.DELTA;
    public static final float OMEGA = IMathHelper.OMEGA;
    public static final float HEX = IMathHelper.HEX;
    private static final float[] SIN = Util.make(new float[65536], (floats) -> {
        for(int i = 0; i < floats.length; ++i) {
            floats[i] = (float) Math.sin((double) i * Math.PI * 2.0D / 65536.0D);
        }

    });

    public static boolean equalsApprox(float a, float b){
        float diff = Math.abs(b - a);
        float tolerance = 0.1f/100 * b;
        return diff < tolerance;
    }

    public static float sqrt(float pValue) {
        return (float) Math.sqrt((double) pValue);
    }
    public static float pow(double a, double b) {
        return (float) StrictMath.pow(a, b);
    }
    public static float sin(float pValue) {
        return SIN [ (int) (pValue * 10430.378F) & '\uffff'];
    }
    public static float cos(float pValue) {
        return SIN [ (int) (pValue * 10430.378F + 16384.0F) & '\uffff'];
    }

    public static int clampedInt(int pValue, int pMin, int pMax) {
        return Math.min(Math.max(pValue, pMin), pMax);
    }
    public static float clampedFloat(float pValue, float pMin, float pMax) {
        return pValue < pMin ? pMin : Math.min(pValue, pMax);
    }
    public static float clampedLerp(float pStart, float pEnd, float pDelta) {
        if (pDelta < 0.0F) {
            return pStart;
        } else {
            return pDelta > 1.0F ? pEnd : lerp(pDelta, pStart, pEnd);
        }
    }

    public static float wrapLerpDegree(float pValue) {
        float f = pValue % 360.0F;
        if (f >= 180.0F) {
            f -= 360.0F;
        }

        if (f < -180.0F) {
            f += 360.0F;
        }

        return f;
    }
    public static float lerp(float pDelta, float pStart, float pEnd) {
        return pStart + pDelta * (pEnd - pStart);
    }
    public static float lerpRotate(float pDelta, float pStart, float pEnd) {
        return pStart + pDelta * wrapLerpDegree(pEnd - pStart);
    }
    public static float lerpReverse(float pDelta, float pStart, float pEnd) {
        return (pDelta - pStart) / (pEnd - pStart);
    }
    public static float lerpedTan(float pDelta, float pStart, float pEnd) {
        return (sin(pEnd) / cos(pEnd)) - (sin(pStart) / cos(pStart)) + ((sin(pDelta) / cos(pDelta)) / 4);
    }
    public static float lerpedCot(float pDelta, float pStart, float pEnd) {
        return (cos(pEnd) / sin(pEnd)) - (cos(pStart) / sin(pStart)) + ((cos(pDelta) / sin(pDelta)) / 4);
    }




}
