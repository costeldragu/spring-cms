package net.mavroprovato.springcms.repository;

import net.mavroprovato.springcms.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * The category repository
 */
public interface CategoryRepository extends JpaRepository<Category, Integer> {
}
