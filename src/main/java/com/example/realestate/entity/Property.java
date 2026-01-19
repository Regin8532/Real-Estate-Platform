package com.example.realestate.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class Property {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Column(length = 1000)
    private String description;

    private double price;

    private String type; // SALE / RENT

    private String location;

    private String imageUrl;

    private boolean approved;

    private LocalDate dateListed;

    @ManyToOne
    private User owner;
}
