package uz.saidoff.crmecosystem.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.saidoff.crmecosystem.entity.*;
import uz.saidoff.crmecosystem.entity.auth.Role;
import uz.saidoff.crmecosystem.entity.auth.User;
import uz.saidoff.crmecosystem.enums.RoleType;
import uz.saidoff.crmecosystem.exception.NotFoundException;
import uz.saidoff.crmecosystem.mapper.StudentMapper;
import uz.saidoff.crmecosystem.payload.*;
import uz.saidoff.crmecosystem.payload.PaymentForMonthDto.PaymentForMonthCreatDto;
import uz.saidoff.crmecosystem.repository.*;
import uz.saidoff.crmecosystem.response.ResponseData;
import uz.saidoff.crmecosystem.util.MessageKey;
import uz.saidoff.crmecosystem.util.MessageService;

import java.text.ParseException;
import java.util.*;

import static uz.saidoff.crmecosystem.enums.RoleType.STUDENT;

@Service
@RequiredArgsConstructor
public class StudentService {
    /***
     * @author Azimbek Shaymanov 14 07
     */
    private final StudentRepository studentRepository;
    private final GroupRepository groupRepository;
    private final StudentMapper studentMapper;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final SpecialityRepository specialityRepository;
    private final AttachmentRepository attachmentRepository;
    private final GroupStudentRepository groupStudentRepository;
    private final PaymentForMonthService paymentForMonthService;
    private final PaymentForServiceRepository paymentForServiceRepository;
    private final ProjectUserRepository projectUserRepository;

    @Transactional
    public ResponseData<?> saved(StudentResponseDto studentResponseDto) throws ParseException {

        Optional<Group> group = groupRepository.findById(studentResponseDto.getGroupId());
        if (group.isEmpty()) {
            throw new NotFoundException("group not found");
        }
        Optional<Speciality> byName = specialityRepository.findById(studentResponseDto.getSpecialtyId());
        if (byName.isEmpty()) {
            throw new NotFoundException("speciality not found");
        }
        Optional<Role> byRoleType = roleRepository.findByRoleType(RoleType.STUDENT);
        if (byRoleType.isEmpty()) {
            throw new NotFoundException("rot type not found");
        }

        User newUserEntity = studentMapper.toFromUserEntity(studentResponseDto, byName.get(), byRoleType.get());
        for (GroupStudent groupStudent : group.get().getGroupStudents()) {
            if (groupStudent.getStudent().getPassportSeries().equals(newUserEntity.getPassportSeries())) {
                throw new NotFoundException("student already exists");
            }
        }
        User savedUser = studentRepository.save(newUserEntity);

        GroupStudent groupStudent = new GroupStudent();
        groupStudent.setStudent(savedUser);
        GroupStudent respons = groupStudentRepository.save(groupStudent);
        Group group1 = group.get();
        group1.getGroupStudents().add(respons);
        groupRepository.save(group1);

        return ResponseData.successResponse("student succesfuly created to group");
    }

    public ResponseData<String> removeStudent(UUID studentId) {
        Optional<User> studentRepositoryById = studentRepository.findById(studentId);
        if (studentRepositoryById.isEmpty()) {
            return new ResponseData<>("student not found ", false);
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
        response.put("data", userPage.toList());
        response.put("total", userPage.getTotalElements());
        response.put("totalPages", userPage.getTotalPages());

        return new ResponseData<>(response, true);

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
        if (updateDto.getSalary() != null) {
            user.setSalary(updateDto.getSalary());
        }
        if (updateDto.getNumberOfChildren() != null) {
            user.setNumberOfChildren(updateDto.getNumberOfChildren());
        }
        studentRepository.save(user);
        return ResponseData.successResponse(user);
    }

    public ResponseData<?> userTransfer(UUID userId) {
        Optional<User> optionalUser = studentRepository.findById(userId);
        if (optionalUser.isEmpty()) {
            throw new NotFoundException("student not found");
        }
        Optional<Role> roleType = roleRepository.findByRoleType(RoleType.INTERN);
        if (roleType.isEmpty()) {
            throw new NotFoundException("INTERN ->  ... role not found ");
        }
        User user = optionalUser.get();
        user.setRole(roleType.get());
        userRepository.save(user);
        return ResponseData.successResponse("The student successfully transitioned to an intern. ");
    }

    public ResponseData<?> getUserById(UUID userId) {
        Optional<User> optionalUser = studentRepository.findById(userId);
        if (optionalUser.isEmpty()) {
            throw new NotFoundException("student not found");
        }
        User user = optionalUser.get();
        List<Group> studentGroups = groupRepository.getStudentGroups(user.getId());
        if (studentGroups.isEmpty()) {
            throw new NotFoundException("userga tegishli guruh topilmadi");
        }
        Group group = studentGroups.get(0);

        StudentDto responsStudentDo = studentMapper.toResponsStudentDo(user, group);
        return ResponseData.successResponse(responsStudentDo);
    }


    public ResponseData<?> getByAllStudentProject(UUID userId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isEmpty()) {
            throw new NotFoundException("user not found");
        }
        User user = optionalUser.get();
        List<Project> projectList = projectUserRepository.findByProjectsByUserId(user.getId());
        if (projectList.isEmpty()) {
            throw new NotFoundException("projects not found");
        }
        return ResponseData.successResponse(List.of(projectList));
    }

    public ResponseData<?> getStudentClassSchedule(UUID studentId) {
        RequestWeekDayStudentDTO request = new RequestWeekDayStudentDTO();
        Optional<User> optionalUser = userRepository.findById(studentId);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            Optional<Group> dayByStudentId = groupRepository.usergaTegishliGroup(user.getId());
            if (dayByStudentId.isEmpty()) {
                throw new NotFoundException("There are no students in the group.");
            }
            Group group = dayByStudentId.get();
            request.setStudentId(optionalUser.get().getId());
            request.setGroupName(group.getName());
            request.setDays(group.getWeekDays());
            request.setStartTime(group.getStartTime());
            request.setEndTime(group.getEndTime());
            request.setCount(group.getGroupStudents().size());
        } else {
            throw new NotFoundException("student not found");
        }
        return ResponseData.successResponse(request);
    }

    public ResponseData<?> getStudentGroups(UUID userId) {

        Optional<User> optionalUser = studentRepository.findById(userId);
        if (optionalUser.isEmpty()) {
            throw new NotFoundException("user not found");
        }
        User user = optionalUser.get();
        List<Group> studentGroups = groupRepository.getStudentGroups(user.getId());
        if (studentGroups.isEmpty()) {
            throw new NotFoundException("group not found");
        }

        StudentGroupsResponseDto responseDto = new StudentGroupsResponseDto();
        for (Group group : studentGroups) {
            responseDto.setStudentId(user.getId());
            responseDto.setGroupsName(group.getName());
            responseDto.setCount(groupRepository.countStudentsGroup(group.getId()));
            responseDto.setTeacherName(group.getTeacher().getFirstName());
        }
        return ResponseData.successResponse(responseDto);
    }

    /***
     * @author Azimbek Shaymanov
     */
}
