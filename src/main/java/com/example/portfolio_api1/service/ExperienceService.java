package com.example.portfolio_api1.service;

import com.example.portfolio_api1.dto.request.ExperienceRequest;
import com.example.portfolio_api1.dto.response.ExperienceResponse;

import java.util.List;

public interface ExperienceService {
    List<ExperienceResponse> getAllExperiences();

    ExperienceResponse getExperienceById(Long id);

    ExperienceResponse createExperienceByAdmin(ExperienceRequest experienceRequest, Long userId);

    ExperienceResponse createExperienceByUser(ExperienceRequest experienceRequest, String username);

    ExperienceResponse updateExperience(Long id, ExperienceRequest experienceRequest, String username);

    void deleteExperience(Long id, String username);
}
