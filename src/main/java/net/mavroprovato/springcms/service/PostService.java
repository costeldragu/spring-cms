package net.mavroprovato.springcms.service;

import com.rometools.rome.feed.atom.Entry;
import com.rometools.rome.feed.atom.Feed;
import com.rometools.rome.feed.atom.Link;
import net.mavroprovato.springcms.entity.*;
import net.mavroprovato.springcms.exception.ResourceNotFoundException;
import net.mavroprovato.springcms.repository.CategoryRepository;
import net.mavroprovato.springcms.repository.CommentRepository;
import net.mavroprovato.springcms.repository.PageRepository;
import net.mavroprovato.springcms.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * The post service.
 */
@Service
public class PostService {

    /** The post repository */
    private final PostRepository postRepository;

    /** The page repository */
    private final PageRepository pageRepository;

    /** The category repository */
    private final CategoryRepository categoryRepository;

    /** The comment repository */
    private final CommentRepository commentRepository;

    /** The configuration parameter service */
    private final ConfigurationParameterService configurationParameterService;

    /**
     * Create the post service.
     *
     * @param postRepository The post repository.
     * @param categoryRepository The category repository.
     * @param commentRepository The comment repository.
     * @param configurationParameterService The configuration parameter service.
     */
    @Autowired
    public PostService(PostRepository postRepository, PageRepository pageRepository,
                       CategoryRepository categoryRepository, CommentRepository commentRepository,
                       ConfigurationParameterService configurationParameterService) {
        this.postRepository = postRepository;
        this.pageRepository = pageRepository;
        this.categoryRepository = categoryRepository;
        this.commentRepository = commentRepository;
        this.configurationParameterService = configurationParameterService;
    }

    /**
     * Get a post page, ordered by publication date.
     *
     * @param page The page number.
     * @return The content items.
     */
    public Map<String, ?> list(int page) {
        return listImpl(null, null, null, page);
    }

    /**
     * Get a page of posts published in a year, ordered by publication date.
     *
     * @param year The year.
     * @param page The page number.
     * @return The posts.
     */
    public Map<String, ?> list(int year, int page) {
        return listImpl(year, null, null, page);
    }

    /**
     * Get a page of posts published in a month, ordered by publication date.
     *
     * @param year The year.
     * @param month The month number (1 for January, 12 for December)
     * @param page The page number.
     * @return The posts.
     */
    public Map<String, ?> list(int year, int month, int page) {
        return listImpl(year, month, null, page);
    }

    /**
     * Get a page of posts published in a day, ordered by publication date.
     *
     * @param year The year.
     * @param month The month number (1 for January, 12 for December)
     * @param day The day number.
     * @param page The page number.
     * @return The posts.
     */
    public Map<String, ?> list(int year, int month, int day, int page) {
        return listImpl(year, month, day, page);
    }

    /**
     * Get a page of posts, ordered by publication date.
     *
     * @param year The year.
     * @param month The month number (1 for January, 12 for December)
     * @param day The day number.
     * @param page The page number.
     * @return The posts.
     */
    private Map<String, ?> listImpl(Integer year, Integer month, Integer day, int page) {
        // Calculate the start/end publication date to use for the content query, and the url prefix for the pagination
        // links.
        OffsetDateTime startDateTime = null;
        OffsetDateTime endDateTime = null;
        String urlPrefix = "";
        if (year != null && month != null && day != null) {
            LocalDate date = LocalDate.of(year, month, day);
            startDateTime = OffsetDateTime.of(date, LocalTime.MIN, ZoneOffset.UTC);
            endDateTime = OffsetDateTime.of(date, LocalTime.MAX, ZoneOffset.UTC);
            urlPrefix = String.format("/%02d/%02d/%02d", year, month, day);
        } else if (year != null && month != null) {
            LocalDate startDate = LocalDate.of(year, month, 1);
            startDateTime = OffsetDateTime.of(startDate, LocalTime.MIN, ZoneOffset.UTC);
            LocalDate endDate = startDate.withDayOfMonth(startDate.getMonth().length(startDate.isLeapYear()));
            endDateTime = OffsetDateTime.of(endDate, LocalTime.MAX, ZoneOffset.UTC);
            urlPrefix = String.format("/%02d/%02d", year, month);
        } else if (year != null) {
            LocalDate startDate = LocalDate.of(year, Month.JANUARY.getValue(), 1);
            startDateTime = OffsetDateTime.of(startDate, LocalTime.MIN, ZoneOffset.UTC);
            LocalDate endDate = LocalDate.of(year, Month.DECEMBER.getValue(), 31);
            endDateTime = OffsetDateTime.of(endDate, LocalTime.MIN, ZoneOffset.UTC);
            urlPrefix = String.format("/%02d", year);
        }

        // Run the query
        int postsPerPage = configurationParameterService.getInteger(Parameter.POSTS_PER_PAGE);
        PageRequest pageRequest = PageRequest.of(page - 1, postsPerPage, Sort.Direction.DESC, "publishedAt");
        Page<Post> posts;
        if (startDateTime == null) {
            posts = postRepository.findByStatus(ContentStatus.PUBLISHED, pageRequest);
        } else {
            posts = postRepository.findByStatusAndPublishedAtBetween(
                    ContentStatus.PUBLISHED, startDateTime, endDateTime, pageRequest);
        }

        return getListModel(posts, urlPrefix);
    }

