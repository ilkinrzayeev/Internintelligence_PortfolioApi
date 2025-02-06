package com.example.portfolio_api1.controller;

import com.example.portfolio_api1.dto.request.EducationRequest;
import com.example.portfolio_api1.dto.response.EducationResponse;
import com.example.portfolio_api1.service.EducationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/education")
@RequiredArgsConstructor
public class EducationController {

    private final EducationService educationService;

    @GetMapping
    public ResponseEntity<List<EducationResponse>> getAllEducations(){
        return ResponseEntity.ok(educationService.getAllEducations());
    }

    @GetMapping("{id}")
    public ResponseEntity<EducationResponse> getEducationById(@PathVariable Long id){
        return ResponseEntity.ok(educationService.getEducationById(id));
    }

    @PreAuthorize("hasAuthority('USER')")
    @PostMapping("/user")
    public ResponseEntity<EducationResponse> createEducationByUser(@RequestBody EducationRequest educationRequest){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return ResponseEntity.ok(educationService.createEducationByUser(educationRequest, authentication.getName()));
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/{userId}/admin")
    public ResponseEntity<EducationResponse> createEducationByAdmin(@RequestBody EducationRequest educationRequest, @PathVariable Long userId){
        return ResponseEntity.ok(educationService.createEducationByAdmin(educationRequest, userId));
    }

    @PutMapping("{id}")
    public ResponseEntity<EducationResponse> updateEducation(@PathVariable Long id, @RequestBody EducationRequest educationRequest){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return ResponseEntity.ok(educationService.updateEducation(id, educationRequest, authentication.getName()));
    }

    @DeleteMapping("{id}")
    public void deleteEducation(@PathVariable Long id){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        educationService.deleteEducation(id,authentication.getName());
    }
}
