package net.mavroprovato.springcms.entity;

/**
 * An enumeration of the defined configuration parameters.
 */
public enum Parameter {
    /** How many posts to display in the list page. */
    POSTS_PER_PAGE(10);

    /** The default value of the parameter */
    private final Object defaultValue;

    /**
     * Create the parameter.
     *
     * @param defaultValue The default parameter value.
     */
    Parameter(Object defaultValue) {
        this.defaultValue = defaultValue;
    }

    /**
     * Get the parameter default value.
     *
     * @return The parameter default value.
     */
    public Object defaultValue() {
        return defaultValue;
    }
}
