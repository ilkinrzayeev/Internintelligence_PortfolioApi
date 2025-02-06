package com.example.portfolio_api1.service.impl;
import com.example.portfolio_api1.dto.request.SkillRequest;
import com.example.portfolio_api1.dto.response.SkillResponse;
import com.example.portfolio_api1.entity.SkillEntity;
import com.example.portfolio_api1.entity.UserEntity;
import com.example.portfolio_api1.exceptions.NotFoundException;
import com.example.portfolio_api1.exceptions.UnauthorizedException;
import com.example.portfolio_api1.mapper.SkillMapper;
import com.example.portfolio_api1.repository.SkillRepository;
import com.example.portfolio_api1.repository.UserRepository;
import com.example.portfolio_api1.service.SkillService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import static com.example.portfolio_api1.enums.ErrorMessage.*;
import static com.example.portfolio_api1.enums.RoleEnum.ADMIN;

@Service
@RequiredArgsConstructor
public class SkillServiceImpl implements SkillService {

    private final SkillRepository skillRepository;
    private final UserRepository userRepository;
    private final SkillMapper skillMapper;

    @Override
    public List<SkillResponse> getAllSkills() {
        return skillRepository.findAll()
                .stream()
                .map(skillMapper::toResponse)
                .toList();
    }

    @Override
    public SkillResponse getSkillById(Long id) {
        return skillRepository.findById(id)
                .map(skillMapper::toResponse)
                .orElseThrow(() -> new NotFoundException(
                        String.format(SKILL_NOT_FOUND.getMessage(), id)
                ));
    }

    @Override
    public SkillResponse createSkillByAdmin(SkillRequest skillRequest, Long userId) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(USER_NOT_FOUND.getMessage()));

        SkillEntity entity = skillMapper.toEntity(skillRequest, user);
        return skillMapper.toResponse(skillRepository.save(entity));
    }

    @Override
    public SkillResponse createSkillByUser(SkillRequest skillRequest, String username) {
        UserEntity user = userRepository.findByUsername(username)
                .orElseThrow(() -> new NotFoundException(USER_NOT_FOUND.getMessage()));

        SkillEntity entity = skillMapper.toEntity(skillRequest, user);
        return skillMapper.toResponse(skillRepository.save(entity));
    }

    @Override
    public SkillResponse updateSkill(Long id, SkillRequest skillRequest, String username) {
        SkillEntity entity = skillRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(
                        String.format(SKILL_NOT_FOUND.getMessage(), id)
                ));

        if (!hasPermission(entity, username)) {
            throw new UnauthorizedException(UNAUTHORIZED_ACCESS.getMessage());
        }

        skillMapper.updateEntity(entity, skillRequest);
        return skillMapper.toResponse(skillRepository.save(entity));
    }

    @Override
    public void deleteSkill(Long id, String username) {
        SkillEntity entity = skillRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(
                        String.format(SKILL_NOT_FOUND.getMessage(), id)
                ));

        if (!hasPermission(entity, username)) {
            throw new UnauthorizedException(UNAUTHORIZED_ACCESS.getMessage());
        }

        UserEntity user = entity.getUser();
        user.getSkills().remove(entity);
        userRepository.save(user);
    }

    private boolean hasPermission(SkillEntity entity, String username) {
        return entity.getUser().getUsername().equals(username) ||
                userRepository.findByUsername(username)
                        .orElseThrow(() -> new NotFoundException(USER_NOT_FOUND.getMessage()))
                        .getRole().equals(ADMIN);
    }
}
