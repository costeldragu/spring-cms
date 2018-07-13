package net.mavroprovato.springcms.controller;

import net.mavroprovato.springcms.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Controller for administration actions.
 */
@Controller
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
    @GetMapping("admin")
    public ModelAndView dashboard() {
        return new ModelAndView("admin/dashboard", adminService.dashboard());
    }
}
