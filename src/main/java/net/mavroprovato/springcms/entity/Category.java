package net.mavroprovato.springcms.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.util.Objects;

/**
 * Categories for content
 */
@Entity
public class Category {

    /** The unique identifier of the category */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /** The category name */
    @Column(nullable = false)
    private String name;

    /** The category slug */
    @Column(unique = true)
    private String slug;

    /** The parent category */
    @ManyToOne
    private Category parent;

    /**
     * Return the unique identifier of the category.
     *
     * @return The unique identifier of the category.
     */
    public Integer getId() {
        return id;
    }

    /**
     * Return the category name.
     *
     * @return The category name.
     */
    public String getName() {
        return name;
    }

    /**
     * Set the category name.
     *
     * @param name The category name.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Return the slug for the category.
     *
     * @return The slug for the category.
     */
    public String getSlug() {
        return slug;
    }

    /**
     * Set the slug for the category.
     *
     * @param slug The slug for the category.
     */
    public void setSlug(String slug) {
        this.slug = slug;
    }

    /**
     * Return the parent for this category.
     *
     * @return The parent for this category.
     */
    public Category getParent() {
        return parent;
    }

    /**
     * Set the parent for this category.
     *
     * @param parent The parent for this category.
     */
    public void setParent(Category parent) {
        this.parent = parent;
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
        Category category = (Category) o;

        return Objects.equals(name, category.name);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
