package com.example.portfolio_api1.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "skills")
public class SkillEntity {
    @Id
    @SequenceGenerator(sequenceName = "skill_id",name = "skill_id",allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "skill_id")
    private Long id;

    private String name;
    private String level;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;
}