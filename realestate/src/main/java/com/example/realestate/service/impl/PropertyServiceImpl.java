package com.example.realestate.service.impl;

import com.example.realestate.dto.PropertyDto;
import com.example.realestate.entity.Property;
import com.example.realestate.entity.User;
import com.example.realestate.repository.PropertyRepository;
import com.example.realestate.service.PropertyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
@Service
@RequiredArgsConstructor
public class PropertyServiceImpl implements PropertyService {

    private final PropertyRepository repo;

    @Override
    public List<Property> search(
            String location,
            String type,
            Double minPrice,
            Double maxPrice,
            String keyword) {

        return repo.search(
                location == null || location.isBlank() ? null : location,
                type == null || type.isBlank() ? null : type,
                minPrice,
                maxPrice,
                keyword == null || keyword.isBlank() ? null : keyword
        );
    }

    @Override
    public void update(Property property) {
        repo.save(property);
    }

    @Override
    public Property save(Property property) {
        return repo.save(property);
    }


    @Override
    public Property create(PropertyDto dto, User owner) {
        Property p = new Property();
        p.setTitle(dto.getTitle());
        p.setDescription(dto.getDescription());
        p.setPrice(dto.getPrice());
        p.setType(dto.getType());
        p.setLocation(dto.getLocation());
        p.setOwner(owner);
        p.setApproved(false);
        p.setDateListed(LocalDate.now());
        return repo.save(p); // SAVE ONLY HERE
    }

    @Override
    public List<Property> approved() {
        return repo.findByApprovedTrue();
    }

    @Override
    public List<Property> myProperties(User user) {
        return repo.findByOwner(user);
    }
    public void deleteById(Long id) {
        repo.deleteById(id);
    }
    @Override
    public void approve(Long id) {
        Property p = repo.findById(id).orElseThrow();
        p.setApproved(true);
        repo.save(p);
    }
}
