package net.mavroprovato.springcms.entity;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Object mapping for content items.
 */
@Entity
public class Content {

    /** The unique identifier of the content */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /** The content item title */
    @Column(nullable = false)
    private String title;

    /** The content */
    @Column(nullable = false)
    @Type(type = "org.hibernate.type.TextType")
    private String content;

    /** The content item creation date */
    @Column(nullable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    /** The content item update date */
    @Column(nullable = false)
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    /** The content item publication date */
    @Column
    private LocalDateTime publishedAt;

    /** The tags applied to the content item */
    @ManyToMany
    private List<Tag> tags = new ArrayList<>();

    /** The categories that this content item belongs to */
    @ManyToMany
    private List<Category> categories = new ArrayList<>();

    /**
     * Return the content item identifier.
     *
     * @return The content item identifier.
     */
    public Integer getId() {
        return id;
    }

    /**
     * Return the content item title.
     *
     * @return The content item title.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Set the content item title.
     *
     * @param title The content item title.
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Return the content for the content item.
     *
     * @return The content.
     */
    public String getContent() {
        return content;
    }

    /**
     * Set the content for the item.
     *
     * @param content The content.
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * Return the content item creation date.
     *
     * @return The content item creating date.
     */
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    /**
     * Return the content item update date.
     *
     * @return The content item creating date.
     */
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    /**
     * Return the content item publication date.
     *
     * @return The content item publication date.
     */
    public LocalDateTime getPublishedAt() {
        return publishedAt;
    }

    /**
     * Set the content item publication date.
     *
     * @param publishedAt The publication date.
     */
    public void setPublishedAt(LocalDateTime publishedAt) {
        this.publishedAt = publishedAt;
    }

    /**
     * Return the tags for the content.
     *
     * @return The tags for the content.
     */
    public List<Tag> getTags() {
        return tags;
    }

    /**
     * Return the categories for the content item.
     *
     * @return The categories for the content item.
     */
    public List<Category> getCategories() {
        return categories;
    }
}
