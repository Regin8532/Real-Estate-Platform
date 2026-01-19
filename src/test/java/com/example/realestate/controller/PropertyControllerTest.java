package com.example.realestate.controller;

import com.example.realestate.entity.Property;
import com.example.realestate.entity.User;
import com.example.realestate.service.ImageService;
import com.example.realestate.service.PropertyService;
import com.example.realestate.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
@WebMvcTest(PropertyController.class)
class PropertyControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PropertyService propertyService;

    @MockBean
    private UserService userService;

    @MockBean
    private ImageService imageService;

    @Test
    void shouldLoadHomePage() throws Exception {
        when(propertyService.search(any(), any(), any(), any(), any()))
                .thenReturn(List.of());

        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("property-list"))
                .andExpect(model().attributeExists("properties"));
    }

    @Test
    @WithMockUser(username = "user@test.com")
    void shouldShowAddPropertyForm() throws Exception {
        mockMvc.perform(get("/property/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("property-form"))
                .andExpect(model().attributeExists("property"));
    }

    @Test
    @WithMockUser(username = "user@test.com")
    void shouldSavePropertyWithImage() throws Exception {

        User user = new User();
        user.setEmail("user@test.com");

        when(userService.getByEmail("user@test.com")).thenReturn(user);
        when(imageService.upload(any())).thenReturn("http://image.url");

        MockMultipartFile image =
                new MockMultipartFile("image", "house.jpg",
                        "image/jpeg", "dummy".getBytes());

        mockMvc.perform(multipart("/property")
                        .file(image)
                        .param("title", "House")
                        .param("description", "Nice house")
                        .param("price", "100000")
                        .param("type", "SALE")
                        .param("location", "Chennai"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/dashboard"));

        verify(propertyService).save(any(Property.class));
    }
}
