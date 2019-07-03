package com.atherys.skills.resource;

import com.atherys.skills.api.resource.Resource;
import org.spongepowered.api.text.format.TextColors;

public class ActionPoints extends AbstractResource {

    public static final String ID = "atherys:action_points";

    public ActionPoints(double starting) {
        super(
                TextColors.GOLD,
                ID,
                "Action Points",
                100.0d,
                starting,
                5.0d
        );
    }

    @Override
    public Resource copy() {
        return new ActionPoints(getCurrent());
    }

}
