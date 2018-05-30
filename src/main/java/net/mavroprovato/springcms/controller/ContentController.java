package net.mavroprovato.springcms.controller;

import net.mavroprovato.springcms.entity.Comment;
import net.mavroprovato.springcms.service.PostService;
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
    private final PostService postService;

    /**
     * Create the controller.
     *
     * @param postService The content service.
     */
    @Autowired
    public ContentController(PostService postService) {
        this.postService = postService;
    }

    /**
     * Display the content by its id.
     *
     * @param id The content id.
     * @return The model and view.
     */
    @GetMapping(value = "/content/{id:\\d+}")
    public ModelAndView byId(@PathVariable("id") int id) {
        return new ModelAndView("content", postService.getById(id));
    }

    /**
     * Display the content by its slug.
     *
     * @param slug The content slug.
     * @return The model and view.
     */
    @GetMapping("/content/{slug:\\D\\S+}")
    public ModelAndView bySlug(@PathVariable("slug") String slug) {
        return new ModelAndView("content", postService.getBySlug(slug));
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
            return new ModelAndView("content", postService.getById(id));
        }
        postService.addComment(id, comment);

        return new ModelAndView("redirect:/content/" + id);
    }
}
