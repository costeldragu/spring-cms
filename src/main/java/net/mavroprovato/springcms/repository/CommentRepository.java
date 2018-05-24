package net.mavroprovato.springcms.repository;

import net.mavroprovato.springcms.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * The comment repository
 */
public interface CommentRepository extends JpaRepository<Comment, Integer> {

}
