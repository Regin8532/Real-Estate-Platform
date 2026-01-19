package com.example.realestate.controller;

import com.example.realestate.repository.PropertyRepository;
import com.example.realestate.service.PropertyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final PropertyRepository repo;
    private final PropertyService propertyService;

    @GetMapping("/properties")
    public String approvePage(Model model) {
        model.addAttribute("properties", repo.findAll());
        return "admin-properties";
    }
    @PostMapping("/property/delete/{id}")
    public String deleteProperty(@PathVariable Long id) {
        propertyService.deleteById(id);
        return "redirect:/admin/properties";
    }

    @PostMapping("/approve/{id}")
    public String approve(@PathVariable Long id) {
        propertyService.approve(id);
        return "redirect:/admin/properties";
    }
}
