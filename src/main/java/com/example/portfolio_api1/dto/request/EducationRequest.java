package com.example.portfolio_api1.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Builder
public class EducationRequest {
    private String institution;
    private String degree;
    private LocalDate startDate;
    private LocalDate endDate;
    private String description;
}
