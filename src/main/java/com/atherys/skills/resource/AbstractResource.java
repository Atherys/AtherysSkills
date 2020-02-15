package com.atherys.skills.resource;

import com.atherys.skills.AtherysSkills;
import com.atherys.skills.api.resource.Resource;
import com.atherys.skills.api.util.MathUtils;
import com.google.gson.annotations.Expose;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.action.TextActions;
import org.spongepowered.api.text.format.TextColor;
import org.spongepowered.api.text.format.TextColors;

/**
 * An abstract implementation of {@link Resource} where the minimum amount is always 0
 */
public abstract class AbstractResource implements Resource {

    @Expose private String id;
    @Expose private String name;

    @Expose private TextColor color;
    @Expose private double current;
    @Expose private double regen;


    protected AbstractResource(TextColor color, String id, String name, double regen) {
        this.color = color;
        this.id = id;
        this.name = name;
        this.current = 0.0d;
        this.regen = regen;
    }

    @Override
    public double getMax() {
        return AtherysSkills.getInstance().getConfig().RESOURCE_LIMIT;
    }

    @Override
    public double getCurrent() {
        return current;
    }

    private void change(double delta) {
        this.current = MathUtils.clamp(current + delta, 0.0d, getMax());
    }

    @Override
    public void drain(double amount) {
        this.change(-amount);
    }

    @Override
    public void fill(double amount) {
        this.change(amount);
    }

    @Override
    public TextColor getColor() {
        return color;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public double getRegen() {
        return regen;
    }

    @Override
    public Text toText() {
        return Text.builder()
                .append(Text.of(this.getColor(), this.getName()))
                .onHover(TextActions.showText(Text.of(this.getCurrent(), this.getColor(), "/", TextColors.RESET, this.getMax())))
                .build();
    }
}
