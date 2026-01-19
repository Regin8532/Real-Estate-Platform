package com.example.realestate.service;

import com.example.realestate.dto.PropertyDto;
import com.example.realestate.entity.Property;
import com.example.realestate.entity.User;

import java.util.List;

public interface PropertyService {

    void deleteById(Long id);

    Property create(PropertyDto dto, User owner);

    List<Property> approved();

    List<Property> myProperties(User user);

    void approve(Long id);

    List<Property> search(
            String location,
            String type,
            Double minPrice,
            Double maxPrice,
            String keyword
    );

    void update(Property property);

    Property save(Property property);

}
