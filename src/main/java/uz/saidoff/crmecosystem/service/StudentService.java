package uz.saidoff.crmecosystem.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import uz.saidoff.crmecosystem.entity.Group;
import uz.saidoff.crmecosystem.entity.auth.Role;
import uz.saidoff.crmecosystem.entity.auth.User;
import uz.saidoff.crmecosystem.enums.RoleType;
import uz.saidoff.crmecosystem.exception.AlreadyExistException;
import uz.saidoff.crmecosystem.exception.NotFoundException;
import uz.saidoff.crmecosystem.mapper.StudentMapper;
import uz.saidoff.crmecosystem.payload.StudentResponseDto;
import uz.saidoff.crmecosystem.repository.GroupRepository;
import uz.saidoff.crmecosystem.repository.StudentRepository;
import uz.saidoff.crmecosystem.response.ResponseData;

import java.util.*;

@Service
@RequiredArgsConstructor
public class StudentService {
    private final StudentRepository studentRepository;
    private final GroupRepository groupRepository;
    private StudentMapper studentMapper;

    public ResponseData<?> saved(StudentResponseDto studentResponseDto) {

        Optional<Group> group = groupRepository.findById(studentResponseDto.getGroupId());
        if (group.isEmpty()) {
            throw new NotFoundException("group not found");
        }
        Group group2 = group.get();

        User mapperFromUserEntity = studentMapper.toFromUserEntity(studentResponseDto);
        mapperFromUserEntity.setGroup(group2);
        studentRepository.save(mapperFromUserEntity);

        return ResponseData.successResponse("student succesfuly created to group");
    }

    public ResponseData<String> removeStudent(UUID studentId) {
        Optional<User> studentRepositoryById = studentRepository.findById(studentId);
        if (studentRepositoryById.isEmpty()) {
            return ResponseData.successResponse("student not found");
        }
        User user = studentRepositoryById.get();
        user.setDeleted(false);
        studentRepository.save(user);
        return ResponseData.successResponse("student soft delete ");

    }

    public ResponseData<?> getStudentGroupFilter(int size, int page) {
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
        List<User> userList = studentRepository.findByGroupIdAndRoleRoleTypeAndDeletedFalse(groupId, RoleType.STUDENT);
        if (userList.isEmpty()) {
            return new ResponseData<>("not found user group", false);
        }
        return ResponseData.successResponse(userList);
    }
}
