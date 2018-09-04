package net.mavroprovato.springcms.service;

import net.mavroprovato.springcms.datatables.DataTableRequest;
import net.mavroprovato.springcms.repository.CategoryRepository;
import net.mavroprovato.springcms.repository.CommentRepository;
import net.mavroprovato.springcms.repository.PageRepository;
import net.mavroprovato.springcms.repository.PostRepository;
import net.mavroprovato.springcms.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
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

    /** The categories repository */
    private final CategoryRepository categoryRepository;

    /** The tags repository */
    private final TagRepository tagRepository;

    /** The comment repository */
    private final CommentRepository commentRepository;

    /**
     * Create the administration service.
     *
     * @param postRepository The post repository.
     * @param pageRepository The page repository.
     * @param categoryRepository The category repository.
     * @param tagRepository The tag repository.
     * @param commentRepository The comment repository.
     */
    @Autowired
    public AdminService(PostRepository postRepository, PageRepository pageRepository,
                        CategoryRepository categoryRepository, TagRepository tagRepository,
                        CommentRepository commentRepository) {
        this.postRepository = postRepository;
        this.pageRepository = pageRepository;
        this.categoryRepository = categoryRepository;
        this.tagRepository = tagRepository;
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
    public Map<String, Object> listPosts(DataTableRequest dataTableRequest) {
        return getDataTablesModel(dataTableRequest, postRepository);
    }

    /**
     * Return pages in order to be displayed in a data table.
     *
     * @return Pages in order to be displayed in a data table.
     */
    public Map<String, Object> listPages(DataTableRequest dataTableRequest) {
        return getDataTablesModel(dataTableRequest, pageRepository);
    }

    /**
     * Return categories in order to be displayed in a data table.
     *
     * @return Categories in order to be displayed in a data table.
     */
    public Map<String, Object> listCategories(DataTableRequest dataTableRequest) {
        return getDataTablesModel(dataTableRequest, categoryRepository);
    }

    /**
     * Return tags in order to be displayed in a data table.
     *
     * @return Tags in order to be displayed in a data table.
     */
    public Map<String, Object> listTags(DataTableRequest dataTableRequest) {
        return getDataTablesModel(dataTableRequest, tagRepository);
    }

    private Map<String, Object> getDataTablesModel(DataTableRequest dataTableRequest,
                                                   PagingAndSortingRepository<?, ?> repository) {
        Map<String, Object> model = new HashMap<>();
        model.put("draw", dataTableRequest.getDraw());
        model.put("recordsTotal", repository.count());
        model.put("recordsFiltered", repository.count());
        model.put("data", repository.findAll(dataTableRequest.getPageRequest()).getContent());

        return model;
    }
}
