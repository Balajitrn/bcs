package com.swiz.bcs.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "genres")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Genre {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(length = 500)
    private String description;

    // Getters and Setters

    // Equals and HashCode

    // Other methods and logic
}

