package net.mavroprovato.springcms.repository;

import net.mavroprovato.springcms.entity.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * The page repository.
 */
public interface PageRepository extends JpaRepository<Page, Integer>  {

    /**
     * Return all published pages by their specified order.
     *
     * @return The ordered published pages.
     */
    @Query("SELECT p FROM Page p WHERE p.status = 'PUBLISHED' ORDER BY order")
    List<Page> findAll();
}
