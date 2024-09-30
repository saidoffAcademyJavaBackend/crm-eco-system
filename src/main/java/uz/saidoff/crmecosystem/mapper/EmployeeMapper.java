package uz.saidoff.crmecosystem.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import uz.saidoff.crmecosystem.entity.auth.User;
import uz.saidoff.crmecosystem.enums.RoleType;
import uz.saidoff.crmecosystem.exception.NotFoundException;
import uz.saidoff.crmecosystem.payload.EmployeeCreatDto;
import uz.saidoff.crmecosystem.payload.EmployeeDto;
import uz.saidoff.crmecosystem.repository.FileRepository;
import uz.saidoff.crmecosystem.repository.RoleRepository;
import uz.saidoff.crmecosystem.repository.SpecialityRepository;
import uz.saidoff.crmecosystem.util.MessageKey;
import uz.saidoff.crmecosystem.util.MessageService;


import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class EmployeeMapper {

    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final SpecialityRepository specialityRepository;
    private final FileRepository fileRepository;

    public User toEntity(EmployeeCreatDto employeeCreatDto) {
        User user = new User();
        user.setAttachment(fileRepository.findById(employeeCreatDto.getAttachmentId()).orElse(null));
        user.setPassword(passwordEncoder.encode(employeeCreatDto.getPassword()));
        user.setFirstName(employeeCreatDto.getFirstName());
        user.setLastName(employeeCreatDto.getLastName());
        user.setFatherName(employeeCreatDto.getFatherName());
        user.setPhoneNumber(employeeCreatDto.getPhoneNumber());
        user.setSecondPhoneNumber(employeeCreatDto.getSecondPhoneNumber());
        user.setBirthDate(employeeCreatDto.getBirthDate());
        user.setBirthPlace(employeeCreatDto.getBirthPlace());
        user.setCurrentResidence(employeeCreatDto.getCurrentResidence());
        user.setSpeciality(specialityRepository.findById(employeeCreatDto.getSpecialtyId()).orElse(null));
        user.setStartWork(employeeCreatDto.getStartWork());
        user.setRole(roleRepository.findByRoleType(RoleType.EMPLOYEE).orElseThrow(
                () -> new NotFoundException(MessageService.getMessage(MessageKey.ROLE_NOT_FOUND))));

        return user;
    }

    public EmployeeDto toDto(User user) {
        EmployeeDto employeeDto = new EmployeeDto();
        employeeDto.setAttachmentId(user.getAttachment().getId());
        employeeDto.setEmployeeId(user.getId());
        employeeDto.setFirstName(user.getFirstName());
        employeeDto.setLastName(user.getLastName());
        employeeDto.setFatherName(user.getFatherName());
        employeeDto.setPhoneNumber(user.getPhoneNumber());
        employeeDto.setSecondPhoneNumber(user.getSecondPhoneNumber());
        employeeDto.setBirthDate(user.getBirthDate());
        employeeDto.setBirthPlace(user.getBirthPlace());
        employeeDto.setCurrentResidence(user.getCurrentResidence());
        employeeDto.setSpecialtyId(user.getSpeciality().getId());
        employeeDto.setStartWork(user.getStartWork());
        employeeDto.setRoleId(user.getRole().getId());
        return employeeDto;
    }

    public List<EmployeeDto> toDto(List<User> users) {
        List<EmployeeDto> employeeDto = new ArrayList<>();
        for (User user : users) {
            employeeDto.add(toDto(user));
        }
        return employeeDto;
    }

    public User updateEntity(User user, EmployeeCreatDto employeeCreatDto) {
        if (employeeCreatDto.getAttachmentId() != null) {
            user.setAttachment(fileRepository.findById(employeeCreatDto.getAttachmentId()).orElse(null));
        }
        if (employeeCreatDto.getFirstName()!=null) {
            user.setFirstName(employeeCreatDto.getFirstName());
        }
        if (employeeCreatDto.getLastName()!=null) {
            user.setLastName(employeeCreatDto.getLastName());
        }
        if (employeeCreatDto.getFatherName()!=null) {
            user.setFatherName(employeeCreatDto.getFatherName());
        }
        if (employeeCreatDto.getPhoneNumber()!=null) {
            user.setPhoneNumber(employeeCreatDto.getPhoneNumber());
        }
        if (employeeCreatDto.getSecondPhoneNumber()!=null) {
            user.setSecondPhoneNumber(employeeCreatDto.getSecondPhoneNumber());
        }
        if (employeeCreatDto.getBirthDate()!=null) {
            user.setBirthDate(employeeCreatDto.getBirthDate());
        }
        if (employeeCreatDto.getBirthPlace()!=null) {
            user.setBirthPlace(employeeCreatDto.getBirthPlace());
        }
        if (employeeCreatDto.getCurrentResidence()!=null) {
            user.setCurrentResidence(employeeCreatDto.getCurrentResidence());
        }
        if (employeeCreatDto.getSpecialtyId()!=null) {
            user.setSpeciality(specialityRepository.findById(employeeCreatDto.getSpecialtyId()).orElse(null));
        }
        if (employeeCreatDto.getStartWork()!=null) {
            user.setStartWork(employeeCreatDto.getStartWork());
        }
        return user;
    }
}
