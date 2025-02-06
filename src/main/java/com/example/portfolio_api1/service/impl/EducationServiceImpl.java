package com.example.portfolio_api1.service.impl;
import com.example.portfolio_api1.dto.request.EducationRequest;
import com.example.portfolio_api1.dto.response.EducationResponse;
import com.example.portfolio_api1.entity.EducationEntity;
import com.example.portfolio_api1.entity.UserEntity;
import com.example.portfolio_api1.exceptions.NotFoundException;
import com.example.portfolio_api1.exceptions.UnauthorizedException;
import com.example.portfolio_api1.mapper.EducationMapper;
import com.example.portfolio_api1.repository.EducationRepository;
import com.example.portfolio_api1.repository.UserRepository;
import com.example.portfolio_api1.service.EducationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import static com.example.portfolio_api1.enums.ErrorMessage.*;
import static com.example.portfolio_api1.enums.RoleEnum.ADMIN;

@Service
@RequiredArgsConstructor
public class EducationServiceImpl implements EducationService {

    private final EducationRepository educationRepository;
    private final UserRepository userRepository;
    private final EducationMapper educationMapper;

    @Override
    public List<EducationResponse> getAllEducations() {
        return educationRepository.findAll()
                .stream()
                .map(educationMapper::toResponse)
                .toList();
    }

    @Override
    public EducationResponse getEducationById(Long id) {
        return educationRepository.findById(id)
                .map(educationMapper::toResponse)
                .orElseThrow(() -> new NotFoundException(
                        String.format(EDUCATION_NOT_FOUND.getMessage(), id)
                ));
    }

    @Override
    public EducationResponse createEducationByUser(EducationRequest educationRequest, String username) {
        UserEntity user = userRepository.findByUsername(username)
                .orElseThrow(() -> new NotFoundException(USER_NOT_FOUND.getMessage()));

        EducationEntity entity = educationMapper.toEntity(educationRequest, user);
        return educationMapper.toResponse(educationRepository.save(entity));
    }

    @Override
    public EducationResponse createEducationByAdmin(EducationRequest educationRequest, Long userId) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(USER_NOT_FOUND.getMessage()));

        EducationEntity entity = educationMapper.toEntity(educationRequest, user);
        return educationMapper.toResponse(educationRepository.save(entity));
    }

    @Override
    public EducationResponse updateEducation(Long id, EducationRequest educationRequest, String username) {
        EducationEntity entity = educationRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(
                        String.format(EDUCATION_NOT_FOUND.getMessage(), id)
                ));

        if (!hasPermission(username, entity)) {
            throw new UnauthorizedException(UNAUTHORIZED_ACCESS.getMessage());
        }

        educationMapper.updateEntity(educationRequest, entity);
        return educationMapper.toResponse(educationRepository.save(entity));
    }

    @Override
    public void deleteEducation(Long id, String username) {
        EducationEntity entity = educationRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(
                        String.format(EDUCATION_NOT_FOUND.getMessage(), id)
                ));

        if (!hasPermission(username, entity)) {
            throw new UnauthorizedException(UNAUTHORIZED_ACCESS.getMessage());
        }

        UserEntity user = entity.getUser();
        user.getEducationList().remove(entity);
        userRepository.save(user);
    }

    private boolean hasPermission(String username, EducationEntity entity) {
        return entity.getUser().getUsername().equals(username) ||
                userRepository.findByUsername(username)
                        .orElseThrow(() -> new NotFoundException(USER_NOT_FOUND.getMessage()))
                        .getRole().equals(ADMIN);
    }
}
