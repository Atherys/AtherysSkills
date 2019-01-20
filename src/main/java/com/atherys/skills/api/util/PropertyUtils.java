package com.atherys.skills.api.util;

import com.atherys.skills.api.property.CastableProperties;
import com.atherys.skills.api.skill.Castable;

public class PropertyUtils {

    public static <T extends Castable<T,P>, P extends CastableProperties<T,P>> P inherit(P child, P parent) {
        P copy = child.copy();

        parent.getProperties().forEach((String k, Object v) -> {
            if ( !copy.hasProperty(k) ) {
                copy.addProperty(k, v);
            }
        });

        return copy;
    }

}
