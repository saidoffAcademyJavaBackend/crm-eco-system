package uz.saidoff.crmecosystem.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import uz.saidoff.crmecosystem.entity.Group;
import uz.saidoff.crmecosystem.entity.auth.Role;
import uz.saidoff.crmecosystem.entity.auth.User;
import uz.saidoff.crmecosystem.enums.GroupStage;
import uz.saidoff.crmecosystem.enums.RoleType;
import uz.saidoff.crmecosystem.exception.AlreadyExistException;
import uz.saidoff.crmecosystem.exception.NotFoundException;
import uz.saidoff.crmecosystem.mapper.StudentMapper;
import uz.saidoff.crmecosystem.payload.StudentDto;
import uz.saidoff.crmecosystem.payload.StudentResponseDto;
import uz.saidoff.crmecosystem.payload.StudentUpdateDto;
import uz.saidoff.crmecosystem.repository.GroupRepository;
import uz.saidoff.crmecosystem.repository.RoleRepository;
import uz.saidoff.crmecosystem.repository.StudentRepository;
import uz.saidoff.crmecosystem.response.ResponseData;
import uz.saidoff.crmecosystem.util.MessageKey;
import uz.saidoff.crmecosystem.util.MessageService;

import java.util.*;

import static uz.saidoff.crmecosystem.enums.RoleType.STUDENT;

@Service
@RequiredArgsConstructor
public class StudentService {
    private final StudentRepository studentRepository;
    private final GroupRepository groupRepository;
    private StudentMapper studentMapper;
    private final RoleRepository roleRepository;

    public ResponseData<?> saved(UUID groupId, StudentResponseDto studentResponseDto) {

        Optional<Group> group = groupRepository.findById(groupId);
        if (group.isEmpty()) {
            throw new NotFoundException("group not found");
        }
        Group group2 = group.get();
        User newUserEntity = studentMapper.toFromUserEntity(studentResponseDto, group2);
        studentRepository.save(newUserEntity);
        return ResponseData.successResponse("student succesfuly created to group");
    }

    public ResponseData<String> removeStudent(UUID studentId) {
        Optional<User> studentRepositoryById = studentRepository.findById(studentId);
        if (studentRepositoryById.isEmpty()) {
            return ResponseData.successResponse("student not found");
        }
        User user = studentRepositoryById.get();
        user.setDeleted(true);
        studentRepository.save(user);
        return ResponseData.successResponse("student soft delete ");

    }

    public ResponseData<?> getStudentGroupSorted(int size, int page) {
        Pageable pageable = PageRequest.of(size, page);
        Page<User> userPage = studentRepository.findAll(pageable);
        if (userPage.isEmpty()) {
            throw new NotFoundException("student not found");
        }
        Map<String, Object> response = new HashMap<>();
        response.put("data", studentMapper.toDto(userPage.toList()));
        response.put("total", userPage.getTotalElements());
        response.put("totalPages", userPage.getTotalPages());

        return new ResponseData<>(response, true);

    }

    public ResponseData<?> getFiltr(UUID groupId) {
        List<User> userList = studentRepository.findByGroupIdAndRoleRoleTypeAndDeletedFalse(groupId, STUDENT);
        if (userList.isEmpty()) {
            return new ResponseData<>("not found user group", false);
        }
        return ResponseData.successResponse(userList);
    }

    public ResponseData<?> updateStudent(UUID studentId, StudentUpdateDto updateDto) {
        Optional<User> optionalUser = studentRepository.findById(studentId);
        if (optionalUser.isEmpty()) {
            return new ResponseData<>("student not found ", false);
        }
        User user = optionalUser.get();
        if (updateDto.getFirstName() != null) {
            user.setFirstName(updateDto.getFirstName());
        }
        if (updateDto.getLastName() != null) {
            user.setLastName(updateDto.getLastName());
        }
        if (updateDto.getPhoneNumber() != null) {
            user.setPhoneNumber(updateDto.getPhoneNumber());
        }
        if (updateDto.getRole() != null) {
            user.setRole(roleRepository.findByRoleType(updateDto.getRole().getRoleType()).orElseThrow(()
                    -> new NotFoundException(MessageService.getMessage(MessageKey.ROLE_NOT_FOUND))));
        }
        studentRepository.save(user);
        return ResponseData.successResponse(user);
    }

    public ResponseData<?> userTransfer(UUID userId) {
        Optional<User> optionalUser = studentRepository.findByIdAndGroupGroupStageAndDeletedFalse(userId, GroupStage.STUDENT);
        if (optionalUser.isEmpty()) {
            return new ResponseData<>("user not found", false);
        }
        User user = optionalUser.get();
        user.getGroup().setGroupStage(GroupStage.INTERN);
        studentRepository.save(user);
        return ResponseData.successResponse("student succesfuly intern ");
    }

    public ResponseData<?> getUserById(UUID userId) {
        Optional<User> optionalUser = studentRepository.findById(userId);
        if (optionalUser.isEmpty()) {
            return new ResponseData<>("student not fouind", false);
        }
        User user = optionalUser.get();
        StudentDto responsStudentDo = studentMapper.toResponsStudentDo(user);
        return ResponseData.successResponse(responsStudentDo);
    }
}
