package uz.saidoff.crmecosystem.mapper;


import uz.saidoff.crmecosystem.entity.Group;
import uz.saidoff.crmecosystem.entity.auth.User;
import uz.saidoff.crmecosystem.payload.StudentDto;
import uz.saidoff.crmecosystem.payload.StudentResponseDto;
import uz.saidoff.crmecosystem.payload.UserDto;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;


public class StudentMapper {

    public User toFromUserEntity(StudentResponseDto studentResponseDto, Group group) {

        User user = new User();

        user.setFirstName(studentResponseDto.getFirstName());
        user.setLastName(studentResponseDto.getLastName());
        user.setFatherName(studentResponseDto.getFatherName());
        user.setPhoneNumber(studentResponseDto.getPhoneNumber());
        user.setSecondPhoneNumber(studentResponseDto.getSecundPhoneNumber());
        user.setBirthDate(studentResponseDto.getDateOfBirth());
        user.setBirthPlace(studentResponseDto.getPlaceOfBirth());
        user.setSpecialty(studentResponseDto.getSpecialty());
        user.setPassportSeries(studentResponseDto.getPassportSeries());
        user.setRole(studentResponseDto.getRole());
        user.setSalary(studentResponseDto.getSalary());
        user.setGroup(group);

        user.setDeleted(false);
        user.setCreatedAt(Timestamp.from(Instant.now()));
        user.setUpdatedAt(Timestamp.from(Instant.now()));


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
        StudentDto studentResponseDto = new StudentDto();

        studentResponseDto.setFirstName(user.getFirstName());
        studentResponseDto.setLastName(user.getLastName());
        studentResponseDto.setFatherName(user.getFatherName());
        studentResponseDto.setPhoneNumber(user.getPhoneNumber());
        studentResponseDto.setSecundPhoneNumber(user.getSecondPhoneNumber());
        studentResponseDto.setDateOfBirth(user.getBirthDate());
        studentResponseDto.setPlaceOfBirth(user.getBirthPlace());
        studentResponseDto.setSpecialty(user.getSpecialty());
        studentResponseDto.setPassportSeries(user.getPassportSeries());
        studentResponseDto.setRole(user.getRole());
        studentResponseDto.setSalary(user.getSalary());
        studentResponseDto.setAddedBy(user.getAddedBy());
        studentResponseDto.setGroup(user.getGroup());
        studentResponseDto.setTeacherId(user.getGroup().getTeacher().getId());
        return studentResponseDto;
    }
}
