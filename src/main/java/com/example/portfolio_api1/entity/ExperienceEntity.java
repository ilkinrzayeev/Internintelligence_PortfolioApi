package com.example.portfolio_api1.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "experiences")
public class ExperienceEntity {
    @Id
    @SequenceGenerator(sequenceName = "experience_id",name = "experience_id",allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "experience_id")
    private Long id;

    private String company;
    private String position;
    private LocalDate startDate;
    private LocalDate endDate;
    private String description;


    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

}
