package com.atherys.skills.resource;

import com.atherys.skills.api.resource.Resource;
import org.spongepowered.api.text.format.TextColors;

public class Mana extends AbstractResource {

    public static final String ID = "atherys:mana";

    public Mana(double starting) {
        super(
                TextColors.BLUE,
                ID,
                "Mana",
                5.0d
        );
    }

    @Override
    public Resource copy() {
        return new Mana(getCurrent());
    }

}
