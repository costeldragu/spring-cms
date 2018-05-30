package net.mavroprovato.springcms.entity;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.OffsetDateTime;

/**
 * A comment on a content item
 */
@Entity
public class Comment {

    /** The unique identifier of the comment */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /** The comment content */
    @Column(nullable = false)
    @Type(type = "org.hibernate.type.TextType")
    @NotNull
    private String comment;

    /** The name of the user that posted the comment */
    @Column(nullable = false, length = 255)
    @NotNull
    @Size(min = 2, max = 255)
    private String name;

    /** The email of the user that posted the comment */
    @Column(nullable = false)
    @NotNull
    @Size(min = 2, max = 255)
    private String email;

    /** The web site of the user that posted the comment */
    @Column
    @Size(min = 2, max = 255)
    private String webSite;

    /** The comment creation date */
    @Column(nullable = false)
    @CreationTimestamp
    private OffsetDateTime createdAt;

    /** The comment update date */
    @Column(nullable = false)
    @UpdateTimestamp
    private OffsetDateTime updatedAt;

    /** The content that this comment belong to */
    @ManyToOne
    @JoinColumn
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
    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }

    /**
     * Return the update date of the comment.
     *
     * @return The update date of the comment.
     */
    public OffsetDateTime getUpdatedAt() {
        return updatedAt;
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
