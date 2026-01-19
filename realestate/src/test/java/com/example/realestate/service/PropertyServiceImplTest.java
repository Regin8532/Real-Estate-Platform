package com.example.realestate.service;

import com.example.realestate.dto.PropertyDto;
import com.example.realestate.entity.Property;
import com.example.realestate.entity.User;
import com.example.realestate.repository.PropertyRepository;
import com.example.realestate.service.impl.PropertyServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PropertyServiceImplTest {

    @Mock
    private PropertyRepository propertyRepository;

    @InjectMocks
    private PropertyServiceImpl propertyService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreate() {
        PropertyDto dto = new PropertyDto();
        dto.setTitle("Test");
        dto.setDescription("Desc");
        dto.setPrice(1000.00);
        dto.setType("SALE");
        dto.setLocation("City");

        User owner = new User();
        owner.setName("Owner");

        when(propertyRepository.save(any(Property.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Property property = propertyService.create(dto, owner);

        assertEquals("Test", property.getTitle());
        assertEquals(owner, property.getOwner());
        assertFalse(property.isApproved());
        assertNotNull(property.getDateListed());
    }

    @Test
    void testSave() {
        Property property = new Property();
        when(propertyRepository.save(property)).thenReturn(property);

        Property saved = propertyService.save(property);

        assertEquals(property, saved);
        verify(propertyRepository, times(1)).save(property);
    }

    @Test
    void testUpdate() {
        Property property = new Property();
        propertyService.update(property);
        verify(propertyRepository, times(1)).save(property);
    }

    @Test
    void testApprove() {
        Property property = new Property();
        property.setApproved(false);
        property.setId(1L);

        when(propertyRepository.findById(1L)).thenReturn(Optional.of(property));

        propertyService.approve(1L);

        assertTrue(property.isApproved());
        verify(propertyRepository, times(1)).save(property);
    }

    @Test
    void testMyProperties() {
        User owner = new User();
        List<Property> list = List.of(new Property());
        when(propertyRepository.findByOwner(owner)).thenReturn(list);

        List<Property> result = propertyService.myProperties(owner);
        assertEquals(1, result.size());
    }

    @Test
    void testApproved() {
        List<Property> list = List.of(new Property());
        when(propertyRepository.findByApprovedTrue()).thenReturn(list);

        List<Property> result = propertyService.approved();
        assertEquals(1, result.size());
    }

    @Test
    void testSearch() {
        List<Property> list = List.of(new Property());
        when(propertyRepository.search(null, null, null, null, null)).thenReturn(list);

        List<Property> result = propertyService.search(null, null, null, null, null);
        assertEquals(1, result.size());
    }
}
