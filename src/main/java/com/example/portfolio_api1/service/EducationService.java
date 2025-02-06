package com.example.portfolio_api1.service;

import com.example.portfolio_api1.dto.request.EducationRequest;
import com.example.portfolio_api1.dto.response.EducationResponse;

import java.util.List;

public interface EducationService {
    List<EducationResponse> getAllEducations();

    EducationResponse getEducationById(Long id);

    EducationResponse createEducationByUser(EducationRequest educationRequest, String username);

    EducationResponse createEducationByAdmin(EducationRequest educationRequest, Long userId);

    EducationResponse updateEducation(Long id, EducationRequest educationRequest, String username);

    void deleteEducation(Long id, String username);
}