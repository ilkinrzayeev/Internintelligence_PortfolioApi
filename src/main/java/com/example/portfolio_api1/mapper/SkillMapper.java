package com.example.portfolio_api1.mapper;

import com.example.portfolio_api1.dto.request.SkillRequest;
import com.example.portfolio_api1.dto.response.SkillResponse;
import com.example.portfolio_api1.entity.SkillEntity;
import com.example.portfolio_api1.entity.UserEntity;
import org.mapstruct.*;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface SkillMapper {

    @Mapping(target = "userId", source = "user.id")
    SkillResponse toResponse(SkillEntity skillEntity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", source = "userEntity")
    SkillEntity toEntity(SkillRequest skillRequest, UserEntity userEntity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    void updateEntity(@MappingTarget SkillEntity entity, SkillRequest request);
}