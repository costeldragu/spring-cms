package net.mavroprovato.springcms.controller;

import net.mavroprovato.springcms.service.ContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

/**
 * The content list controller.
 */
@Controller
public class ContentListController {

    /** The content service */
    private final ContentService contentService;

    /**
     * Create the controller.
     *
     * @param contentService The content service.
     */
    @Autowired
    public ContentListController(ContentService contentService) {
        this.contentService = contentService;
    }

    /**
     * Display the first page of content items, ordered by the latest published first.
     *
     * @return The page template name.
     */
    @GetMapping(value = "/")
    public ModelAndView list() {
        return listPage(1);
    }

    /**
     * Display a page of content items, order by the latest published first.
     *
     * @param page The page number.
     * @return The model and view.
     */
    @GetMapping(value = "/page/{page:\\d+}")
    public ModelAndView listPage(@PathVariable("page") int page) {
        return new ModelAndView("contents", contentService.list(page));
    }

    /**
     * Display the first page of content items published in a year, ordered by the latest published first.
     *
     * @param year The year.
     * @return The model and view.
     */
    @GetMapping(value = "/{year:\\d+}")
    public ModelAndView listYear(@PathVariable("year") int year) {
        return listYearPage(year, 1);
    }

    /**
     * Display a page of content items published in a year, ordered by the latest published first.
     *
     * @param year The year.
     * @param page The page number.
     * @return The model and view.
     */
    @GetMapping(value = "/{year:\\d+}/page/{page:\\d+}")
    public ModelAndView listYearPage(@PathVariable("year") int year, @PathVariable("page") int page) {
        return new ModelAndView("contents", contentService.list(year, page));
    }

    /**
     * Display the first page of content items published in a month, ordered by the latest published first.
     *
     * @param year The year.
     * @param month The month number (1 for January, 12 for December).
     * @return The model and view.
     */
    @GetMapping(value = "/{year:\\d+}/{month:\\d{1,2}}")
    public ModelAndView listMonth(@PathVariable("year") int year, @PathVariable("month") int month) {
        return listMonthPage(year, month, 1);
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
    public ModelAndView listMonthPage(@PathVariable("year") int year, @PathVariable("month") int month,
                                @PathVariable("page") int page) {
        return new ModelAndView("contents", contentService.list(year, month, page));
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
    public ModelAndView listDay(@PathVariable("year") int year, @PathVariable("month") int month,
                          @PathVariable("day") int day) {
        return listDayPage(year, month, day, 1);
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
    public ModelAndView listDayPage(@PathVariable("year") int year, @PathVariable("month") int month,
                              @PathVariable("day") int day, @PathVariable("page") int page) {
        return new ModelAndView("contents", contentService.list(year, month, day, page));
    }

    /**
     * Display the first page of content items that use the provided tag. The tag is determined by its unique
     * identifier.
     *
     * @param id The tag identifier.
     * @return The model and view.
     */
    @GetMapping(value = "/tag/{id:\\d+}")
    public ModelAndView listByTagId(@PathVariable("id") int id) {
        return listByTagIdPage(id, 1);
    }

    /**
     * Display a page of content items that use the provided tag.
     *
     * @param id The tag identifier.
     * @param page The page number.
     * @return The model and view.
     */
    @GetMapping(value = "/tag/{id:\\d+}/page/{page:\\d+}")
    public ModelAndView listByTagIdPage(@PathVariable("id") int id, @PathVariable("page") int page) {
        return new ModelAndView("contents", contentService.listByTagId(id, page));
    }

    /**
     * Display the first page of content items that use the provided tag. The tag is determined by its slug.
     *
     * @param slug The tag slug.
     * @return The model and view.
     */
    @GetMapping(value = "/tag/{slug:\\D\\S+}")
    public ModelAndView listByTagSlug(@PathVariable("slug") String slug) {
        return listByTagSlugPage(slug, 1);
    }

    /**
     * Display a page of content items that use the provided tag.
     *
     * @param slug The tag slug.
     * @param page The page number.
     * @return The model and view.
     */
    @GetMapping(value = "/tag/{slug:\\D\\S+}/page/{page:\\d+}")
    public ModelAndView listByTagSlugPage(@PathVariable("slug") String slug, @PathVariable("page") int page) {
        return new ModelAndView("contents", contentService.listByTagSlug(slug, page));
    }

    /**
     * Display the first page of content items that are categorized under the specified category.
     *
     * @param id The category identifier.
     * @return The model and view.
     */
    @GetMapping(value = "/category/{id:\\d+}")
    public ModelAndView listByCategoryId(@PathVariable("id") int id) {
        return listByCategoryIdPage(id, 1);
    }

    /**
     * Display a page of content items that are categorized under the specified category.
     *
     * @param id The category identifier.
     * @param page The page number.
     * @return The model and view.
     */
    @GetMapping(value = "/category/{id:\\d+}/page/{page:\\d+}")
    public ModelAndView listByCategoryIdPage(@PathVariable("id") int id, @PathVariable("page") int page) {
        return new ModelAndView("contents", contentService.listByCategoryId(id, page));
    }

    /**
     * Display the first page of content items that use the provided tag. The tag is determined by its slug.
     *
     * @param slug The tag slug.
     * @return The model and view.
     */
    @GetMapping(value = "/category/{slug:\\D\\S+}")
    public ModelAndView listByCategorySlug(@PathVariable("slug") String slug) {
        return listByCategorySlugPage(slug, 1);
    }

    /**
     * Display a page of content items that use the provided tag.
     *
     * @param slug The tag slug.
     * @param page The page number.
     * @return The model and view.
     */
    @GetMapping(value = "/category/{slug:\\D\\S+}/page/{page:\\d+}")
    public ModelAndView listByCategorySlugPage(@PathVariable("slug") String slug, @PathVariable("page") int page) {
        return new ModelAndView("contents", contentService.listByCategorySlug(slug, page));
    }
}
