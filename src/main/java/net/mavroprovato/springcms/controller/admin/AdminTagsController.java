package net.mavroprovato.springcms.controller.admin;

import net.mavroprovato.springcms.datatables.DataTableRequest;
import net.mavroprovato.springcms.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

/**
 * Controller for administrating tags.
 */
@Controller
@RequestMapping("admin/tags")
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
    public ModelAndView allTags() {
        return new ModelAndView("admin/tags");
    }

    /**
     * Return all tags as a JSON string.
     *
     * @return The response body.
     */
    @GetMapping("list")
    @ResponseBody
    public Map<String, Object> ajaxListTags(DataTableRequest dataTableRequest) {
        return adminService.listTags(dataTableRequest);
    }
}
