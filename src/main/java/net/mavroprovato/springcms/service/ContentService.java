package net.mavroprovato.springcms.service;

import com.rometools.rome.feed.atom.Category;
import com.rometools.rome.feed.atom.Entry;
import com.rometools.rome.feed.atom.Feed;
import com.rometools.rome.feed.atom.Link;
import net.mavroprovato.springcms.entity.Comment;
import net.mavroprovato.springcms.entity.ConfigurationParameter;
import net.mavroprovato.springcms.entity.Content;
import net.mavroprovato.springcms.entity.ContentStatus;
import net.mavroprovato.springcms.exception.ResourceNotFoundException;
import net.mavroprovato.springcms.repository.CategoryRepository;
import net.mavroprovato.springcms.repository.CommentRepository;
import net.mavroprovato.springcms.repository.ContentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;

/**
 * The content service.
 */
@Service
public class ContentService {

    /** The content repository */
    private final ContentRepository contentRepository;

    /** The category repository */
    private final CategoryRepository categoryRepository;

    /** The comment repository */
    private final CommentRepository commentRepository;

    /** The configuration parameter service */
    private final ConfigurationParameterService configurationParameterService;

    /**
     * Create the content service.
     * @param contentRepository The content repository.
     * @param categoryRepository The category repository.
     * @param commentRepository The comment repository.
     * @param configurationParameterService The configuration parameter service.
     */
    @Autowired
    public ContentService(ContentRepository contentRepository, CategoryRepository categoryRepository,
                          CommentRepository commentRepository, ConfigurationParameterService configurationParameterService) {
        this.contentRepository = contentRepository;
        this.categoryRepository = categoryRepository;
        this.commentRepository = commentRepository;
        this.configurationParameterService = configurationParameterService;
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
        int postsPerPage = configurationParameterService.getInteger(ConfigurationParameter.Parameter.POSTS_PER_PAGE);
        PageRequest pageRequest = PageRequest.of(page - 1, postsPerPage, Sort.Direction.DESC, "publishedAt");
        Page<Content> contents;
        if (startDateTime == null) {
            contents = contentRepository.findByStatus(ContentStatus.PUBLISHED, pageRequest);
        } else {
            contents = contentRepository.findByStatusAndPublishedAtBetween(
                    ContentStatus.PUBLISHED, startDateTime, endDateTime, pageRequest);
        }

        return getListModel(contents, urlPrefix);
    }

    /**
     * Get a page of content items under a specific tag, specified by its id.
     *
     * @param id The tag identifier.
     * @param page The page.
     * @return The content items.
     */
    public Map<String,?> listByTagId(int id, int page) {
        // Run the query
        int postsPerPage = configurationParameterService.getInteger(ConfigurationParameter.Parameter.POSTS_PER_PAGE);
        PageRequest pageRequest = PageRequest.of(page - 1, postsPerPage, Sort.Direction.DESC, "publishedAt");
        Page<Content> contents = contentRepository.findByStatusAndTagsId(ContentStatus.PUBLISHED, id, pageRequest);

        return getListModel(contents, String.format("/tag/%d", id));
    }

    /**
     * Get a page of content items under a specific tag, specified by its slug.
     *
     * @param slug The tag slug.
     * @param page The page.
     * @return The content items.
     */
    public Map<String,?> listByTagSlug(String slug, int page) {
        // Run the query
        int postsPerPage = configurationParameterService.getInteger(ConfigurationParameter.Parameter.POSTS_PER_PAGE);
        PageRequest pageRequest = PageRequest.of(page - 1, postsPerPage, Sort.Direction.DESC, "publishedAt");
        Page<Content> contents = contentRepository.findByStatusAndTagsSlug(ContentStatus.PUBLISHED, slug, pageRequest);

        return getListModel(contents, String.format("/tag/%s", slug));
    }

    /**
     * Get a page of content items categorized with a specific category, specified by its id.
     *
     * @param id The category identifier.
     * @param page The page.
     * @return The content items.
     */
    public Map<String,?> listByCategoryId(int id, int page) {
        // Run the query
        int postsPerPage = configurationParameterService.getInteger(ConfigurationParameter.Parameter.POSTS_PER_PAGE);
        PageRequest pageRequest = PageRequest.of(page - 1, postsPerPage, Sort.Direction.DESC, "publishedAt");
        Page<Content> contents = contentRepository.findByStatusAndCategoriesId(
                ContentStatus.PUBLISHED, id, pageRequest);

        return getListModel(contents, String.format("/category/%d", id));
    }

