package com.example.portfolio_api1.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ErrorMessage {
    INVALID_USER_REQUEST("Invalid user request"),
    EDUCATION_NOT_FOUND("Education not found with given id: %s"),
    USER_ALREADY_EXISTS("This user already exists"),
    UNAUTHORIZED_ACCESS("Unauthorized access to the resource"),
    EXPERIENCE_NOT_FOUND("Experience not found with given id: %s"),
    SKILL_NOT_FOUND("Skill not found with given id: %s"),
    PROJECT_NOT_FOUND("Project not found with given id: %s"),
    USER_NOT_FOUND("User not found"),
    TOKEN_IS_EXPIRED("Token is expired"),
    TOKEN_IS_INVALID("Token is invalid");

    private final String message;
}
