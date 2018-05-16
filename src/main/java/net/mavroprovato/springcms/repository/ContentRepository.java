package net.mavroprovato.springcms.repository;

import net.mavroprovato.springcms.dto.CountByMonth;
import net.mavroprovato.springcms.entity.Content;
import net.mavroprovato.springcms.entity.ContentStatus;
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

    /**
     * Return a page of content items by status.
     *
     * @param status The content item status.
     * @param pageable The pagination parameters.
     * @return The content item list.
     */
    Page<Content> findByStatus(ContentStatus status, Pageable pageable);

    /**
     * Return a page of content items published between two dates.
     *
     * @param startDateTime The start dates.
     * @param endDateTime The end dates.
     * @param pageable The pagination parameters.
     * @return The content item list.
     */
    Page<Content> findByStatusAndPublishedAtBetween(ContentStatus status, LocalDateTime startDateTime,
                                                    LocalDateTime endDateTime, Pageable pageable);

    /**
     * Find content by status and tag identifier.
     *
     * @param tagId The tag identifier.
     * @param pageable The pagination parameters.
     * @return The content item list.
     */
    Page<Content> findByStatusAndTagsId(ContentStatus status, int tagId, Pageable pageable);

    /**
     * Find content by status and tag slug.
     *
     * @param tagSlug The tag slug.
     * @param pageable The pagination parameters.
     * @return The content item list.
     */
    Page<Content> findByStatusAndTagsSlug(ContentStatus status, String tagSlug, Pageable pageable);

    /**
     * Find content by status and category.
     *
     * @param categoryId The category identifier.
     * @param pageable The pagination parameters.
     * @return The content item list.
     */
    Page<Content> findByStatusAndCategoriesId(ContentStatus status, int categoryId, Pageable pageable);
}
