package com.example.portfolio_api1.service.impl;
import com.example.portfolio_api1.dto.request.ProjectRequest;
import com.example.portfolio_api1.dto.response.ProjectResponse;
import com.example.portfolio_api1.entity.ProjectEntity;
import com.example.portfolio_api1.entity.UserEntity;
import com.example.portfolio_api1.exceptions.NotFoundException;
import com.example.portfolio_api1.exceptions.UnauthorizedException;
import com.example.portfolio_api1.mapper.ProjectMapper;
import com.example.portfolio_api1.repository.ProjectRepository;
import com.example.portfolio_api1.repository.UserRepository;
import com.example.portfolio_api1.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import static com.example.portfolio_api1.enums.ErrorMessage.*;
import static com.example.portfolio_api1.enums.RoleEnum.ADMIN;

@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;
    private final ProjectMapper projectMapper;

    @Override
    public List<ProjectResponse> getAllProjects() {
        return projectRepository.findAll()
                .stream()
                .map(projectMapper::toResponse)
                .toList();
    }

    @Override
    public ProjectResponse getProjectById(Long id) {
        return projectRepository.findById(id)
                .map(projectMapper::toResponse)
                .orElseThrow(() -> new NotFoundException(
                        String.format(PROJECT_NOT_FOUND.getMessage(), id)
                ));
    }

    @Override
    public ProjectResponse createProjectByUser(ProjectRequest projectRequest, String username) {
        UserEntity user = userRepository.findByUsername(username)
                .orElseThrow(() -> new NotFoundException(USER_NOT_FOUND.getMessage()));

        ProjectEntity entity = projectMapper.toEntity(projectRequest, user);
        return projectMapper.toResponse(projectRepository.save(entity));
    }

    @Override
    public ProjectResponse createProjectByAdmin(ProjectRequest projectRequest, Long userId) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(USER_NOT_FOUND.getMessage()));

        ProjectEntity entity = projectMapper.toEntity(projectRequest, user);
        return projectMapper.toResponse(projectRepository.save(entity));
    }

    @Override
    public ProjectResponse updateProject(Long id, ProjectRequest projectRequest, String username) {
        ProjectEntity entity = projectRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(
                        String.format(PROJECT_NOT_FOUND.getMessage(), id)
                ));

        if (!hasPermission(entity, username)) {
            throw new UnauthorizedException(UNAUTHORIZED_ACCESS.getMessage());
        }

        projectMapper.updateEntity(entity, projectRequest);
        return projectMapper.toResponse(projectRepository.save(entity));
    }

    @Override
    public void deleteProject(Long id, String username) {
        ProjectEntity entity = projectRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(
                        String.format(PROJECT_NOT_FOUND.getMessage(), id)
                ));

        if (!hasPermission(entity, username)) {
            throw new UnauthorizedException(UNAUTHORIZED_ACCESS.getMessage());
        }

        UserEntity user = entity.getUser();
        user.getProjects().remove(entity);
        userRepository.save(user);
    }

    private boolean hasPermission(ProjectEntity entity, String username) {
        return entity.getUser().getUsername().equals(username) ||
                userRepository.findByUsername(username)
                        .orElseThrow(() -> new NotFoundException(USER_NOT_FOUND.getMessage()))
                        .getRole().equals(ADMIN);
    }
}