    /**
     * Get a page of posts under a specific tag, specified by its id.
     *
     * @param id The tag identifier.
     * @param page The page.
     * @return The posts.
     */
    public Map<String, ?> listByTagId(int id, int page) {
        // Run the query
        int postsPerPage = configurationParameterService.getInteger(Parameter.POSTS_PER_PAGE);
        PageRequest pageRequest = PageRequest.of(page - 1, postsPerPage, Sort.Direction.DESC, "publishedAt");
        Page<Post> posts = postRepository.findByStatusAndTagsId(ContentStatus.PUBLISHED, id, pageRequest);

        return getListModel(posts, String.format("/tag/%d", id));
    }

    /**
     * Get a page of posts under a specific tag, specified by its slug.
     *
     * @param slug The tag slug.
     * @param page The page.
     * @return The posts.
     */
    public Map<String, ?> listByTagSlug(String slug, int page) {
        // Run the query
        int postsPerPage = configurationParameterService.getInteger(Parameter.POSTS_PER_PAGE);
        PageRequest pageRequest = PageRequest.of(page - 1, postsPerPage, Sort.Direction.DESC, "publishedAt");
        Page<Post> posts = postRepository.findByStatusAndTagsSlug(ContentStatus.PUBLISHED, slug, pageRequest);

        return getListModel(posts, String.format("/tag/%s", slug));
    }

    /**
     * Get a page of posts categorized with a specific category, specified by its id.
     *
     * @param id The category identifier.
     * @param page The page.
     * @return The posts.
     */
    public Map<String, ?> listByCategoryId(int id, int page) {
        // Run the query
        int postsPerPage = configurationParameterService.getInteger(Parameter.POSTS_PER_PAGE);
        PageRequest pageRequest = PageRequest.of(page - 1, postsPerPage, Sort.Direction.DESC, "publishedAt");
        Page<Post> posts = postRepository.findByStatusAndCategoriesId(
                ContentStatus.PUBLISHED, id, pageRequest);

        return getListModel(posts, String.format("/category/%d", id));
    }

    /**
     * Get a page of posts categorized with a specific category, specified by its slug.
     *
     * @param slug The category slug.
     * @param page The page.
     * @return The posts.
     */
    public Map<String, ?> listByCategorySlug(String slug, int page) {
        // Run the query
        int postsPerPage = configurationParameterService.getInteger(Parameter.POSTS_PER_PAGE);
        PageRequest pageRequest = PageRequest.of(page - 1, postsPerPage, Sort.Direction.DESC, "publishedAt");
        Page<Post> posts = postRepository.findByStatusAndCategoriesSlug(
                ContentStatus.PUBLISHED, slug, pageRequest);

        return getListModel(posts, String.format("/category/%s", slug));
    }

    /**
     * Return the posts page model.
     *
     * @param posts The posts.
     * @param urlPrefix The URL prefix.
     * @return The posts page model.
     */
    private Map<String, ?> getListModel(Page<Post> posts, String urlPrefix) {
        Map<String, Object> model = new HashMap<>();
        model.put("posts", posts);
        addCommonModel(model);
        model.put("urlPrefix", urlPrefix);

        return model;
    }

    /**
     * Return the model for a post page.
     *
     * @param id The content item identifier.
     * @return The page model.
     */
    public Map<String, ?> getById(int id) {
        Map<String, Object> model = new HashMap<>();
        Optional<Post> post = postRepository.findById(id);
        post.ifPresent(p -> model.put("post", p));
        post.orElseThrow(ResourceNotFoundException::new);
        addCommonModel(model);

        return model;
    }

    /**
     * Return the model for a post page.
     *
     * @param slug The content slug.
     * @return The post model.
     */
    public Map<String, ?> getBySlug(String slug) {
        Map<String, Object> model = new HashMap<>();
        Optional<Post> post = postRepository.findOneBySlug(slug);
        post.ifPresent(p -> model.put("post", p));
        post.orElseThrow(ResourceNotFoundException::new);
        addCommonModel(model);

        return model;
    }

