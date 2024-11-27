package uz.saidoff.crmecosystem.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.saidoff.crmecosystem.entity.Project;
import uz.saidoff.crmecosystem.entity.ProjectUser;
import uz.saidoff.crmecosystem.entity.auth.User;
import uz.saidoff.crmecosystem.exception.NotFoundException;
import uz.saidoff.crmecosystem.payload.*;
import uz.saidoff.crmecosystem.repository.ProjectRepository;
import uz.saidoff.crmecosystem.repository.ProjectUserRepository;
import uz.saidoff.crmecosystem.repository.UserRepository;
import uz.saidoff.crmecosystem.response.ResponseData;

import java.sql.Date;
import java.time.LocalDate;
import java.util.*;

@Service
@RequiredArgsConstructor
public class ProjectService {
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;
    private final ProjectUserRepository projectUserRepository;

    @Transactional
    public ResponseData<?> savedProject(ProjectRequestDto projectRequestDto) {
        Project project = new Project();
        project.setName(projectRequestDto.getName());
        project.setStartDate(projectRequestDto.getStartDate());
        project.setEndDate(projectRequestDto.getEndDate());
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

    public ResponseData<?> userSavedToProject(UUID userId, UUID projectId) {
        Optional<Project> optionalProject = projectRepository.findById(projectId);
        if (optionalProject.isEmpty()) {
            throw new NotFoundException("project not found ");
        }
        Project project = optionalProject.get();

        List<User> usersByProjectId = projectUserRepository.findUsersByProjectId(project.getId());
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isEmpty()) {
            throw new NotFoundException("user not found");
        }
        User user = optionalUser.get();
        for (User user1 : usersByProjectId) {
            if (user1.getId().equals(user.getId())) {
                throw new NotFoundException("user already exists");
            }
        }

        ProjectUser projectUser = new ProjectUser(user, project,false);   //false ni ozim qoshib qoydim hatolik berayothani uchun // ToDo Azimbek project bilan project usergan yana boshqa narsa ham qoshuvdim
        projectUserRepository.save(projectUser);                              // isOwner va yana summasi va currency si  shularni ko'rib tog'irlab qoyarsiz
        return ResponseData.successResponse("user and project succesfuly to saved");
    }

    public ResponseData<?> getByIdProject(UUID projectId) {
        List<User> usersByProjectId = projectUserRepository.findUsersByProjectId(projectId);
        if (usersByProjectId.isEmpty()) {
            throw new NotFoundException("No users related to the project were found");
        }
        List<StudentResponseDto> studentResponseDtos = mapToUser(usersByProjectId);
        return ResponseData.successResponse(studentResponseDtos);
    }

    private List<StudentResponseDto> mapToUser(List<User> userList) {
        List<StudentResponseDto> studentResponseDtos = new ArrayList<>();
        for (User user : userList) {
            studentResponseDtos.add(toDto(user));
        }
        return studentResponseDtos;
    }

    private StudentResponseDto toDto(User user) {
        StudentResponseDto studentResponseDto = new StudentResponseDto();
        studentResponseDto.setFirstName(user.getFirstName());
        studentResponseDto.setLastName(user.getLastName());
        studentResponseDto.setFatherName(user.getFatherName());
        studentResponseDto.setSpecialtyId(studentResponseDto.getSpecialtyId());
        studentResponseDto.setPhoneNumber(user.getPhoneNumber());
        studentResponseDto.setSecondPhoneNumber(user.getSecondPhoneNumber());
        studentResponseDto.setPassportSeries(user.getPassportSeries());
        studentResponseDto.setRole(user.getRole().getName());
        studentResponseDto.setCurrentResidence(user.getCurrentResidence());
        studentResponseDto.setStartWork(Date.valueOf(String.valueOf((Date.valueOf(user.getStartWork().toLocalDate())))));
        return studentResponseDto;
    }

    public ResponseData<?> getProjectsByDate(LocalDate start, LocalDate end) {
        List<Project> projectsByDate = projectRepository.findProjectsByDate(start, end);
        if (projectsByDate.isEmpty()) {
            throw new NotFoundException("No projects found in this range.");
        }
        return ResponseData.successResponse(projectsByDate);
    }

    public ResponseData<?> getProjectByUser(UUID userId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isEmpty()) {
            throw new NotFoundException("user not found");
        }
        User user = optionalUser.get();
        List<Project> projectList = projectUserRepository.findByProjectsByUserId(user.getId());
        if (projectList.isEmpty()) {
            throw new NotFoundException("project not found");
        }
        return ResponseData.successResponse(List.of(projectList));
    }
}
