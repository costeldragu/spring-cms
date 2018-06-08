package net.mavroprovato.springcms.repository;

import net.mavroprovato.springcms.dto.CountByMonth;
import net.mavroprovato.springcms.entity.ContentStatus;
import net.mavroprovato.springcms.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

/**
 * The content repository.
 */
public interface PostRepository extends JpaRepository<Post, Integer> {

    /**
     * Return the number of content objects that where published per month.
     *
     * @return The number of content objects that where published per month.
     */
    @Query(
        "SELECT new net.mavroprovato.springcms.dto.CountByMonth(YEAR(p.publishedAt), MONTH(p.publishedAt), COUNT(p)) " +
        "FROM Post p " +
        "GROUP BY YEAR(p.publishedAt), MONTH(p.publishedAt) " +
        "ORDER BY YEAR(p.publishedAt) DESC, MONTH(p.publishedAt) DESC"
    )
    List<CountByMonth> countByMonth();

    /**
     * Return a page of content items by status.
     *
     * @param status The content item status.
     * @param pageable The pagination parameters.
     * @return The content item page.
     */
    Page<Post> findByStatus(ContentStatus status, Pageable pageable);

    /**
     * Return a page of content items published between two dates.
     *
     * @param startDateTime The start dates.
     * @param endDateTime The end dates.
     * @param pageable The pagination parameters.
     * @return The content item page.
     */
    Page<Post> findByStatusAndPublishedAtBetween(ContentStatus status, OffsetDateTime startDateTime,
                                                 OffsetDateTime endDateTime, Pageable pageable);

    /**
     * Find content by status and tag identifier.
     *
     * @param tagId The tag identifier.
     * @param pageable The pagination parameters.
     * @return The content item page.
     */
    Page<Post> findByStatusAndTagsId(ContentStatus status, int tagId, Pageable pageable);

    /**
     * Find content by status and tag slug.
     *
     * @param tagSlug The tag slug.
     * @param pageable The pagination parameters.
     * @return The content item page.
     */
    Page<Post> findByStatusAndTagsSlug(ContentStatus status, String tagSlug, Pageable pageable);

    /**
     * Find content by status and category id.
     *
     * @param categoryId The category identifier.
     * @param pageable The pagination parameters.
     * @return The content item page.
     */
    Page<Post> findByStatusAndCategoriesId(ContentStatus status, int categoryId, Pageable pageable);

    /**
     * Find content by status and category slug.
     *
     * @param slug The category slug.
     * @param pageable The pagination parameters.
     * @return The content item page.
     */
    Page<Post> findByStatusAndCategoriesSlug(ContentStatus published, String slug, Pageable pageable);

    /**
     * Get content by slug.
     *
     * @param slug The slug.
     * @return The content.
     */
    Optional<Post> findOneBySlug(String slug);
}
