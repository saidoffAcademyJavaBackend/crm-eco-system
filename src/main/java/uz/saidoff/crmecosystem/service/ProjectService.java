package uz.saidoff.crmecosystem.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import uz.saidoff.crmecosystem.entity.Project;
import uz.saidoff.crmecosystem.entity.auth.User;
import uz.saidoff.crmecosystem.exception.NotFoundException;
import uz.saidoff.crmecosystem.payload.ProjectRequestDto;
import uz.saidoff.crmecosystem.payload.ProjectResponseDto;
import uz.saidoff.crmecosystem.payload.ProjectUpdateDto;
import uz.saidoff.crmecosystem.repository.ProjectRepository;
import uz.saidoff.crmecosystem.repository.UserRepository;
import uz.saidoff.crmecosystem.response.ResponseData;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProjectService {
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;

    public ResponseData<?> savedProject(ProjectRequestDto projectRequestDto) {
        Optional<User> optionalUser = userRepository.findById(projectRequestDto.getUserId());
        if (optionalUser.isEmpty()) {
            throw new NotFoundException("user not found ");
        }
        Project project = new Project();
        project.setName(projectRequestDto.getName());
        project.setStartDate(projectRequestDto.getStartDate());
        project.setEndDate(projectRequestDto.getEndDate());
        project.setOwner(optionalUser.get());
        projectRepository.save(project);

        return ResponseData.successResponse("project created");
    }

    public ResponseData<?> getByOneProject(UUID projectId) {
        Optional<Project> optionalProject = projectRepository.findById(projectId);
        if (optionalProject.isEmpty()) {
            throw new NotFoundException("project not found");
        }
        Project project = optionalProject.get();
        ProjectResponseDto responseDto = new ProjectResponseDto();
        responseDto.setProjectId(project.getId());
        responseDto.setName(project.getName());
        responseDto.setStartDate(project.getStartDate());
        responseDto.setEndDate(project.getEndDate());
        responseDto.setOwnerId(project.getOwner().getId());
        responseDto.setCreatedAt(project.getCreatedAt());

        return ResponseData.successResponse(responseDto);
    }

    public ResponseData<?> allProject(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Project> projectPage = projectRepository.findAllByDeletedFalse(pageable);
        if (projectPage.isEmpty()) {
            throw new NotFoundException("not project ...");
        }
        Map<String, Object> result = new HashMap<>();
        result.put("data", projectPage.toList());
        result.put("total", projectPage.getTotalElements());
        result.put("totalPages", projectPage.getTotalPages());

        return new ResponseData<>(result, true);
    }

    public ResponseData<?> removeProject(UUID projectId) {
        Optional<Project> optionalProject = projectRepository.findById(projectId);
        if (optionalProject.isEmpty()) {
            throw new NotFoundException("project not found");
        }
        Project project = optionalProject.get();
        project.setDeleted(true);
        projectRepository.save(project);
        return ResponseData.successResponse("project succesfuly deleted");
    }

    public ResponseData<?> editProject(UUID projectId, ProjectUpdateDto updateDto) {
        Optional<Project> optionalProject = projectRepository.findById(projectId);
        if (optionalProject.isEmpty()) {
            throw new NotFoundException("project not found ...");
        }
        Project project = optionalProject.get();

        if (updateDto.getName() != null) {
            project.setName(updateDto.getName());
        }
        if (updateDto.getStartDate() != null) {
            project.setStartDate(updateDto.getStartDate());
        }
        if (updateDto.getEndDate() != null) {
            project.setEndDate(updateDto.getEndDate());
        }
        Project save = projectRepository.save(project);
        return ResponseData.successResponse(save);
    }
}
