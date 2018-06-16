package net.mavroprovato.springcms.entity;

import org.hibernate.search.annotations.Indexed;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Object mapping for post.
 */
@Entity
@DiscriminatorValue(ContentType.Values.POST)
@Indexed
public class Post extends Content {

    /** The post comments */
    @OneToMany(mappedBy = "post")
    private List<Comment> comments = new ArrayList<>();

    /** The tags applied to the post */
    @ManyToMany
    @JoinTable(
            joinColumns = @JoinColumn(name = "post_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id"),
            indexes = {
                    @Index(columnList = "post_id"),
                    @Index(columnList = "tag_id")
            }
    )
    private List<Tag> tags = new ArrayList<>();

    /** The categories that this post belongs to */
    @ManyToMany
    @JoinTable(
            joinColumns = @JoinColumn(name = "post_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id"),
            indexes = {
                    @Index(columnList = "post_id"),
                    @Index(columnList = "category_id")
            }
    )
    private List<Category> categories = new ArrayList<>();

    /**
     * Return the comments for the content item.
     *
     * @return The comments for the content item.
     */
    public List<Comment> getComments() {
        return comments;
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
