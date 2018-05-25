package net.mavroprovato.springcms.controller;

import net.mavroprovato.springcms.entity.Comment;
import net.mavroprovato.springcms.service.ContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

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
     * @param id The content id.
     * @return The model and view.
     */
    @GetMapping(value = "/content/{id:\\d+}")
    public ModelAndView getById(@PathVariable("id") int id) {
        return new ModelAndView("content", contentService.getById(id));
    }

    /**
     * Display the content by its slug.
     *
     * @param slug The content slug.
     * @return The model and view.
     */
    @GetMapping("/content/{slug:\\D\\S+}")
    public ModelAndView getBySlug(@PathVariable("slug") String slug) {
        return new ModelAndView("content", contentService.getBySlug(slug));
    }

    /**
     * Post a comment to a content item.
     *
     * @param id The post identifier.
     * @param comment The submitted comment.
     * @param bindingResult The form binding result.
     * @return The model and view.
     */
    @PostMapping("/content/{id:\\d+}/comment")
    public ModelAndView postComment(@PathVariable("id") int id, @Valid @ModelAttribute("newComment") Comment comment,
                                    BindingResult bindingResult) {
        // Validate the form
        if (bindingResult.hasErrors()) {
            return new ModelAndView("content", contentService.getById(id));
        }
        contentService.addComment(id, comment);

        return new ModelAndView("redirect:/content/" + id);
    }
}
