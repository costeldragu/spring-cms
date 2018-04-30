package net.mavroprovato.springcms.controller;

import net.mavroprovato.springcms.service.ContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * The default controller.
 */
@Controller
public class DefaultController {

    /** The content service */
    private final ContentService contentService;

    /**
     * Create the controller.
     *
     * @param contentService The content service.
     */
    @Autowired
    public DefaultController(ContentService contentService) {
        this.contentService = contentService;
    }

    /**
     * Display the index page.
     *
     * @param model The page model.
     * @return The template name.
     */
    @RequestMapping("/")
    public String index(Model model) {
        return indexPage(model, 1);
    }

    /**
     * Display an index page.
     *
     * @param model The page model.
     * @return The template name.
     */
    @RequestMapping("/page/{page}")
    public String indexPage(Model model, @PathVariable("page") int page) {
        model.addAllAttributes(contentService.getContent(page));

        return "index";
    }
}
