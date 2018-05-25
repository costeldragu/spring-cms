package net.mavroprovato.springcms.controller;

import net.mavroprovato.springcms.entity.Comment;
import net.mavroprovato.springcms.service.ContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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

    /**
     * Post a comment to a content item.
     *
     * @param model The page model.
     * @param id The post identifier.
     * @param comment The submitted comment.
     * @param bindingResult The form binding result.
     * @return The template name.
     */
    @PostMapping("/content/{id:\\d+}/comment")
    public String postComment(Model model, @PathVariable("id") int id,
                              @Valid @ModelAttribute("newComment") Comment comment, BindingResult bindingResult) {
        // Validate the form
        if (bindingResult.hasErrors()) {
            model.addAllAttributes(contentService.getById(id));

            return "content";
        }
        contentService.addComment(id, comment);

        return "redirect:/content/" + id;
    }
}
