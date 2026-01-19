package com.example.realestate.repository;

import com.example.realestate.entity.Property;
import com.example.realestate.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class PropertyRepositoryTest {

    @Autowired
    private PropertyRepository propertyRepository;

    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setName("Test User");
        user.setEmail("test@example.com");
    }

    @Test
    void testFindByOwner() {
        Property property = new Property();
        property.setTitle("Test Property");
        property.setOwner(user);
        propertyRepository.save(property);

        List<Property> result = propertyRepository.findByOwner(user);
        assertThat(result).isNotEmpty();
        assertThat(result.get(0).getTitle()).isEqualTo("Test Property");
    }

    @Test
    void testSearchWithKeyword() {
        Property property = new Property();
        property.setTitle("Luxury Villa");
        property.setDescription("Beautiful villa near beach");
        property.setOwner(user);
        property.setType("SALE");
        property.setLocation("Goa");
        property.setPrice(5000.0);
        propertyRepository.save(property);

        List<Property> results = propertyRepository.search(
                "Goa", "SALE", 1000.0, 6000.0, "villa"
        );

        assertThat(results).hasSize(1);
        assertThat(results.get(0).getTitle()).isEqualTo("Luxury Villa");
    }

    @Test
    void testFindByApprovedTrue() {
        Property property = new Property();
        property.setTitle("Approved House");
        property.setOwner(user);
        property.setApproved(true); // ensure you have a boolean field
        propertyRepository.save(property);

        List<Property> results = propertyRepository.findByApprovedTrue();
        assertThat(results).isNotEmpty();
        assertThat(results.get(0).getTitle()).isEqualTo("Approved House");
    }
}
