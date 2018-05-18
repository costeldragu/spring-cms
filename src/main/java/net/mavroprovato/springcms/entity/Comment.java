package net.mavroprovato.springcms.entity;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * A comment on a content item
 */
@Entity
public class Comment {

    /** The unique identifier of the comment */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /** Comment on a content item */
    @Column(nullable = false)
    @Type(type = "org.hibernate.type.TextType")
    private String comment;

    /** The name of the user that posted the comment */
    @Column
    private String name;

    /** The email of the user that posted the comment */
    @Column
    private String email;

    /** The web site of the user that posted the comment */
    @Column
    private String webSite;

    /** The comment creation date */
    @Column(nullable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    /** The comment update date */
    @Column(nullable = false)
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    /** The content that this comment belong to */
    @ManyToOne
    private Content content;

    /**
     * Return the unique identifier of the comment.
     *
     * @return The unique identifier of the comment.
     */
    public Integer getId() {
        return id;
    }

    /**
     * Return the content of the comment.
     *
     * @return The content of the comment.
     */
    public String getComment() {
        return comment;
    }

    /**
     * Set the content of the comment.
     *
     * @param comment The content of the comment.
     */
    public void setComment(String comment) {
        this.comment = comment;
    }

    /**
     * Return the name of the name of the user that posted the comment.
     *
     * @return The name of the name of the user that posted the comment.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the name of the user that posted the comment.
     *
     * @param name The name of the name of the user that posted the comment.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the email of the name of the user that posted the comment.
     *
     * @return The name of the name of the user that posted the comment.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the name of the email of the user that posted the comment.
     *
     * @param email The name of the name of the user that posted the comment.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Returns the web site of the name of the user that posted the comment.
     *
     * @return The web site of the name of the user that posted the comment.
     */
    public String getWebSite() {
        return webSite;
    }

    /**
     * Sets the web site of the email of the user that posted the comment.
     *
     * @param webSite The web site of the name of the user that posted the comment.
     */
    public void setWebSite(String webSite) {
        this.webSite = webSite;
    }

    /**
     * Return the creation date of the comment.
     *
     * @return The creation date of the comment.
     */
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    /**
     * Set the creation date of the comment.
     *
     * @param createdAt The creation date of the comment.
     */
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * Return the update date of the comment.
     *
     * @return The update date of the comment.
     */
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    /**
     * Set the update date of the comment.
     *
     * @param updatedAt The update date of the comment.
     */
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    /**
     * Return the content for this comment.
     *
     * @return The content for this comment.
     */
    public Content getContent() {
        return content;
    }

    /**
     * Set the content for this comment.
     *
     * @param content The content for this comment.
     */
    public void setContent(Content content) {
        this.content = content;
    }
}
