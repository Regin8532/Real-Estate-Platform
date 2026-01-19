package com.example.realestate.controller;

import com.example.realestate.repository.PropertyRepository;
import com.example.realestate.service.PropertyService;
import com.example.realestate.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
@WebMvcTest(AdminController.class)
class AdminControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PropertyRepository propertyRepository;

    @MockBean
    private PropertyService propertyService;

    @Test
    void shouldLoadAdminPropertiesPage() throws Exception {
        when(propertyRepository.findAll()).thenReturn(List.of());

        mockMvc.perform(get("/admin/properties"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin-properties"))
                .andExpect(model().attributeExists("properties"));
    }

    @Test
    void shouldApproveProperty() throws Exception {
        mockMvc.perform(post("/admin/approve/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/properties"));

        verify(propertyService).approve(1L);
    }

    @Test
    void shouldDeleteProperty() throws Exception {
        mockMvc.perform(post("/admin/property/delete/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/properties"));

        verify(propertyService).deleteById(1L);
    }
}
