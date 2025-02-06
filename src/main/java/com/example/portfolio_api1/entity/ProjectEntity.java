package com.example.portfolio_api1.entity;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "projects")
public class ProjectEntity {
    @Id
    @SequenceGenerator(sequenceName = "project_id",name = "project_id",allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "project_id")
    private Long id;

    private String title;
    private String description;
    private String url;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;
}