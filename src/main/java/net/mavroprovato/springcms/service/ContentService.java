package net.mavroprovato.springcms.service;

import net.mavroprovato.springcms.repository.ContentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class ContentService {

    /** The content repository */
    private final ContentRepository contentRepository;

    @Autowired
    public ContentService(ContentRepository contentRepository) {
        this.contentRepository = contentRepository;
    }

    public Map<String, ?> getContent(int page) {
        Map<String, Object> model = new HashMap<>();
        PageRequest pageRequest = PageRequest.of(page - 1, 10, Sort.Direction.DESC, "publishedAt");

        model.put("contents", contentRepository.findAll(pageRequest));
        model.put("archives", contentRepository.countByMonth());

        return model;
    }
}
