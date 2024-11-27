package uz.saidoff.crmecosystem.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import uz.saidoff.crmecosystem.entity.*;
import uz.saidoff.crmecosystem.entity.auth.Role;
import uz.saidoff.crmecosystem.entity.auth.User;
import uz.saidoff.crmecosystem.enums.Permissions;
import uz.saidoff.crmecosystem.enums.RoleType;
import uz.saidoff.crmecosystem.exception.NotFoundException;
import uz.saidoff.crmecosystem.mapper.GroupMapper;
import uz.saidoff.crmecosystem.mapper.InternsMapper;
import uz.saidoff.crmecosystem.payload.GroupDto;
import uz.saidoff.crmecosystem.payload.InternAddDto;
import uz.saidoff.crmecosystem.payload.InternGetDto;
import uz.saidoff.crmecosystem.payload.PaymentForMonthDto.PaymentForMonthCreatDto;
import uz.saidoff.crmecosystem.payload.ProjectResponseDto;
import uz.saidoff.crmecosystem.repository.*;
import uz.saidoff.crmecosystem.response.ResponseData;

import java.util.*;

@Service
@RequiredArgsConstructor
@Transactional
public class InternsService {
    private final InternsRepository internsRepository;
    private final InternsMapper internsMapper;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final SpecialityRepository specialityRepository;
    private final AttachmentRepository fileRepository;
    private final GroupRepository groupRepository;
    private final GroupStudentRepository groupStudentRepository;
    private final ProjectUserRepository projectUserRepository;
    private final GroupMapper groupMapper;

    public ResponseData<?> getAllInterns(int page, int size) {
        Optional<Role> optionalRole = roleRepository.findByRoleType(RoleType.INTERN);
        if (optionalRole.isEmpty()) {
            throw new NotFoundException("Role not found");
        }
//        Pageable pageable = PageRequest. of(page, size);
//        Page<User> interns = internsRepository.findAllInternsPageable("INTERN", pageable);
        Page<User> interns = internsRepository.findAllByRoleAndDeletedFalse(optionalRole.get(), PageRequest.of(page, size));
        if (interns.isEmpty()) {
            throw new NotFoundException("Interns not found");
        }
        List<InternGetDto> list = interns.get().map(internsMapper::toInternGetDto).toList();
        Map<String, Object> result = new HashMap<>();
        result.put("data", list);
        result.put("total", interns.getTotalElements());
        result.put("TotalPages", interns.getTotalPages());
        return ResponseData.successResponse(result);
    }

    public ResponseData<?> getOneById(UUID interId) {
        Optional<User> optionalUser = internsRepository.findById(interId);
        if (optionalUser.isEmpty()) {
            throw new NotFoundException("User not found");
        }
        InternGetDto internGetDto = internsMapper.toInternGetDto(optionalUser.get());
        return ResponseData.successResponse(internGetDto);
    }

