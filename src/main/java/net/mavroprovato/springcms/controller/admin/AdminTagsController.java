package net.mavroprovato.springcms.controller.admin;

import net.mavroprovato.springcms.datatables.DataTableRequest;
import net.mavroprovato.springcms.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

/**
 * Controller for administrating tags.
 */
@Controller
@RequestMapping("/admin/tags")
public class AdminTagsController {

    /** The admin service. */
    private final AdminService adminService;

    /**
     * Create the admin tags controller.
     *
     * @param adminService The admin service.
     */
    @Autowired
    public AdminTagsController(AdminService adminService) {
        this.adminService = adminService;
    }

    /**
     * Display the list of tags.
     *
     * @return The model and view.
     */
    @GetMapping("")
    public ModelAndView listTags() {
        return new ModelAndView("admin/tags");
    }

    /**
     * Return all tags as a JSON string.
     *
     * @return The response body.
     */
    @GetMapping("/list")
    @ResponseBody
    public Map<String, Object> ajaxListTags(DataTableRequest dataTableRequest) {
        return adminService.listTags(dataTableRequest);
    }

    /**
     * Display the page to edit a tag.
     *
     * @param id The tag identifier.
     * @return The model and view needed to display the page.
     */
    @GetMapping("/{id:\\d+}")
    public ModelAndView tag(@PathVariable("id") Integer id) {
        return new ModelAndView("admin/tag", adminService.tag(id));
    }
}
