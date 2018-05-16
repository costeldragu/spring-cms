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
@Table(indexes = {
        @Index(columnList = "publishedAt")
})
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

    /** The content status */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ContentStatus status = ContentStatus.DRAFT;

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
    @JoinTable(
            joinColumns = @JoinColumn(name = "content_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id"),
            indexes = {
                    @Index(columnList = "content_id"),
                    @Index(columnList = "tag_id")
            }
    )
    private List<Tag> tags = new ArrayList<>();

    /** The categories that this content item belongs to */
    @ManyToMany
    @JoinTable(
            joinColumns = @JoinColumn(name = "content_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id"),
            indexes = {
                    @Index(columnList = "content_id"),
                    @Index(columnList = "category_id")
            }
    )
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
     * Return the content status.
     *
     * @return The content status.
     */
    public ContentStatus getStatus() {
        return status;
    }

    /**
     * Set the content status for the item.
     *
     * @param status The content status.
     */
    public void setStatus(ContentStatus status) {
        this.status = status;
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
