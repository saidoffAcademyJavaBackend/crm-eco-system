package uz.saidoff.crmecosystem.mapper;


import uz.saidoff.crmecosystem.entity.Group;
import uz.saidoff.crmecosystem.entity.auth.User;
import uz.saidoff.crmecosystem.enums.Permissions;
import uz.saidoff.crmecosystem.enums.RoleType;
import uz.saidoff.crmecosystem.exception.NotFoundException;
import uz.saidoff.crmecosystem.payload.StudentDto;
import uz.saidoff.crmecosystem.payload.StudentResponseDto;
import uz.saidoff.crmecosystem.payload.UserDto;
import uz.saidoff.crmecosystem.repository.RoleRepository;
import uz.saidoff.crmecosystem.repository.StudentRepository;
import uz.saidoff.crmecosystem.util.MessageKey;
import uz.saidoff.crmecosystem.util.MessageService;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;


public class StudentMapper {

    private StudentRepository studentRepository;
    private RoleRepository roleRepository;

    public User toFromUserEntity(StudentResponseDto studentResponseDto) {

        User user = new User();

        user.setFirstName(studentResponseDto.getFirstName());
        user.setLastName(studentResponseDto.getLastName());
        user.setFatherName(studentResponseDto.getFatherName());
        user.setPhoneNumber(studentResponseDto.getPhoneNumber());
        user.setSecondPhoneNumber(studentResponseDto.getSecundPhoneNumber());
        user.setBirthDate(studentResponseDto.getDateOfBirth());
        user.setBirthPlace(studentResponseDto.getPlaceOfBirth());

        user.setSpeciality(studentResponseDto.getSpecialty());
        user.setPassportSeries(studentResponseDto.getPassportSeries());
        user.setRole(roleRepository.findByRoleType(RoleType.STUDENT).orElseThrow(
                () -> new NotFoundException(MessageService.getMessage(MessageKey.ROLE_NOT_FOUND))
        ));
        user.setSalary(studentResponseDto.getSalary());
        user.setPermissions(List.of(Permissions.CREATE_STUDENT));
        return user;
    }

    public List<StudentDto> toDto(List<User> users) {
        List<StudentDto> userDto = new ArrayList<>();
        for (User user : users) {
            userDto.add(toDtos(user));
        }
        return userDto;
    }

    public StudentDto toDtos(User user) {

        StudentDto studentResponse = new StudentDto();

        studentResponse.setFirstName(user.getFirstName());
        studentResponse.setLastName(user.getLastName());
        studentResponse.setFatherName(user.getFatherName());
        studentResponse.setPhoneNumber(user.getPhoneNumber());
        studentResponse.setSecundPhoneNumber(user.getSecondPhoneNumber());
        studentResponse.setDateOfBirth(user.getBirthDate());
        studentResponse.setPlaceOfBirth(user.getBirthPlace());
        studentResponse.setSpecialty(user.getSpeciality());
        studentResponse.setPassportSeries(user.getPassportSeries());
        studentResponse.setRole(user.getRole());
//        studentResponse.setAddedBy(user.getAddedBy());
        studentResponse.setPaymentAmount(user.getSalary());
        return studentResponse;
    }

    public StudentDto toResponsStudentDo(User user, Group group) {

        StudentDto studentResponseDto = new StudentDto();

        studentResponseDto.setFirstName(user.getFirstName());
        studentResponseDto.setLastName(user.getLastName());
        studentResponseDto.setFatherName(user.getFatherName());
        studentResponseDto.setPhoneNumber(user.getPhoneNumber());
        studentResponseDto.setSecundPhoneNumber(user.getSecondPhoneNumber());
        studentResponseDto.setDateOfBirth(user.getBirthDate());
        studentResponseDto.setPlaceOfBirth(user.getBirthPlace());
        studentResponseDto.setSpecialty(user.getSpeciality());
        studentResponseDto.setPassportSeries(user.getPassportSeries());
        studentResponseDto.setRole(user.getRole());

//        studentResponseDto.setAddedBy(user.getAddedBy());

        studentResponseDto.setPaymentAmount(user.getSalary());
        studentResponseDto.setTeacherId(group.getTeacher().getId());
        studentResponseDto.setGroup(group);

        return studentResponseDto;
    }
}
