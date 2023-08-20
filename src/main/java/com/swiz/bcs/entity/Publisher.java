package com.swiz.bcs.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "publishers")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Publisher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(length = 500)
    private String address;

    // Constructors

    // Getters and Setters

    // Equals and HashCode

    // Other methods and logic
}
