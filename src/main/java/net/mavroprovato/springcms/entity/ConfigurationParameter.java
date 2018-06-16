package net.mavroprovato.springcms.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Mapping for configuration parameters
 */
@Entity
public class ConfigurationParameter {

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
