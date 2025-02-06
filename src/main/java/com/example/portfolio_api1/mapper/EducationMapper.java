package com.example.portfolio_api1.mapper;

import com.example.portfolio_api1.dto.request.EducationRequest;
import com.example.portfolio_api1.dto.response.EducationResponse;
import com.example.portfolio_api1.entity.EducationEntity;
import com.example.portfolio_api1.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface EducationMapper {

    @Mapping(target = "userId", source = "user.id")
    EducationResponse toResponse(EducationEntity educationEntity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", source = "user")
    EducationEntity toEntity(EducationRequest request, UserEntity user);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    void updateEntity(EducationRequest request, @MappingTarget EducationEntity entity);
}