    public ResponseData<?> addIntern(UUID groupId, InternAddDto internAddDto) {
        Optional<Group> optionalGroup = Optional.empty();
        if (groupId != null) {
            optionalGroup = groupRepository.findById(groupId);
            if (optionalGroup.isEmpty()) {
                throw new NotFoundException("Group not found");
            }
        }
        User principal = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UUID userId = principal.getId();
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isEmpty()) {
            throw new NotFoundException("User not found");
        }
        Optional<Speciality> optionalSpeciality = specialityRepository.findByIdAndDeletedFalse(internAddDto.getSpecialtyId());
        if (optionalSpeciality.isEmpty()) {
            throw new NotFoundException("Speciality not found");
        }
        Optional<Role> optionalRole = roleRepository.findByRoleType(RoleType.INTERN);
        if (optionalRole.isEmpty()) {
            throw new NotFoundException("Role not found to set as an Intern");
        }
        Optional<Attachment> optionalAttachment = Optional.empty();
        if (internAddDto.getAttachmentId() != null) {
            optionalAttachment = fileRepository.findById(internAddDto.getAttachmentId());
            if (optionalAttachment.isEmpty()) {
                throw new NotFoundException("Attachment not found");
            }
        }
        User user = internsMapper.toUser(userId, internAddDto, optionalSpeciality.get(), optionalRole.get(), optionalAttachment);
        internsRepository.save(user);
        optionalGroup.ifPresent(group -> groupStudentRepository.save(new GroupStudent(group, user)));
        return ResponseData.successResponse("intern added successfully");
    }

    public ResponseData<?> deleteById(UUID internId) {
        Optional<User> optionalIntern = internsRepository.findById(internId);
        if (optionalIntern.isEmpty()) {
            throw new NotFoundException("User not found");
        }
        User intern = optionalIntern.get();
        intern.setDeleted(true);
        internsRepository.save(intern);
        return ResponseData.successResponse("intern deleted successfully");
    }

    public ResponseData<?> update(InternGetDto internGetDto, String password) {
        Optional<User> optionalIntern = internsRepository.findById(internGetDto.getInterId());
        if (optionalIntern.isEmpty()) {
            throw new NotFoundException("intern not found");
        }
        Optional<Speciality> optionalSpeciality = specialityRepository.findByIdAndDeletedFalse(internGetDto.getSpecialtyId());
        if (optionalSpeciality.isEmpty()) {
            throw new NotFoundException("Speciality not found");
        }
        Optional<Role> optionalRole = roleRepository.findByRoleType(RoleType.INTERN);
        if (optionalRole.isEmpty()) {
            throw new NotFoundException("Role not found to set as an Intern");
        }
        Attachment attachment = new Attachment();
        if (internGetDto.getAttachmentId() != null) {
            Optional<Attachment> optionalAttachment = fileRepository.findById(internGetDto.getAttachmentId());
            if (optionalAttachment.isEmpty()) {
                throw new NotFoundException("Attachment not found");
            }
            attachment = optionalAttachment.get();
        }
        User intern = optionalIntern.get();
        intern = internsMapper.toUpdateUser(intern, internGetDto, attachment, optionalRole.get(), optionalSpeciality.get(), password);
        internsRepository.save(intern);
        return ResponseData.successResponse("intern updated successfully");
    }

    public ResponseData<?> internToEmployee(UUID internId) {
        Optional<User> optionalIntern = internsRepository.findById(internId);
        if (optionalIntern.isEmpty()) {
            throw new NotFoundException("Intern not found");
        }
        Optional<Role> optionalRole = roleRepository.findByRoleType(RoleType.EMPLOYEE);
        if (optionalRole.isEmpty()) {
            throw new NotFoundException("Role not found to set as an Employee");
        }

        User intern = optionalIntern.get();

        intern.setRole(optionalRole.get());
        intern.setPermissions(Collections.singletonList(Permissions.valueOf("READ_EMPLOYEE")));
        internsRepository.save(intern);
        return ResponseData.successResponse("intern updated successfully");
    }

    public ResponseData<?> getParticipatedProjects(UUID userId) {
        if (userId == null) {
            User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            userId = user.getId();
        }
        List<ProjectUser> userProjects = projectUserRepository.findByUserId(userId);
        if (userProjects.isEmpty()) {
            throw new NotFoundException("User not found");
        }
        List<ProjectResponseDto> projectResponseDtos = new LinkedList<>();
        for (ProjectUser userProject : userProjects) {
            projectResponseDtos.add(internsMapper.getInternProjects(userProject));
        }
        return ResponseData.successResponse(projectResponseDtos);
    }

    public ResponseData<?> getGroups(UUID userId) {
        if (userId == null) {
            User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            userId = user.getId();
        }
        List<GroupStudent> groupStudents = groupStudentRepository.findByStudentId(userId);
        if (groupStudents.isEmpty()) {
            throw new NotFoundException("Group not found");
        }
        List<GroupDto> groupDtos = new LinkedList<>();
        groupMapper.toDto(groupStudents.getFirst().getGroupId());
        for (GroupStudent groupStudent : groupStudents) {
            groupDtos.add(groupMapper.toDto(groupStudent.getGroupId()));
        }
        return ResponseData.successResponse(groupDtos);
    }
}
