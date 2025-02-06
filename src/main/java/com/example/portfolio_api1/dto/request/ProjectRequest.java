package com.example.portfolio_api1.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProjectRequest {
    private String title;
    private String description;
    private String url;
}
