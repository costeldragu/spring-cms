package net.mavroprovato.springcms.controller;

import net.mavroprovato.springcms.entity.Comment;
import net.mavroprovato.springcms.service.ContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * The content controller.
 */
@Controller
public class ContentController {

    /** The content service */
    private final ContentService contentService;

    /**
     * Create the controller.
     *
     * @param contentService The content service.
     */
    @Autowired
    public ContentController(ContentService contentService) {
        this.contentService = contentService;
    }

    /**
     * Display the first page of content items, ordered by the latest published first.
     *
     * @param model The page model.
     * @return The page template name.
     */
    @GetMapping(value = "/")
    public String list(Model model) {
        return listPage(model, 1);
    }

    /**
     * Display a page of content items, order by the latest published first.
     *
     * @param model The page model.
     * @param page The page number.
     * @return The page template name.
     */
    @GetMapping(value = "/page/{page:\\d+}")
    public String listPage(Model model, @PathVariable("page") int page) {
        model.addAllAttributes(contentService.list(page));

        return "contents";
    }

    /**
     * Display the first page of content items published in a year, ordered by the latest published first.
     *
     * @param model The model.
     * @param year The year.
     * @return The template name.
     */
    @GetMapping(value = "/{year:\\d+}")
    public String listYear(Model model, @PathVariable("year") int year) {
        return listYearPage(model, year, 1);
    }

    /**
     * Display a page of content items published in a year, ordered by the latest published first.
     *
     * @param model The page model.
     * @param year The year.
     * @param page The page number.
     * @return The template name.
     */
    @GetMapping(value = "/{year:\\d+}/page/{page:\\d+}")
    public String listYearPage(Model model, @PathVariable("year") int year, @PathVariable("page") int page) {
        model.addAllAttributes(contentService.list(year, page));

        return "contents";
    }

    /**
     * Display the first page of content items published in a month, ordered by the latest published first.
     *
     * @param model The model.
     * @param year The year.
     * @param month The month number (1 for January, 12 for December).
     * @return The template name.
     */
    @GetMapping(value = "/{year:\\d+}/{month:\\d{1,2}}")
    public String listMonth(Model model, @PathVariable("year") int year, @PathVariable("month") int month) {
        return listMonthPage(model, year, month, 1);
    }

    /**
     * Display a page of content items published in a month, ordered by the latest published first.
     *
     * @param model The model.
     * @param year The year.
     * @param month The month number (1 for January, 12 for December).
     * @param page The page number.
     * @return The template name.
     */
    @GetMapping(value = "/{year:\\d+}/{month:\\d+}/page/{page:\\d+}")
    public String listMonthPage(Model model, @PathVariable("year") int year, @PathVariable("month") int month,
                                @PathVariable("page") int page) {
        model.addAllAttributes(contentService.list(year, month, page));

        return "contents";
    }

    /**
     * Display the first page of content items published in a month, ordered by the latest published first.
     *
     * @param model The model.
     * @param year The year.
     * @param month The month number (1 for January, 12 for December).
     * @param day The day number.
     * @return The template name.
     */
    @GetMapping(value = "/{year:\\d+}/{month:\\d+}/{day:\\d+}")
    public String listDay(Model model, @PathVariable("year") int year, @PathVariable("month") int month,
                          @PathVariable("day") int day) {
        return listDayPage(model, year, month, day, 1);
    }

    /**
     * Display a page of content items published in a month, ordered by the latest published first.
     *
     * @param model The model.
     * @param year The year.
     * @param month The month number (1 for January, 12 for December).
     * @param day The day number.
     * @param page The page number.
     * @return The template name.
     */
    @GetMapping(value = "/{year:\\d+}/{month:\\d+}/{day:\\d+}/page/{page:\\d+}")
    public String listDayPage(Model model, @PathVariable("year") int year, @PathVariable("month") int month,
                              @PathVariable("day") int day, @PathVariable("page") int page) {
        model.addAllAttributes(contentService.list(year, month, day, page));

        return "contents";
    }

