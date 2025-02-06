package com.example.portfolio_api1.controller;

import com.example.portfolio_api1.dto.request.SkillRequest;
import com.example.portfolio_api1.dto.response.SkillResponse;
import com.example.portfolio_api1.service.SkillService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/skill")
@RequiredArgsConstructor
public class SkillController {
    private final SkillService skillService;


    @GetMapping
    public ResponseEntity<List<SkillResponse>> getAllProjects() {
        return ResponseEntity.ok(skillService.getAllSkills());
    }

    @GetMapping("{id}")
    public ResponseEntity<SkillResponse> getProjectById(@PathVariable Long id) {
        return ResponseEntity.ok(skillService.getSkillById(id));
    }

    @PreAuthorize("hasAuthority('USER')")
    @PostMapping("/user")
    public ResponseEntity<SkillResponse> createProjectByUser(@RequestBody SkillRequest skillRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return ResponseEntity.ok(skillService.createSkillByUser(skillRequest, authentication.getName()));
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/{userId}/admin")
    public ResponseEntity<SkillResponse> createProjectByAdmin(@RequestBody SkillRequest skillRequest,
                                                              @PathVariable Long userId) {
        return ResponseEntity.ok(skillService.createSkillByAdmin(skillRequest, userId));
    }

    @PutMapping("{id}")
    public ResponseEntity<SkillResponse> updateProject(@RequestBody SkillRequest skillRequest,
                                                       @PathVariable Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return ResponseEntity.ok(skillService.updateSkill(id, skillRequest, authentication.getName()));
    }

    @DeleteMapping("{id}")
    public void deleteProject(@PathVariable Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        skillService.deleteSkill(id, authentication.getName());
    }
}