package net.mavroprovato.springcms.controller;

import com.rometools.rome.feed.atom.Feed;
import net.mavroprovato.springcms.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * The content page controller.
 */
@Controller
public class PostListController {

    /** The content service */
    private final PostService contentService;

    /**
     * Create the controller.
     *
     * @param postService The content service.
     */
    @Autowired
    public PostListController(PostService postService) {
        this.contentService = postService;
    }

    /**
     * Display the first page of content items, ordered by the latest published first.
     *
     * @return The page template name.
     */
    @GetMapping(value = "/")
    public ModelAndView page() {
        return page(1);
    }

    /**
     * Display a page of content items, order by the latest published first.
     *
     * @param page The page number.
     * @return The model and view.
     */
    @GetMapping(value = "/page/{page:\\d+}")
    public ModelAndView page(@PathVariable("page") int page) {
        return new ModelAndView("posts", contentService.list(page));
    }

    /**
     * Display the first page of content items published in a year, ordered by the latest published first.
     *
     * @param year The year.
     * @return The model and view.
     */
    @GetMapping(value = "/{year:\\d+}")
    public ModelAndView year(@PathVariable("year") int year) {
        return yearPage(year, 1);
    }

    /**
     * Display a page of content items published in a year, ordered by the latest published first.
     *
     * @param year The year.
     * @param page The page number.
     * @return The model and view.
     */
    @GetMapping(value = "/{year:\\d+}/page/{page:\\d+}")
    public ModelAndView yearPage(@PathVariable("year") int year, @PathVariable("page") int page) {
        return new ModelAndView("posts", contentService.list(year, page));
    }

    /**
     * Display the first page of content items published in a month, ordered by the latest published first.
     *
     * @param year The year.
     * @param month The month number (1 for January, 12 for December).
     * @return The model and view.
     */
    @GetMapping(value = "/{year:\\d+}/{month:\\d{1,2}}")
    public ModelAndView month(@PathVariable("year") int year, @PathVariable("month") int month) {
        return monthPage(year, month, 1);
    }

    /**
     * Display a page of content items published in a month, ordered by the latest published first.
     *
     * @param year The year.
     * @param month The month number (1 for January, 12 for December).
     * @param page The page number.
     * @return The model and view.
     */
    @GetMapping(value = "/{year:\\d+}/{month:\\d+}/page/{page:\\d+}")
    public ModelAndView monthPage(@PathVariable("year") int year, @PathVariable("month") int month,
                                  @PathVariable("page") int page) {
        return new ModelAndView("posts", contentService.list(year, month, page));
    }

    /**
     * Display the first page of content items published in a month, ordered by the latest published first.
     *
     * @param year The year.
     * @param month The month number (1 for January, 12 for December).
     * @param day The day number.
     * @return The model and view.
     */
    @GetMapping(value = "/{year:\\d+}/{month:\\d+}/{day:\\d+}")
    public ModelAndView day(@PathVariable("year") int year, @PathVariable("month") int month,
                            @PathVariable("day") int day) {
        return dayPage(year, month, day, 1);
    }

    /**
     * Display a page of content items published in a month, ordered by the latest published first.
     *
     * @param year The year.
     * @param month The month number (1 for January, 12 for December).
     * @param day The day number.
     * @param page The page number.
     * @return The model and view.
     */
    @GetMapping(value = "/{year:\\d+}/{month:\\d+}/{day:\\d+}/page/{page:\\d+}")
    public ModelAndView dayPage(@PathVariable("year") int year, @PathVariable("month") int month,
                                @PathVariable("day") int day, @PathVariable("page") int page) {
        return new ModelAndView("posts", contentService.list(year, month, day, page));
    }

    /**
     * Display the first page of content items that use the provided tag. The tag is determined by its unique
     * identifier.
     *
     * @param id The tag identifier.
     * @return The model and view.
     */
    @GetMapping(value = "/tag/{id:\\d+}")
    public ModelAndView byTagId(@PathVariable("id") int id) {
        return byTagIdPage(id, 1);
    }

    /**
     * Display a page of content items that use the provided tag.
     *
     * @param id The tag identifier.
     * @param page The page number.
     * @return The model and view.
     */
    @GetMapping(value = "/tag/{id:\\d+}/page/{page:\\d+}")
    public ModelAndView byTagIdPage(@PathVariable("id") int id, @PathVariable("page") int page) {
        return new ModelAndView("posts", contentService.listByTagId(id, page));
    }

    /**
     * Display the first page of content items that use the provided tag. The tag is determined by its slug.
     *
     * @param slug The tag slug.
     * @return The model and view.
     */
    @GetMapping(value = "/tag/{slug:\\D\\S+}")
    public ModelAndView byTagSlug(@PathVariable("slug") String slug) {
        return byTagSlugPage(slug, 1);
    }

    /**
     * Display a page of content items that use the provided tag.
     *
     * @param slug The tag slug.
     * @param page The page number.
     * @return The model and view.
     */
    @GetMapping(value = "/tag/{slug:\\D\\S+}/page/{page:\\d+}")
    public ModelAndView byTagSlugPage(@PathVariable("slug") String slug, @PathVariable("page") int page) {
        return new ModelAndView("posts", contentService.listByTagSlug(slug, page));
    }

    /**
     * Display the first page of content items that are categorized under the specified category.
     *
     * @param id The category identifier.
     * @return The model and view.
     */
    @GetMapping(value = "/category/{id:\\d+}")
    public ModelAndView byCategoryId(@PathVariable("id") int id) {
        return byCategoryIdPage(id, 1);
    }

    /**
     * Display a page of content items that are categorized under the specified category.
     *
     * @param id The category identifier.
     * @param page The page number.
     * @return The model and view.
     */
    @GetMapping(value = "/category/{id:\\d+}/page/{page:\\d+}")
    public ModelAndView byCategoryIdPage(@PathVariable("id") int id, @PathVariable("page") int page) {
        return new ModelAndView("posts", contentService.listByCategoryId(id, page));
    }

    /**
     * Display the first page of content items that use the provided tag. The tag is determined by its slug.
     *
     * @param slug The tag slug.
     * @return The model and view.
     */
    @GetMapping(value = "/category/{slug:\\D\\S+}")
    public ModelAndView byCategorySlug(@PathVariable("slug") String slug) {
        return byCategorySlugPage(slug, 1);
    }

    /**
     * Display a page of content items that use the provided tag.
     *
     * @param slug The tag slug.
     * @param page The page number.
     * @return The model and view.
     */
    @GetMapping(value = "/category/{slug:\\D\\S+}/page/{page:\\d+}")
    public ModelAndView byCategorySlugPage(@PathVariable("slug") String slug, @PathVariable("page") int page) {
        return new ModelAndView("posts", contentService.listByCategorySlug(slug, page));
    }

    /**
     * Return the feed with the latest posts.
     */
    @GetMapping(value = "/feed")
    @ResponseBody
    public Feed feed() {
        return contentService.latestPostsFeed();
    }

    /**
     * Return the feed with the latest posts.
     */
    @GetMapping(value = "/comments/feed")
    @ResponseBody
    public Feed commentsFeed() {
        return contentService.latestCommentsFeed();
    }
}