    /**
     * Add model objects common for all content pages.
     *
     * @param model The model.
     */
    private void addCommonModel(Map<String, Object> model) {
        model.put("archives", postRepository.countByMonth());
        model.put("categories", categoryRepository.findAllByOrderByNameAsc());
        model.put("config", configurationParameterService.allParameters());
        model.put("pages", pageRepository.findAll());
    }

    /**
     * Add a new comment to a content item.
     *
     * @param postId The post identifier.
     * @param comment The comment.
     */
    public void addComment(int postId, Comment comment) {
        Optional<Post> post = postRepository.findById(postId);
        post.ifPresent(p -> {
            comment.setPost(p);
            p.getComments().add(comment);
            commentRepository.save(comment);
        });
        post.orElseThrow(ResourceNotFoundException::new);
    }

    /**
     * Return the post url.
     *
     * @param post The post.
     * @return The post url.
     */
    public String getPostUrl(Post post) {
        if (post.getSlug() == null) {
            return "/post/" + post.getId();
        } else {
            return "/post/" + post.getSlug();
        }
    }

    /**
     * Return the comment url.
     *
     * @param comment The comment.
     * @return The comment url.
     */
    public String getCommentUrl(Comment comment) {
        if (comment.getPost().getSlug() == null) {
            return "/post/" + comment.getPost().getId() + "#comment-" + comment.getId();
        } else {
            return "/post/" + comment.getPost().getSlug() + "#comment-" + comment.getId();
        }
    }

    /**
     * Return a feed with the latest posts.
     *
     * @return a feed with the latest posts.
     */
    public Feed latestPostsFeed() {
        // Get the posts to include
        int postsPerPage = configurationParameterService.getInteger(Parameter.POSTS_PER_PAGE);
        PageRequest pageRequest = PageRequest.of(0, postsPerPage, Sort.Direction.DESC, "publishedAt");
        Page<Post> posts = postRepository.findByStatus(ContentStatus.PUBLISHED, pageRequest);

        return createFeed(posts.stream().map(post -> {
            Entry entry = new Entry();

            entry.setTitle(post.getTitle());
            Link link = new Link();
            link.setHref(getPostUrl(post));
            entry.setAlternateLinks(Collections.singletonList(link));
            com.rometools.rome.feed.atom.Content summary = new com.rometools.rome.feed.atom.Content();
            summary.setType("text/plain");
            summary.setValue(post.getContent());
            entry.setSummary(summary);
            entry.setCreated(new Date(post.getCreatedAt().toInstant().toEpochMilli()));
            entry.setUpdated(new Date(post.getUpdatedAt().toInstant().toEpochMilli()));
            entry.setPublished(new Date(post.getPublishedAt().toInstant().toEpochMilli()));

            return entry;
        }).collect(Collectors.toList()));
    }

    /**
     * Return a feed with the latest comments.
     *
     * @return a feed with the latest comments.
     */
    public Feed latestCommentsFeed() {
        // Get the comments items to include
        int postsPerPage = configurationParameterService.getInteger(Parameter.POSTS_PER_PAGE);
        PageRequest pageRequest = PageRequest.of(0, postsPerPage, Sort.Direction.DESC, "createdAt");
        Page<Comment> comments = commentRepository.findPublished(pageRequest);

        return createFeed(comments.stream().map(comment -> {
            Entry entry = new Entry();

            entry.setTitle("Comment");
            Link link = new Link();
            link.setHref(getCommentUrl(comment));
            entry.setAlternateLinks(Collections.singletonList(link));
            com.rometools.rome.feed.atom.Content summary = new com.rometools.rome.feed.atom.Content();
            summary.setType("text/plain");
            summary.setValue(comment.getComment());
            entry.setSummary(summary);
            entry.setCreated(new Date(comment.getCreatedAt().toInstant().toEpochMilli()));
            entry.setUpdated(new Date(comment.getUpdatedAt().toInstant().toEpochMilli()));

            return entry;
        }).collect(Collectors.toList()));
    }

    /**
     * Create a feed.
     *
     * @param entries The feed entries.
     * @return The feed.
     */
    private Feed createFeed(List<Entry> entries) {
        Feed feed = new Feed();
        feed.setFeedType("atom_1.0");
        feed.setTitle(configurationParameterService.getString(Parameter.TITLE));

        com.rometools.rome.feed.atom.Content subtitle = new com.rometools.rome.feed.atom.Content();
        subtitle.setType("text/plain");
        subtitle.setValue(configurationParameterService.getString(Parameter.SUBTITLE));
        feed.setSubtitle(subtitle);

        Link feedLink = new Link();
        feedLink.setHref("http://localhost:8080/");
        feed.setAlternateLinks(Collections.singletonList(feedLink));
        feed.setEntries(entries);

        return feed;
    }
}
