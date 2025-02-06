package com.example.portfolio_api1.controller;

import com.example.portfolio_api1.dto.request.ProjectRequest;
import com.example.portfolio_api1.dto.response.ProjectResponse;
import com.example.portfolio_api1.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/project")
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;

    @GetMapping
    public ResponseEntity<List<ProjectResponse>> getAllProjects() {
        return ResponseEntity.ok(projectService.getAllProjects());
    }

    @GetMapping("{id}")
    public ResponseEntity<ProjectResponse> getProjectById(@PathVariable Long id) {
        return ResponseEntity.ok(projectService.getProjectById(id));
    }

    @PreAuthorize("hasAuthority('USER')")
    @PostMapping("/user")
    public ResponseEntity<ProjectResponse> createProjectByUser(@RequestBody ProjectRequest projectRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return ResponseEntity.ok(projectService.createProjectByUser(projectRequest, authentication.getName()));
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/{userId}/admin")
    public ResponseEntity<ProjectResponse> createProjectByAdmin(@RequestBody ProjectRequest projectRequest,
                                                                @PathVariable Long userId) {
        return ResponseEntity.ok(projectService.createProjectByAdmin(projectRequest, userId));
    }

    @PutMapping("{id}")
    public ResponseEntity<ProjectResponse> updateProject(@RequestBody ProjectRequest projectRequest,
                                                         @PathVariable Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return ResponseEntity.ok(projectService.updateProject(id, projectRequest, authentication.getName()));
    }

    @DeleteMapping("{id}")
    public void deleteProject(@PathVariable Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        projectService.deleteProject(id, authentication.getName());
    }
}
