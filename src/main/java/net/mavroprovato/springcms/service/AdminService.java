package net.mavroprovato.springcms.service;

import net.mavroprovato.springcms.entity.Parameter;
import net.mavroprovato.springcms.repository.CommentRepository;
import net.mavroprovato.springcms.repository.PageRepository;
import net.mavroprovato.springcms.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * The administration service.
 */
@Service
@Transactional
public class AdminService {

    /** The post repository */
    private final PostRepository postRepository;

    /** The page repository */
    private final PageRepository pageRepository;

    /** The comment repository */
    private final CommentRepository commentRepository;

    /**
     * Create the administration service.
     *
     * @param postRepository The post repository.
     * @param pageRepository The page repository.
     * @param commentRepository The comment repository.
     */
    @Autowired
    public AdminService(PostRepository postRepository, PageRepository pageRepository,
                        CommentRepository commentRepository) {
        this.postRepository = postRepository;
        this.pageRepository = pageRepository;
        this.commentRepository = commentRepository;
    }

    /**
     * Get the model for the dashboard page.
     *
     * @return The model for the dashboard page.
     */
    public Map<String, ?> dashboard() {
        Map<String, Object> model = new HashMap<>();

        model.put("post_count", postRepository.count());
        model.put("page_count", pageRepository.count());
        model.put("comment_count", commentRepository.count());

        return model;
    }

    /**
     * Return posts in order to be displayed in a data table.
     *
     * @return Posts in order to be displayed in a data table.
     */
    public Map<String, Object> listAllPosts() {
        Map<String, Object> model = new HashMap<>();
        model.put("draw", 1);
        model.put("recordsTotal", postRepository.count());
        model.put("recordsFiltered", postRepository.count());
        PageRequest pageRequest = PageRequest.of(0, 10, Sort.Direction.DESC, "updatedAt");
        model.put("data", postRepository.findAll(pageRequest).stream().map(p -> new Object[] {
                p.getTitle(), p.getAuthor().getUserName(), p.getUpdatedAt()
        }));

        return model;
    }
}
