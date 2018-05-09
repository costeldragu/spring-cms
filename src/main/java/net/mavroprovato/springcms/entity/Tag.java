package net.mavroprovato.springcms.entity;

import javax.persistence.*;

/**
 * Tags for content
 */
@Entity
public class Tag {

    /** The unique identifier of the tag */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /** The tag name */
    @Column(nullable = false)
    private String name;

    /**
     * Return the unique identifier of the tag.
     *
     * @return The unique identifier of the tag.
     */
    public Integer getId() {
        return id;
    }
    /**
     * Return the tag name.
     *
     * @return The tag name.
     */
    public String getName() {
        return name;
    }

    /**
     * Set the tag name.
     *
     * @param name The tag name.
     */
    public void setName(String name) {
        this.name = name;
    }
}
