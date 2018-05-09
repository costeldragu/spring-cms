package net.mavroprovato.springcms.controller;

import net.mavroprovato.springcms.service.ContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

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
    @RequestMapping("/")
    public String index(Model model) {
        return indexPage(model, 1);
    }

    /**
     * Display a page of content items, order by the latest published first.
     *
     * @param model The page model.
     * @param page The page number.
     * @return The page template name.
     */
    @RequestMapping("/page/{page:\\d+}")
    public String indexPage(Model model, @PathVariable("page") int page) {
        model.addAllAttributes(contentService.list(page));

        return "index";
    }

    /**
     * Display the first page of content items published in a year, ordered by the latest published first.
     *
     * @param model The model.
     * @param year The year.
     * @return The template name.
     */
    @RequestMapping("/{year:\\d+}")
    public String year(Model model, @PathVariable("year") int year) {
        return yearPage(model, year, 1);
    }

    /**
     * Display a page of content items published in a year, ordered by the latest published first.
     *
     * @param model The page model.
     * @param year The year.
     * @param page The page number.
     * @return The template name.
     */
    @RequestMapping("/{year:\\d+}/page/{page:\\d+}")
    public String yearPage(Model model, @PathVariable("year") int year, @PathVariable("page") int page) {
        model.addAllAttributes(contentService.list(year, page));

        return "index";
    }

    /**
     * Display the first page of content items published in a month, ordered by the latest published first.
     *
     * @param model The model.
     * @param year The year.
     * @param month The month number (1 for January, 12 for December).
     * @return The template name.
     */
    @RequestMapping("/{year:\\d+}/{month:\\d{1,2}}")
    public String month(Model model, @PathVariable("year") int year, @PathVariable("month") int month) {
        return monthPage(model, year, month, 1);
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
    @RequestMapping("/{year:\\d+}/{month:\\d+}/page/{page:\\d+}")
    public String monthPage(Model model, @PathVariable("year") int year, @PathVariable("month") int month,
                           @PathVariable("page") int page) {
        model.addAllAttributes(contentService.list(year, month, page));

        return "index";
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
    @RequestMapping("/{year:\\d+}/{month:\\d+}/{day:\\d+}")
    public String day(Model model, @PathVariable("year") int year, @PathVariable("month") int month,
                      @PathVariable("day") int day) {
        return dayPage(model, year, month, day, 1);
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
    @RequestMapping("/{year:\\d+}/{month:\\d+}/{day:\\d+}/page/{page:\\d+}")
    public String dayPage(Model model, @PathVariable("year") int year, @PathVariable("month") int month,
                          @PathVariable("day") int day, @PathVariable("page") int page) {
        model.addAllAttributes(contentService.list(year, month, day, page));

        return "index";
    }
}
