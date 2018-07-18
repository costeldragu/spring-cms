package net.mavroprovato.springcms.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.search.annotations.Indexed;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Object mapping for post.
 */
@Entity
@DiscriminatorValue(ContentType.Values.POST)
@Indexed
public class Post extends Content {

    /** The post comments */
    @OneToMany(mappedBy = "post")
    @JsonIgnore
    private List<Comment> comments = new ArrayList<>();

    /** The tags applied to the post */
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            joinColumns = @JoinColumn(name = "post_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id"),
            indexes = {
                    @Index(columnList = "post_id"),
                    @Index(columnList = "tag_id")
            }
    )
    private Set<Tag> tags = new HashSet<>();

    /** The categories that this post belongs to */
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            joinColumns = @JoinColumn(name = "post_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id"),
            indexes = {
                    @Index(columnList = "post_id"),
                    @Index(columnList = "category_id")
            }
    )
    private Set<Category> categories = new HashSet<>();

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
    public Set<Tag> getTags() {
        return tags;
    }

    /**
     * Return the categories for the content item.
     *
     * @return The categories for the content item.
     */
    public Set<Category> getCategories() {
        return categories;
    }
}
