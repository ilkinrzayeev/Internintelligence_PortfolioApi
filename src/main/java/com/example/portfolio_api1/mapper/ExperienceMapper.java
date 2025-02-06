package com.example.portfolio_api1.mapper;

import com.example.portfolio_api1.dto.request.ExperienceRequest;
import com.example.portfolio_api1.dto.response.ExperienceResponse;
import com.example.portfolio_api1.entity.ExperienceEntity;
import com.example.portfolio_api1.entity.UserEntity;
import org.mapstruct.*;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ExperienceMapper {

    @Mapping(target = "userId", source = "user.id")
    ExperienceResponse toResponse(ExperienceEntity experienceEntity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", source = "userEntity")
    ExperienceEntity toEntity(ExperienceRequest experienceRequest, UserEntity userEntity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    void updateEntity(@MappingTarget ExperienceEntity entity, ExperienceRequest request);
}