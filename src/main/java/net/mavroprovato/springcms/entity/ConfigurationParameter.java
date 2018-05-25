package net.mavroprovato.springcms.entity;

import javax.persistence.*;

/**
 * Mapping for configuration parameters
 */
@Entity
public class ConfigurationParameter {

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

    /** The unique identifier of the content */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /** The configuration parameter name */
    @Column(nullable = false, unique = true)
    private String name;

    /** The configuration parameter value */
    @Column(nullable = false)
    private String value;

    /**
     * Return the unique identifier for the configuration parameter.
     *
     * @return The unique identifier for the configuration parameter.
     */
    public Integer getId() {
        return id;
    }

    /**
     * Return the name of the configuration parameter.
     *
     * @return The name of the configuration parameter.
     */
    public String getName() {
        return name;
    }

    /**
     * Set the name for the configuration parameter.
     *
     * @param name The name for the configuration paramter.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Get the value for the configuration parameter.
     *
     * @return The value for the configuration parameter.
     */
    public String getValue() {
        return value;
    }

    /**
     * Set the value for the configuration parameter.
     *
     * @param value The value for the configuration parameter.
     */
    public void setValue(String value) {
        this.value = value;
    }
}
