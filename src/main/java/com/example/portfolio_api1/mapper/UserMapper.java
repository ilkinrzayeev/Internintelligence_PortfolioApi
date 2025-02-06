package com.example.portfolio_api1.mapper;

import com.example.portfolio_api1.dto.request.UserRequest;
import com.example.portfolio_api1.dto.response.UserResponse;
import com.example.portfolio_api1.entity.UserEntity;
import org.mapstruct.*;

@Mapper(componentModel = "spring",
        uses = {EducationMapper.class, ProjectMapper.class, SkillMapper.class},
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface UserMapper {

    @Mapping(target = "educationList", source = "educationList")
    @Mapping(target = "projects", source = "projects")
    @Mapping(target = "skills", source = "skills")
    UserResponse toResponse(UserEntity user);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "educationList", ignore = true)
    @Mapping(target = "projects", ignore = true)
    @Mapping(target = "skills", ignore = true)
    @Mapping(target = "password", source = "password", qualifiedByName = "encodePassword")
    UserEntity toEntity(UserRequest userRequest);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "educationList", ignore = true)
    @Mapping(target = "projects", ignore = true)
    @Mapping(target = "skills", ignore = true)
    @Mapping(target = "password", source = "password", qualifiedByName = "encodePassword")
    void updateEntity(UserRequest request, @MappingTarget UserEntity entity);

}
