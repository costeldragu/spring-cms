package net.mavroprovato.springcms.entity;

import javax.persistence.*;
import java.util.Objects;

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

    /** The tag slug */
    @Column(unique = true)
    private String slug;

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

    /**
     * Return the slug for the tag.
     *
     * @return The slug for the tag.
     */
    public String getSlug() {
        return slug;
    }

    /**
     * Set the slug for the tag.
     *
     * @param slug The slug for tag.
     */
    public void setSlug(String slug) {
        this.slug = slug;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Tag tag = (Tag) o;

        return Objects.equals(name, tag.name);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
