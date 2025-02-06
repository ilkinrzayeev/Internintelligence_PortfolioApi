package com.example.portfolio_api1.controller;

import com.example.portfolio_api1.dto.request.ExperienceRequest;
import com.example.portfolio_api1.dto.response.ExperienceResponse;
import com.example.portfolio_api1.service.ExperienceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/experience")
@RequiredArgsConstructor
public class ExperienceController {

    private final ExperienceService experienceService;

    @GetMapping
    public ResponseEntity<List<ExperienceResponse>> getAllExperience() {
        return ResponseEntity.ok(experienceService.getAllExperiences());
    }

    @GetMapping("{id}")
    public ResponseEntity<ExperienceResponse> getExperienceById(@PathVariable Long id) {
        return ResponseEntity.ok(experienceService.getExperienceById(id));
    }

    @PreAuthorize("hasAuthority('USER')")
    @PostMapping("/user")
    public ResponseEntity<ExperienceResponse> createExperienceByUser(@RequestBody ExperienceRequest experienceRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return ResponseEntity.ok(experienceService.createExperienceByUser(experienceRequest, authentication.getName()));
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/{userId}/admin")
    public ResponseEntity<ExperienceResponse> createExperienceByAdmin(@RequestBody ExperienceRequest experienceRequest,
                                                                      @PathVariable Long userId) {
        return ResponseEntity.ok(experienceService.createExperienceByAdmin(experienceRequest, userId));
    }

    @PutMapping("{id}")
    public ResponseEntity<ExperienceResponse> updateExperience(@RequestBody ExperienceRequest experienceRequest,
                                                               @PathVariable Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return ResponseEntity.ok(experienceService.updateExperience(id, experienceRequest, authentication.getName()));
    }

    @DeleteMapping("{id}")
    public void deleteExperience(@PathVariable Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        experienceService.deleteExperience(id, authentication.getName());
    }

}