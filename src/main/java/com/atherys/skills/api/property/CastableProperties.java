package com.atherys.skills.api.property;

import com.atherys.skills.api.skill.Castable;
import com.atherys.skills.api.skill.MouseButtonCombo;

import javax.annotation.Nullable;
import java.io.Serializable;
import java.util.Map;
import java.util.Optional;

/**
 * An interface representing all properties a Castable may need to use during its existence.
 * All CastableProperties must be deep-copyable.
 */
public interface CastableProperties<T extends Castable<T,P>, P extends CastableProperties<T,P>> extends Serializable {

    String PERMISSION = "permission";

    String COOLDOWN = "cooldown";

    String COST = "cost";

    String DESCRIPTION = "description";

    String MOUSE_COMBO = "mouse-combo";

    String getPermission();

    double getResourceCost();

    long getCooldown();

    MouseButtonCombo getCombo();

    String getDescription();

    Map<String, Object> getProperties();

    default boolean hasProperty(String key) {
        return getProperty(key) == null;
    }

    void addProperty(String key, Object value);

    P copy();

    /**
     * Mutates this instance by inheriting the properties of the provided object.
     * If these CastableProperties lack a property from the parent, they should deep-copy it.
     *
     * @param parent The parent to copy properties from
     * @return The new, mutated instance
     */
    P inheritFrom(P parent);

    /**
     * Retrieves the value behind the provided key
     *
     * @param key the key to look for
     * @return The value, or null if not found
     */
    @Nullable
    default Object getProperty(String key) {
        switch(key) {
            case PERMISSION:
                return getPermission();
            case COST:
                return getResourceCost();
            case MOUSE_COMBO:
                return getCombo();
            case COOLDOWN:
                return getCooldown();
            case DESCRIPTION:
                return getDescription();
            default:
                return getProperties().get(key);
        }
    }

    /**
     * Retrieves the value behind the provided key
     *
     * @param key   The key to look for
     * @param clazz The class the result should be interpreted as
     * @return An optional containing the result, or empty, if the resulting value is null OR not the same class as required
     */
    default <O> Optional<O> get(String key, Class<O> clazz) {
        Object object = getProperty(key);

        if (object == null) {
            return Optional.empty();
        }

        if (clazz.isAssignableFrom(object.getClass())) {
            return Optional.of((O) object);
        } else {
            return Optional.empty();
        }
    }

    /**
     * Retrieves the value behind the provided key, or a provided default
     *
     * @param key          The key to look for
     * @param defaultValue The default value
     * @return The result, or the default value
     */
    default <O> O getOrDefault(String key, O defaultValue) {
        Object object = getProperty(key);

        if (object == null) {
            return defaultValue;
        }

        if (defaultValue.getClass().isAssignableFrom(object.getClass())) {
            return (O) object;
        } else {
            return defaultValue;
        }
    }

    default Optional<String> getString(String key) {
        return get(key, String.class);
    }

    default Optional<Double> getDouble(String key) {
        return get(key, Double.class);
    }

    default Optional<Integer> getInteger(String key) {
        return get(key, Integer.class);
    }

    default Optional<Long> getLong(String key) {
        return get(key, Long.class);
    }

    default Optional<Float> getFloat(String key) {
        return get(key, Float.class);
    }

    default Optional<Byte> getByte(String key) {
        return get(key, Byte.class);
    }

    default Optional<Short> getShort(String key) {
        return get(key, Short.class);
    }
}
