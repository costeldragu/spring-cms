package net.mavroprovato.springcms.repository;

import net.mavroprovato.springcms.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * The category repository
 */
public interface CategoryRepository extends JpaRepository<Category, Integer> {
    /**
     * Returns all categories ordered by name.
     *
     * @return All categories ordered by name.
     */
    List<Category> findAllByOrderByNameAsc();
}
