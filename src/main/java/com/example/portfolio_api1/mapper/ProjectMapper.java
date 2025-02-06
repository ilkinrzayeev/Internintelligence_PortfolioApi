package com.example.portfolio_api1.mapper;

import com.example.portfolio_api1.dto.request.ProjectRequest;
import com.example.portfolio_api1.dto.response.ProjectResponse;
import com.example.portfolio_api1.entity.ProjectEntity;
import com.example.portfolio_api1.entity.UserEntity;
import org.mapstruct.*;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ProjectMapper {

    @Mapping(target = "userId", ignore = true)
    ProjectResponse toResponse(ProjectEntity projectEntity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    ProjectEntity toEntity(ProjectRequest projectRequest, UserEntity user);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    void updateEntity(@MappingTarget ProjectEntity entity, ProjectRequest request);
}
