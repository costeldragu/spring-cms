package net.mavroprovato.springcms.service;

import net.mavroprovato.springcms.entity.Content;
import net.mavroprovato.springcms.repository.ContentRepository;
import net.mavroprovato.springcms.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.HashMap;
import java.util.Map;

/**
 * The content service.
 */
@Service
public class ContentService {

    /** The content repository */
    private final ContentRepository contentRepository;

    /**
     * Create the content service.
     *
     * @param contentRepository The content repository.
     */
    @Autowired
    public ContentService(ContentRepository contentRepository) {
        this.contentRepository = contentRepository;
    }

    /**
     * Get a content item page, ordered by publication date.
     *
     * @param page The page number.
     * @return The content items.
     */
    public Map<String, ?> list(int page) {
        return listImpl(null, null, null, page);
    }

    /**
     * Get a page of content items published in a year, ordered by publication date.
     *
     * @param year The year.
     * @param page The page number.
     * @return The content items.
     */
    public Map<String, ?> list(int year, int page) {
        return listImpl(year, null, null, page);
    }

    /**
     * Get a page of content items published in a month, ordered by publication date.
     *
     * @param year The year.
     * @param month The month number (1 for January, 12 for December)
     * @param page The page number.
     * @return The content items.
     */
    public Map<String, ?> list(int year, int month, int page) {
        return listImpl(year, month, null, page);
    }

    /**
     * Get a page of content items published in a day, ordered by publication date.
     *
     * @param year The year.
     * @param month The month number (1 for January, 12 for December)
     * @param day The day number.
     * @param page The page number.
     * @return The content items.
     */
    public Map<String, ?> list(int year, int month, int day, int page) {
        return listImpl(year, month, day, page);
    }

    /**
     * Get a page of content items, ordered by publication date.
     *
     * @param year The year.
     * @param month The month number (1 for January, 12 for December)
     * @param day The day number.
     * @param page The page number.
     * @return The content items.
     */
    private Map<String, ?> listImpl(Integer year, Integer month, Integer day, int page) {
        // Calculate the start/end publication date to use for the content query, and the url prefix for the pagination
        // links.
        LocalDateTime startDateTime = null;
        LocalDateTime endDateTime = null;
        String urlPrefix = "";
        if (year != null && month != null && day != null) {
            LocalDate date = LocalDate.of(year, month, day);
            startDateTime = LocalDateTime.of(date, LocalTime.MIN);
            endDateTime = LocalDateTime.of(date, LocalTime.MAX);
            urlPrefix = String.format("/%02d/%02d/%02d", year, month, day);
        } else if (year != null && month != null) {
            LocalDate startDate = LocalDate.of(year, month, 1);
            startDateTime = LocalDateTime.of(startDate, LocalTime.MIN);
            LocalDate endDate = startDate.withDayOfMonth(startDate.getMonth().length(startDate.isLeapYear()));
            endDateTime = LocalDateTime.of(endDate, LocalTime.MAX);
            urlPrefix = String.format("/%02d/%02d", year, month);
        } else if (year != null) {
            LocalDate startDate = LocalDate.of(year, Month.JANUARY.getValue(), 1);
            startDateTime = LocalDateTime.of(startDate, LocalTime.MIN);
            LocalDate endDate = LocalDate.of(year, Month.DECEMBER.getValue(), 31);
            endDateTime = LocalDateTime.of(endDate, LocalTime.MIN);
            urlPrefix = String.format("/%02d", year);
        }

        // Run the query
        PageRequest pageRequest = PageRequest.of(page - 1, 10, Sort.Direction.DESC, "publishedAt");
        Page<Content> contents;
        if (startDateTime == null) {
            contents = contentRepository.findAll(pageRequest);
        } else {
            contents = contentRepository.findByPublishedAtBetween(startDateTime, endDateTime, pageRequest);
        }

        return getModel(contents, urlPrefix);
    }

    /**
     * Get a page of content items under a specific tag.
     *
     * @param id The tag identifier.
     * @param page The page.
     * @return The content items.
     */
    public Map<String,?> byTagId(int id, int page) {
        // Run the query
        PageRequest pageRequest = PageRequest.of(page - 1, 10, Sort.Direction.DESC, "publishedAt");
        Page<Content> contents = contentRepository.findByTagsId(id, pageRequest);

        return getModel(contents, String.format("/tag/%d", id));
    }

    /**
     * Return the page model.
     *
     * @param contents The page contents.
     * @param urlPrefix The URL prefix.
     * @return The page model.
     */
    private Map<String, ?> getModel(Page<Content> contents, String urlPrefix) {
        Map<String, Object> model = new HashMap<>();
        model.put("contents", contents);
        model.put("archives", contentRepository.countByMonth());
        model.put("urlPrefix", urlPrefix);

        return model;
    }
}
