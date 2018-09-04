package net.mavroprovato.springcms.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Objects;

/**
 * Tags for content
 */
@Entity
public class Tag {

    /** The unique identifier of the tag */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private Integer id;

    /** The tag name */
    @Column(nullable = false)
    @Getter @Setter
    private String name;

    /** The tag description */
    @Column()
    @Getter @Setter
    private String description;

    /** The tag slug */
    @Column(unique = true)
    @Getter @Setter
    private String slug;
}