    /**
     * Get a page of content items categorized with a specific category, specified by its slug.
     *
     * @param slug The category slug.
     * @param page The page.
     * @return The content items.
     */
    public Map<String,?> listByCategorySlug(String slug, int page) {
        // Run the query
        int postsPerPage = configurationParameterService.getInteger(ConfigurationParameter.Parameter.POSTS_PER_PAGE);
        PageRequest pageRequest = PageRequest.of(page - 1, postsPerPage, Sort.Direction.DESC, "publishedAt");
        Page<Content> contents = contentRepository.findByStatusAndCategoriesSlug(
                ContentStatus.PUBLISHED, slug, pageRequest);

        return getListModel(contents, String.format("/category/%s", slug));
    }

    /**
     * Return the content page page model.
     *
     * @param contents The page contents.
     * @param urlPrefix The URL prefix.
     * @return The page model.
     */
    private Map<String, ?> getListModel(Page<Content> contents, String urlPrefix) {
        Map<String, Object> model = new HashMap<>();
        model.put("contents", contents);
        addSidebarModel(model);
        model.put("urlPrefix", urlPrefix);

        return model;
    }

    /**
     * Return the model for a content item page.
     *
     * @param id The content item identifier.
     * @return The page model.
     */
    public Map<String, ?> getById(int id) {
        Map<String, Object> model = new HashMap<>();
        Optional<Content> content = contentRepository.findById(id);
        content.ifPresent(c -> model.put("content", c));
        content.orElseThrow(ResourceNotFoundException::new);
        model.put("newComment", new Comment());
        addSidebarModel(model);

        return model;
    }

    /**
     * Return the model for a content item page.
     *
     * @param slug The content slug.
     * @return The page model.
     */
    public Map<String, ?> getBySlug(String slug) {
        Map<String, Object> model = new HashMap<>();
        Optional<Content> content = contentRepository.findOneBySlug(slug);
        content.ifPresent(c -> model.put("content", c));
        content.orElseThrow(ResourceNotFoundException::new);
        model.put("newComment", new Comment());
        addSidebarModel(model);

        return model;
    }

    /**
     * Add model objects needed to display the sidebar.
     *
     * @param model The model.
     */
    private void addSidebarModel(Map<String, Object> model) {
        model.put("archives", contentRepository.countByMonth());
        model.put("categories", categoryRepository.findAllByOrderByNameAsc());
    }

    /**
     * Add a new comment to a content item.
     *
     * @param postId The post identifier.
     * @param comment The comment.
     */
    public void addComment(int postId, Comment comment) {
        Optional<Content> content = contentRepository.findById(postId);
        content.ifPresent(c -> {
            comment.setContent(c);
            c.getComments().add(comment);
            commentRepository.save(comment);
        });
        content.orElseThrow(ResourceNotFoundException::new);
    }

    /**
     * Return a feed with the latest content.
     *
     * @return a feed with the latest content.
     */
    public Feed latestContentFeed() {
        Feed feed = new Feed();
        feed.setFeedType("atom_1.0");
        feed.setTitle("Blog title");
        Link feedLink = new Link();
        feedLink.setHref("http://localhost:8080/");
        feed.setAlternateLinks(Collections.singletonList(feedLink));

        com.rometools.rome.feed.atom.Content subtitle = new com.rometools.rome.feed.atom.Content();
        subtitle.setType("text/plain");
        subtitle.setValue("Blog subtitle");
        feed.setSubtitle(subtitle);

        int postsPerPage = configurationParameterService.getInteger(ConfigurationParameter.Parameter.POSTS_PER_PAGE);
        PageRequest pageRequest = PageRequest.of(0, postsPerPage, Sort.Direction.DESC, "publishedAt");
        Page<Content> contents = contentRepository.findByStatus(ContentStatus.PUBLISHED, pageRequest);

        feed.setEntries(contents.stream().map(c -> {
            Entry entry = new Entry();

            entry.setTitle(c.getTitle());
            Link link = new Link();
            link.setHref("http://localhost:8080/post/" + c.getId());
            entry.setAlternateLinks(Collections.singletonList(link));
            com.rometools.rome.feed.atom.Content summary = new com.rometools.rome.feed.atom.Content();
            summary.setType("text/plain");
            summary.setValue(c.getContent());
            entry.setSummary(summary);
            entry.setCreated(new Date(c.getCreatedAt().toInstant().toEpochMilli()));
            entry.setUpdated(new Date(c.getUpdatedAt().toInstant().toEpochMilli()));
            entry.setPublished(new Date(c.getPublishedAt().toInstant().toEpochMilli()));

            return entry;
        }).collect(Collectors.toList()));

        return feed;
    }
}
