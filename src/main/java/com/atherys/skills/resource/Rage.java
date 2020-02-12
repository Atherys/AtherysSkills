package com.atherys.skills.resource;

import com.atherys.skills.AtherysSkills;
import com.atherys.skills.api.resource.Resource;
import org.spongepowered.api.text.format.TextColors;

public class Rage extends AbstractResource {

    public static final String ID = "atherys:rage";

    public Rage(double starting) {
        super(
                TextColors.DARK_RED,
                ID,
                "Rage",
                AtherysSkills.getInstance().getConfig().RESOURCE_LIMIT,
                starting,
                5.0d
        );
    }

    @Override
    public Resource copy() {
        return new Rage(getCurrent());
    }

}
