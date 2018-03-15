package net.mavroprovato.springcms.controller;

import net.mavroprovato.springcms.repository.ContentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class DefaultController {

    private final ContentRepository contentRepository;

    @Autowired
    public DefaultController(ContentRepository contentRepository) {
        this.contentRepository = contentRepository;
    }

    @RequestMapping("/")
    public String index(Model model) {
        PageRequest pageRequest = PageRequest.of(0, 10, Sort.Direction.DESC, "publishedAt");
        model.addAttribute("contents", contentRepository.findAll(pageRequest));

        return "index";
    }
}
