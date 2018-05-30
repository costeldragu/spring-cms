package net.mavroprovato.springcms.entity;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.OffsetDateTime;

/**
 * Object mapping for content items.
 */
@Entity
@Table(indexes = {
        @Index(columnList = "type, status, publishedAt")
})
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(discriminatorType = DiscriminatorType.STRING, name = "type")
public abstract class Content {

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

    /** The content item slug */
    @Column(unique = true)
    private String slug;

    /** The content item creation date */
    @Column(nullable = false)
    @CreationTimestamp
    private OffsetDateTime createdAt;

    /** The content item update date */
    @Column(nullable = false)
    @UpdateTimestamp
    private OffsetDateTime updatedAt;

    /** The content item publication date */
    @Column
    private OffsetDateTime publishedAt;

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
     * Return the slug for the content item.
     *
     * @return The slug for the content item.
     */
    public String getSlug() {
        return slug;
    }

    /**
     * Set the slug for the content item.
     *
     * @param slug The slug for the content item.
     */
    public void setSlug(String slug) {
        this.slug = slug;
    }

    /**
     * Return the content item creation date.
     *
     * @return The content item creating date.
     */
    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }

    /**
     * Return the content item update date.
     *
     * @return The content item creating date.
     */
    public OffsetDateTime getUpdatedAt() {
        return updatedAt;
    }

    /**
     * Return the content item publication date.
     *
     * @return The content item publication date.
     */
    public OffsetDateTime getPublishedAt() {
        return publishedAt;
    }

    /**
     * Set the content item publication date.
     *
     * @param publishedAt The publication date.
     */
    public void setPublishedAt(OffsetDateTime publishedAt) {
        this.publishedAt = publishedAt;
    }
}
