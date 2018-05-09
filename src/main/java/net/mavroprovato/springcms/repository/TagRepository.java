package net.mavroprovato.springcms.repository;

import net.mavroprovato.springcms.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * The tag repository
 */
public interface TagRepository extends JpaRepository<Tag, Integer> {
}
