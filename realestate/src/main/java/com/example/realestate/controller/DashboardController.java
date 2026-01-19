package com.example.realestate.controller;

import com.example.realestate.entity.User;
import com.example.realestate.service.PropertyService;
import com.example.realestate.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class DashboardController {

    private final PropertyService propertyService;
    private final UserService userService;

    @GetMapping("/dashboard")
    public String dashboard(Authentication auth, Model model) {
        User user = userService.getByEmail(auth.getName());
        model.addAttribute("properties", propertyService.myProperties(user));
        return "dashboard";
    }
}