    /**
     * Display the first page of content items that use the provided tag. The tag is determined by its unique
     * identifier.
     *
     * @param model The model.
     * @param id The tag identifier.
     * @return The template name.
     */
    @GetMapping(value = "/tag/{id:\\d+}")
    public String listByTagId(Model model, @PathVariable("id") int id) {
        return listByTagIdPage(model, id, 1);
    }

    /**
     * Display a page of content items that use the provided tag.
     *
     * @param model The model.
     * @param id The tag identifier.
     * @param page The page number.
     * @return The template name.
     */
    @GetMapping(value = "/tag/{id:\\d+}/page/{page:\\d+}")
    public String listByTagIdPage(Model model, @PathVariable("id") int id, @PathVariable("page") int page) {
        model.addAllAttributes(contentService.listByTagId(id, page));

        return "contents";
    }

    /**
     * Display the first page of content items that use the provided tag. The tag is determined by its slug.
     *
     * @param model The model.
     * @param slug The tag slug.
     * @return The template name.
     */
    @GetMapping(value = "/tag/{slug:\\D\\S+}")
    public String listByTagSlug(Model model, @PathVariable("slug") String slug) {
        return listByTagSlugPage(model, slug, 1);
    }

    /**
     * Display a page of content items that use the provided tag.
     *
     * @param model The model.
     * @param slug The tag slug.
     * @param page The page number.
     * @return The template name.
     */
    @GetMapping(value = "/tag/{slug:\\D\\S+}/page/{page:\\d+}")
    public String listByTagSlugPage(Model model, @PathVariable("slug") String slug, @PathVariable("page") int page) {
        model.addAllAttributes(contentService.listByTagSlug(slug, page));

        return "contents";
    }

    /**
     * Display the first page of content items that are categorized under the specified category.
     *
     * @param model The model.
     * @param id The category identifier.
     * @return The template name.
     */
    @GetMapping(value = "/category/{id:\\d+}")
    public String listByCategoryId(Model model, @PathVariable("id") int id) {
        return listByCategoryIdPage(model, id, 1);
    }

    /**
     * Display a page of content items that are categorized under the specified category.
     *
     * @param model The model.
     * @param id The category identifier.
     * @param page The page number.
     * @return The template name.
     */
    @GetMapping(value = "/category/{id:\\d+}/page/{page:\\d+}")
    public String listByCategoryIdPage(Model model, @PathVariable("id") int id, @PathVariable("page") int page) {
        model.addAllAttributes(contentService.listByCategoryId(id, page));

        return "contents";
    }

    /**
     * Display the first page of content items that use the provided tag. The tag is determined by its slug.
     *
     * @param model The model.
     * @param slug The tag slug.
     * @return The template name.
     */
    @GetMapping(value = "/category/{slug:\\D\\S+}")
    public String listByCategorySlug(Model model, @PathVariable("slug") String slug) {
        return listByCategorySlugPage(model, slug, 1);
    }

    /**
     * Display a page of content items that use the provided tag.
     *
     * @param model The model.
     * @param slug The tag slug.
     * @param page The page number.
     * @return The template name.
     */
    @GetMapping(value = "/category/{slug:\\D\\S+}/page/{page:\\d+}")
    public String listByCategorySlugPage(Model model, @PathVariable("slug") String slug, @PathVariable("page") int page) {
        model.addAllAttributes(contentService.listByCategorySlug(slug, page));

        return "contents";
    }

    /**
     * Display the content by its id.
     *
     * @param model The model.
     * @param id The content id.
     * @return The template name.
     */
    @GetMapping(value = "/content/{id:\\d+}")
    public String getById(Model model, @PathVariable("id") int id) {
        model.addAllAttributes(contentService.getById(id));

        return "content";
    }

    /**
     * Display the content by its slug.
     *
     * @param model The model.
     * @param slug The content slug.
     * @return The template name.
     */
    @GetMapping("/content/{slug:\\D\\S+}")
    public String getBySlug(Model model, @PathVariable("slug") String slug) {
        model.addAllAttributes(contentService.getBySlug(slug));

        return "content";
    }

}
