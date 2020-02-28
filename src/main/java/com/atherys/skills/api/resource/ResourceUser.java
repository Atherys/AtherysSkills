package com.atherys.skills.api.resource;

public interface ResourceUser {

    /**
     * Fill this ResourceUser's resource by the specified amount
     *
     * @param amount
     */
    void fill(double amount);

    void fill();

    /**
     *
     * @param amount
     */
    void drain(double amount);

    double getMax();

    void setMax(double amount);

    double getCurrent();
}
