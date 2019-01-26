package com.atherys.skills.resource;

import com.atherys.skills.api.resource.Resource;
import org.spongepowered.api.text.format.TextColors;

public class Rage extends AbstractResource {

    public static final String ID = "atherys:rage";

    public Rage(double starting) {
        super(
                TextColors.DARK_RED,
                ID,
                "Rage",
                100.0d,
                starting,
                5.0d
        );
    }

    @Override
    public Resource copy() {
        return new Rage(getCurrent());
    }

}
