package com.example.portfolio_api1.service;

import com.example.portfolio_api1.dto.request.ProjectRequest;
import com.example.portfolio_api1.dto.response.ProjectResponse;

import java.util.List;

public interface ProjectService {
    List<ProjectResponse> getAllProjects();

    ProjectResponse getProjectById(Long id);

    ProjectResponse createProjectByUser(ProjectRequest projectRequest, String username);

    ProjectResponse createProjectByAdmin(ProjectRequest projectRequest, Long userId);

    ProjectResponse updateProject(Long id, ProjectRequest projectRequest, String username);

    void deleteProject(Long id, String username);
}