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
 * The post controller.
 */
@Controller
public class PostController {

    /** The page service */
    private final PostService postService;

    /**
     * Create the controller.
     *
     * @param postService The page service.
     */
    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    /**
     * Display the post by its id.
     *
     * @param id The content id.
     * @return The model and view.
     */
    @GetMapping(value = "/post/{id:\\d+}")
    public ModelAndView byId(@PathVariable("id") int id, @ModelAttribute("newComment") Comment comment) {
        return new ModelAndView("post", postService.getById(id));
    }

    /**
     * Display the post by its slug.
     *
     * @param slug The content slug.
     * @return The model and view.
     */
    @GetMapping("/post/{slug:\\D\\S+}")
    public ModelAndView bySlug(@PathVariable("slug") String slug, @ModelAttribute("newComment") Comment comment) {
        return new ModelAndView("post", postService.getBySlug(slug));
    }

    /**
     * Post a comment to a post.
     *
     * @param id The post identifier.
     * @param comment The submitted comment.
     * @param bindingResult The form binding result.
     * @return The model and view.
     */
    @PostMapping("/post/{id:\\d+}/comment")
    public ModelAndView postComment(@PathVariable("id") int id, @Valid @ModelAttribute("newComment") Comment comment,
                                    BindingResult bindingResult) {
        // Validate the form
        if (bindingResult.hasErrors()) {
            return new ModelAndView("post", postService.getById(id));
        }
        postService.addComment(id, comment);

        return new ModelAndView("redirect:" + postService.getCommentUrl(comment));
    }
}
