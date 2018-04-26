package net.mavroprovato.springcms.controller;

import net.mavroprovato.springcms.repository.ContentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * The default controller.
 */
@Controller
public class DefaultController {

    /** The content repository */
    private final ContentRepository contentRepository;

    /**
     * Create the controller.
     *
     * @param contentRepository The content repository.
     */
    @Autowired
    public DefaultController(ContentRepository contentRepository) {
        this.contentRepository = contentRepository;
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
        PageRequest pageRequest = PageRequest.of(page - 1, 10, Sort.Direction.DESC, "publishedAt");
        model.addAttribute("contents", contentRepository.findAll(pageRequest));
        model.addAttribute("archives", contentRepository.countByMonth());

        return "index";
    }
}
