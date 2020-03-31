package ua.com.virtonomica.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RedirectController {

    @GetMapping("/admin")
    private String adminPage(Model model){

        return "admin";
    }
}
