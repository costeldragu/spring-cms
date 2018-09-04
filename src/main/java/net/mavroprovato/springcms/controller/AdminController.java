package net.mavroprovato.springcms.controller;

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
 * Controller for administration actions.
 */
@Controller
@RequestMapping("admin")
public class AdminController {

    /** The admin service. */
    private final AdminService adminService;

    /**
     * Create the admin controller.
     *
     * @param adminService The admin service.
     */
    @Autowired
    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    /**
     * Display the admin dashboard.
     *
     * @return The model and view.
     */
    @GetMapping("")
    public ModelAndView dashboard() {
        return new ModelAndView("admin/dashboard", adminService.dashboard());
    }

    /**
     * Display the list of posts.
     *
     * @return The model and view.
     */
    @GetMapping("posts")
    public ModelAndView allPosts() {
        return new ModelAndView("admin/posts");
    }

    /**
     * Return all posts as a JSON string.
     *
     * @return The response body.
     */
    @GetMapping("post/list")
    @ResponseBody
    public Map<String, Object> ajaxListPosts(DataTableRequest dataTableRequest) {
        return adminService.listPosts(dataTableRequest);
    }

    /**
     * Display the list of pages.
     *
     * @return The model and view.
     */
    @GetMapping("pages")
    public ModelAndView allPages() {
        return new ModelAndView("admin/pages");
    }

    /**
     * Return all posts as a JSON string.
     *
     * @return The response body.
     */
    @GetMapping("pages/list")
    @ResponseBody
    public Map<String, Object> ajaxListPages(DataTableRequest dataTableRequest) {
        return adminService.listPages(dataTableRequest);
    }

    /**
     * Display the list of categories.
     *
     * @return The model and view.
     */
    @GetMapping("categories")
    public ModelAndView allCategories() {
        return new ModelAndView("admin/categories");
    }

    /**
     * Return all posts as a JSON string.
     *
     * @return The response body.
     */
    @GetMapping("categories/list")
    @ResponseBody
    public Map<String, Object> ajaxListCategories(DataTableRequest dataTableRequest) {
        return adminService.listCategories(dataTableRequest);
    }
}
