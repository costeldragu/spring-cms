package net.mavroprovato.springcms.service;

import net.mavroprovato.springcms.entity.Page;
import net.mavroprovato.springcms.exception.ResourceNotFoundException;
import net.mavroprovato.springcms.repository.CategoryRepository;
import net.mavroprovato.springcms.repository.PageRepository;
import net.mavroprovato.springcms.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * The page service.
 */
@Service
@Transactional
public class PageService {

    /** The post repository */
    private final PostRepository postRepository;

    /** The page repository */
    private final PageRepository pageRepository;

    /** The category repository */
    private final CategoryRepository categoryRepository;

    /** The configuration parameter service */
    private final ConfigurationParameterService configurationParameterService;


    /**
     * Create the page service.
     *
     * @param pageRepository The page repository.
     * @param postRepository The post repository.
     * @param categoryRepository The category repository.
     * @param configurationParameterService The configuration parameter service.
     */
    @Autowired
    public PageService(PageRepository pageRepository, PostRepository postRepository,
                       CategoryRepository categoryRepository,
                       ConfigurationParameterService configurationParameterService) {
        this.pageRepository = pageRepository;
        this.postRepository = postRepository;
        this.categoryRepository = categoryRepository;
        this.configurationParameterService = configurationParameterService;
    }

    public Map<String, ?> getById(int id) {
        Map<String, Object> model = new HashMap<>();
        Optional<Page> page = pageRepository.findById(id);
        page.ifPresent(p -> model.put("page", p));
        page.orElseThrow(ResourceNotFoundException::new);
        addCommonModel(model);

        return model;
    }

    public Map<String, ?> getBySlug(String slug) {
        Map<String, Object> model = new HashMap<>();
        Optional<Page> page = pageRepository.findOneBySlug(slug);
        page.ifPresent(p -> model.put("page", p));
        page.orElseThrow(ResourceNotFoundException::new);
        addCommonModel(model);

        return model;
    }

    private void addCommonModel(Map<String,Object> model) {
        model.put("archives", postRepository.countByMonth());
        model.put("categories", categoryRepository.findAllByOrderByNameAsc());
        model.put("config", configurationParameterService.allParameters());
        model.put("pages", pageRepository.findAll());
    }
}
