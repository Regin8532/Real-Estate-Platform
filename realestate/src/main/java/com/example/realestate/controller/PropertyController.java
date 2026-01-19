package com.example.realestate.controller;

import com.example.realestate.dto.PropertyDto;
import com.example.realestate.entity.Property;
import com.example.realestate.entity.User;
import com.example.realestate.service.ImageService;
import com.example.realestate.service.PropertyService;
import com.example.realestate.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
public class PropertyController {

    private final PropertyService service;
    private final UserService userService;
    private final ImageService imageService;

    @GetMapping("/")
    public String home(
            @RequestParam(required = false) String location,
            @RequestParam(required = false) String type,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice,
            @RequestParam(required = false) String keyword,
            Model model) {

        model.addAttribute("properties",
                service.search(location, type, minPrice, maxPrice, keyword));

        return "property-list";
    }

    @GetMapping("/property/new")
    public String add(Model model) {
        model.addAttribute("property", new PropertyDto());
        return "property-form";
    }

    @PostMapping("/property")
    public String save(@Valid @ModelAttribute("property") PropertyDto dto,
                       @RequestParam("image") MultipartFile file,
                       Authentication auth,
                       RedirectAttributes redirectAttributes) {

        User user = userService.getByEmail(auth.getName());

        Property property = new Property();
        property.setTitle(dto.getTitle());
        property.setDescription(dto.getDescription());
        property.setPrice(dto.getPrice());
        property.setType(dto.getType());
        property.setLocation(dto.getLocation());
        property.setOwner(user);
        property.setApproved(false);

        if (!file.isEmpty()) {
            System.out.println("Uploading file: " + file.getOriginalFilename());
            String imageUrl = imageService.upload(file);
            if (imageUrl != null && !imageUrl.isEmpty()) {
                property.setImageUrl(imageUrl);
            }
        }

        service.save(property);

        redirectAttributes.addFlashAttribute(
                "success",
                "✅ Property saved successfully and waiting for admin approval"
        );

        return "redirect:/dashboard";
    }




}
