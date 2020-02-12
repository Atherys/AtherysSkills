package com.atherys.skills.resource;

import com.atherys.skills.AtherysSkills;
import com.atherys.skills.api.resource.Resource;
import org.spongepowered.api.text.format.TextColors;

public class ActionPoints extends AbstractResource {

    public static final String ID = "atherys:action_points";

    public ActionPoints(double starting) {
        super(
                TextColors.GOLD,
                ID,
                "Action Points",
                AtherysSkills.getInstance().getConfig().RESOURCE_LIMIT,
                starting,
                5.0d
        );
    }

    @Override
    public Resource copy() {
        return new ActionPoints(getCurrent());
    }

}
