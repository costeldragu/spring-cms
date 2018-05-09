package net.mavroprovato.springcms.entity;

import javax.persistence.*;

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
}
