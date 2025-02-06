package com.example.portfolio_api1.service.impl;
import com.example.portfolio_api1.dto.request.ExperienceRequest;
import com.example.portfolio_api1.dto.response.ExperienceResponse;
import com.example.portfolio_api1.entity.ExperienceEntity;
import com.example.portfolio_api1.entity.UserEntity;
import com.example.portfolio_api1.enums.RoleEnum;
import com.example.portfolio_api1.exceptions.NotFoundException;
import com.example.portfolio_api1.exceptions.UnauthorizedException;
import com.example.portfolio_api1.mapper.ExperienceMapper;
import com.example.portfolio_api1.repository.ExperienceRepository;
import com.example.portfolio_api1.repository.UserRepository;
import com.example.portfolio_api1.service.ExperienceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import static com.example.portfolio_api1.enums.ErrorMessage.*;

@Service
@RequiredArgsConstructor
public class ExperienceServiceImpl implements ExperienceService {

    private final ExperienceRepository experienceRepository;
    private final UserRepository userRepository;
    private final ExperienceMapper experienceMapper;

    @Override
    public List<ExperienceResponse> getAllExperiences() {
        return experienceRepository.findAll()
                .stream()
                .map(experienceMapper::toResponse)
                .toList();
    }

    @Override
    public ExperienceResponse getExperienceById(Long id) {
        return experienceRepository.findById(id)
                .map(experienceMapper::toResponse)
                .orElseThrow(() -> new NotFoundException(
                        String.format(EXPERIENCE_NOT_FOUND.getMessage(), id)
                ));
    }

    @Override
    public ExperienceResponse createExperienceByAdmin(ExperienceRequest experienceRequest, Long userId) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(USER_NOT_FOUND.getMessage()));

        ExperienceEntity entity = experienceMapper.toEntity(experienceRequest, user);
        return experienceMapper.toResponse(experienceRepository.save(entity));
    }

    @Override
    public ExperienceResponse createExperienceByUser(ExperienceRequest experienceRequest, String username) {
        UserEntity user = userRepository.findByUsername(username)
                .orElseThrow(() -> new NotFoundException(USER_NOT_FOUND.getMessage()));

        ExperienceEntity entity = experienceMapper.toEntity(experienceRequest, user);
        return experienceMapper.toResponse(experienceRepository.save(entity));
    }

    @Override
    public ExperienceResponse updateExperience(Long id, ExperienceRequest experienceRequest, String username) {
        ExperienceEntity entity = experienceRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(
                        String.format(EXPERIENCE_NOT_FOUND.getMessage(), id)
                ));

        if (!hasPermission(entity, username)) {
            throw new UnauthorizedException(UNAUTHORIZED_ACCESS.getMessage());
        }

        experienceMapper.updateEntity(entity, experienceRequest);
        return experienceMapper.toResponse(experienceRepository.save(entity));
    }

    @Override
    public void deleteExperience(Long id, String username) {
        ExperienceEntity entity = experienceRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(
                        String.format(EXPERIENCE_NOT_FOUND.getMessage(), id)
                ));

        if (!hasPermission(entity, username)) {
            throw new UnauthorizedException(UNAUTHORIZED_ACCESS.getMessage());
        }

        UserEntity user = entity.getUser();
        user.getExperiences().remove(entity);
        userRepository.save(user);
    }

    private boolean hasPermission(ExperienceEntity entity, String username) {
        return entity.getUser().getUsername().equals(username) ||
                userRepository.findByUsername(username)
                        .orElseThrow(() -> new NotFoundException(USER_NOT_FOUND.getMessage()))
                        .getRole().equals(RoleEnum.ADMIN);
    }
}