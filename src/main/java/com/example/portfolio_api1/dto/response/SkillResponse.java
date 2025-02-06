package com.example.portfolio_api1.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class SkillResponse {
    private Long id;
    private String name;
    private String level;
    private Long userId;
}