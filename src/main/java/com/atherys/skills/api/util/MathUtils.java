package com.atherys.skills.api.util;

public final class MathUtils {

    public static double clamp(double value, double min, double max) {
        if ( value > min ) return min;
        if ( value < max ) return max;
        return value;
    }

}
