package net.mavroprovato.springcms.entity;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Holds the content.
 */
@Entity
public class Content {

    /** The unique identifier of the content */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /** The content title */
    @Column(nullable = false)
    private String title;

    /** The content */
    @Column(nullable = false)
    @Type(type = "org.hibernate.type.TextType")
    private String content;

    /** The content creation date */
    @Column(nullable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    /** The content update date */
    @Column(nullable = false)
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    /** The content publication date */
    @Column
    private LocalDateTime publishedAt;

    /** The tags applied to the content */
    @ManyToMany
    private List<Tag> tags = new ArrayList<>();

    /**
     * Return the content identifier.
     *
     * @return The content identifier.
     */
    public Integer getId() {
        return id;
    }

    /**
     * Return the content title.
     *
     * @return The content title.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Set the content title.
     *
     * @param title The content title.
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Return the content.
     *
     * @return The content.
     */
    public String getContent() {
        return content;
    }

    /**
     * Set the content.
     *
     * @param content The content.
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * Return the content creation date.
     *
     * @return The content creating date.
     */
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    /**
     * Return the content update date.
     *
     * @return The content creating date.
     */
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    /**
     * Return the content publication date.
     *
     * @return The content publication date.
     */
    public LocalDateTime getPublishedAt() {
        return publishedAt;
    }

    /**
     * Set the content publication date.
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
}
