package uz.saidoff.crmecosystem.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import uz.saidoff.crmecosystem.entity.*;
import uz.saidoff.crmecosystem.entity.auth.Role;
import uz.saidoff.crmecosystem.entity.auth.User;
import uz.saidoff.crmecosystem.enums.RoleType;
import uz.saidoff.crmecosystem.exception.NotFoundException;
import uz.saidoff.crmecosystem.mapper.StudentMapper;
import uz.saidoff.crmecosystem.payload.PaymentForMonthDto.PaymentForMonthCreatDto;
import uz.saidoff.crmecosystem.payload.StudentDto;
import uz.saidoff.crmecosystem.payload.StudentResponseDto;
import uz.saidoff.crmecosystem.payload.StudentUpdateDto;
import uz.saidoff.crmecosystem.repository.*;
import uz.saidoff.crmecosystem.response.ResponseData;
import uz.saidoff.crmecosystem.util.MessageKey;
import uz.saidoff.crmecosystem.util.MessageService;

import java.util.*;

import static uz.saidoff.crmecosystem.enums.RoleType.STUDENT;

@Service
@RequiredArgsConstructor
@Transactional
public class StudentService {
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

    public ResponseData<?> saved(StudentResponseDto studentResponseDto) {

        Optional<Group> group = groupRepository.findById(studentResponseDto.getGroupId());
        if (group.isEmpty()) {
            throw new NotFoundException("group not found");
        }

        Optional<Speciality> byName = specialityRepository.findByName(studentResponseDto.getSpecialty());
        if (byName.isEmpty()) {
            throw new NotFoundException("speciality not found");
        }
        Optional<Role> byRoleType = roleRepository.findByRoleType(STUDENT);
        if (byRoleType.isEmpty()) {
            throw new NotFoundException("rot type not found");
        }
        Optional<Attachment> optionalAttachment = attachmentRepository.findById(studentResponseDto.getAttachmentId());
        if (optionalAttachment.isEmpty()) {
            throw new NotFoundException("attechment not found");
        }

        User newUserEntity = studentMapper.toFromUserEntity(studentResponseDto, byName.get(), byRoleType.get(), optionalAttachment.get());
        GroupStudent groupStudent = new GroupStudent(group.get(), newUserEntity);
        PaymentForMonthCreatDto paymentForDTO = studentMapper.toPaymentForDTO(groupStudent);
        paymentForMonthService.creat(paymentForDTO);
        groupStudentRepository.save(groupStudent);
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

//    public ResponseData<?> getFiltr(UUID groupId) {
//        List<User> userList = studentRepository.findByGroupIdAndRoleRoleTypeAndDeletedFalse(groupId, STUDENT);
//        if (userList.isEmpty()) {
//            return new ResponseData<>("not found user group", false);
//        }
//        return ResponseData.successResponse(userList);
//    }

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
        Optional<User> optionalUser = studentRepository.findByIdAndRoleRoleTypeAndDeletedFalse(userId, STUDENT);
        if (optionalUser.isEmpty()) {
            throw new NotFoundException("student not found");
        }
        User user = optionalUser.get();
        user.setRole(new Role("intern", RoleType.INTERN));
        userRepository.save(user);
        return ResponseData.successResponse("student succesfuly intern ");
    }

    public ResponseData<?> getUserById(UUID userId) {
        Optional<User> optionalUser = studentRepository.findById(userId);
        if (optionalUser.isEmpty()) {
            throw new NotFoundException("student not found");
        }
        User user = optionalUser.get();
        Optional<Group> optionalGroup = groupRepository.findById(user.getId());
        Group group = optionalGroup.get();

        StudentDto responsStudentDo = studentMapper.toResponsStudentDo(user, group);
        return ResponseData.successResponse(responsStudentDo);
    }

    public ResponseData<?> getAllStudent(int page, int size) {
        Pageable pageable = PageRequest.of(size, page);
        Page<PaymentForMonth> paymentForMonthPage = paymentForServiceRepository.findAll(pageable);
        if (paymentForMonthPage.isEmpty()) {
            throw new NotFoundException(MessageService.getMessage(MessageKey.NO_CONTENT));
        }
        Map<String, Object> response = new HashMap<>();
        response.put("data", paymentForMonthPage.toList());
        response.put("total", paymentForMonthPage.getTotalElements());
        response.put("totalPages", paymentForMonthPage.getTotalPages());
        return new ResponseData<>(response, true);
    }
}
