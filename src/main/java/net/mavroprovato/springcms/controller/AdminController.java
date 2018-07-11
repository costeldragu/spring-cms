package net.mavroprovato.springcms.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Controller for administration actions.
 */
@Controller
public class AdminController {

    @GetMapping("admin")
    public String login() {
        return "admin/dashboard";
    }
}
