package uz.saidoff.crmecosystem.mapper;


import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import uz.saidoff.crmecosystem.entity.Group;
import uz.saidoff.crmecosystem.entity.Speciality;
import uz.saidoff.crmecosystem.entity.auth.Role;
import uz.saidoff.crmecosystem.entity.auth.User;
import uz.saidoff.crmecosystem.payload.StudentDto;
import uz.saidoff.crmecosystem.payload.StudentResponseDto;
import uz.saidoff.crmecosystem.repository.AttachmentRepository;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class StudentMapper {
    private final AttachmentRepository attachmentRepository;
    private final PasswordEncoder passwordEncoder;

    public User toFromUserEntity(StudentResponseDto studentResponseDto, Speciality speciality, Role role) throws ParseException {

        User user = new User();
        if (studentResponseDto.getAttachmentId() != null) {
            user.setAttachment(attachmentRepository.findById(studentResponseDto.getAttachmentId()).orElse(null));
        }

        user.setFirstName(studentResponseDto.getFirstName());

        user.setLastName(studentResponseDto.getLastName());

        user.setFatherName(studentResponseDto.getFatherName());

        user.setBirthDate(studentResponseDto.getDateOfBirth());

        user.setPhoneNumber(studentResponseDto.getPhoneNumber());

        user.setSecondPhoneNumber(studentResponseDto.getSecondPhoneNumber());

        user.setPassword(passwordEncoder.encode(studentResponseDto.getPassword()));

        user.setBirthPlace(studentResponseDto.getPlaceOfBirth());

        user.setCurrentResidence(studentResponseDto.getCurrentResidence());

        user.setPassportSeries(studentResponseDto.getPassportSeries());

        user.setSpeciality(speciality);

        user.setRole(role);

        user.setSalary(studentResponseDto.getSalary());

        user.setStartWork(studentResponseDto.getStartWork());

        user.setNumberOfChildren(studentResponseDto.getNumberOfChildren());

        user.setGender(studentResponseDto.getGender());

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
        studentResponse.setSecondPhoneNumber(user.getSecondPhoneNumber());
        studentResponse.setDateOfBirth(user.getBirthDate());
        studentResponse.setPlaceOfBirth(user.getBirthPlace());
        studentResponse.setSpecialtyId(user.getSpeciality().getId());
        studentResponse.setPassportSeries(user.getPassportSeries());
        studentResponse.setRoleId(user.getRole().getId());
        studentResponse.setAddedBy(user.getCreatedBy());
        studentResponse.setPaymentAmount(user.getSalary());
        return studentResponse;
    }

    public StudentDto toResponsStudentDo(User user, Group group) {

        StudentDto studentResponseDto = new StudentDto();
        if (user.getAttachment() != null) {
            studentResponseDto.setAttachmentId(user.getAttachment().getId());
        }
        studentResponseDto.setFirstName(user.getFirstName());
        studentResponseDto.setLastName(user.getLastName());
        studentResponseDto.setFatherName(user.getFatherName());
        studentResponseDto.setPhoneNumber(user.getPhoneNumber());
        studentResponseDto.setSecondPhoneNumber(user.getSecondPhoneNumber());
        studentResponseDto.setDateOfBirth(user.getBirthDate());
        studentResponseDto.setPlaceOfBirth(user.getBirthPlace());
        studentResponseDto.setSpecialtyId(user.getSpeciality().getId());
        studentResponseDto.setPassportSeries(user.getPassportSeries());
        studentResponseDto.setRoleId(user.getRole().getId());
        studentResponseDto.setAddedBy(user.getCreatedBy());
        studentResponseDto.setPaymentAmount(user.getSalary());
        studentResponseDto.setTeacherId(group.getTeacher().getId());
        studentResponseDto.setGroupId(group.getId());

        return studentResponseDto;
    }

}
