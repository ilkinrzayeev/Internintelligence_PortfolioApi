package com.example.portfolio_api1.dto.response;

import com.example.portfolio_api1.enums.RoleEnum;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Builder
@Getter
@Setter
public class UserResponse {
    private Long id;
    private String username;
    private String email;
    private RoleEnum role;
    private List<ProjectResponse> projects;
    private List<SkillResponse> skills;
    private List<ExperienceResponse> experiences;
    private List<EducationResponse> educationList;

}