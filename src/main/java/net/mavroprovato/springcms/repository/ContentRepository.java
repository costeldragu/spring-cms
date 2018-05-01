package net.mavroprovato.springcms.repository;

import net.mavroprovato.springcms.dto.CountByMonth;
import net.mavroprovato.springcms.entity.Content;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

/**
 * The content repository.
 */
public interface ContentRepository extends JpaRepository<Content, Integer> {

    /**
     * Return the number of content objects that where published per month.
     *
     * @return The number of content objects that where published per month.
     */
    @Query(
        "SELECT new net.mavroprovato.springcms.dto.CountByMonth(YEAR(c.publishedAt), MONTH(c.publishedAt), COUNT(c)) " +
        "FROM Content c " +
        "GROUP BY YEAR(c.publishedAt), MONTH(c.publishedAt) " +
        "ORDER BY YEAR(c.publishedAt) DESC, MONTH(c.publishedAt) DESC"
    )
    List<CountByMonth> countByMonth();

    Page<Content> findByPublishedAtBetween(LocalDateTime startDateTime, LocalDateTime endDateTime, Pageable pageable);
}
