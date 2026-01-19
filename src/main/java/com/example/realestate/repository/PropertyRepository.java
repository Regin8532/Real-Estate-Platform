package com.example.realestate.repository;

import com.example.realestate.entity.Property;
import com.example.realestate.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PropertyRepository extends JpaRepository<Property, Long> {

    List<Property> findByApprovedTrue();

    List<Property> findByOwner(User owner);

    @Query("""
        SELECT p FROM Property p
        WHERE p.approved = true
        AND (:location IS NULL OR LOWER(p.location) LIKE LOWER(CONCAT('%', :location, '%')))
        AND (:type IS NULL OR p.type = :type)
        AND (:minPrice IS NULL OR p.price >= :minPrice)
        AND (:maxPrice IS NULL OR p.price <= :maxPrice)
        AND (
            :keyword IS NULL OR
            LOWER(p.title) LIKE LOWER(CONCAT('%', :keyword, '%')) OR
            LOWER(p.description) LIKE LOWER(CONCAT('%', :keyword, '%'))
        )
    """)
    List<Property> search(
            @Param("location") String location,
            @Param("type") String type,
            @Param("minPrice") Double minPrice,
            @Param("maxPrice") Double maxPrice,
            @Param("keyword") String keyword
    );
}
