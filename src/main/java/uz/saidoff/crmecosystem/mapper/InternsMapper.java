package uz.saidoff.crmecosystem.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import uz.saidoff.crmecosystem.entity.auth.Role;
import uz.saidoff.crmecosystem.entity.auth.User;
import uz.saidoff.crmecosystem.enums.Permissions;
import uz.saidoff.crmecosystem.enums.RoleType;
import uz.saidoff.crmecosystem.payload.InternGetDto;

import java.security.Permission;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class InternsMapper {
    public InternGetDto toInternGetDto(User user) {
        InternGetDto internGetDto = new InternGetDto();
        internGetDto.setInterId((user.getId()));
        internGetDto.setAddedBy(user.getCreatedBy());
        internGetDto.setBirthPlace(user.getBirthPlace());
        internGetDto.setFirsName(user.getFirstName());
        internGetDto.setLastName(user.getLastName());
        internGetDto.setFatherName(user.getFatherName());
        internGetDto.setBirthDate(user.getBirthDate());
        internGetDto.setPassportSeries(user.getPassportSeries());
        internGetDto.setPhoneNumber(user.getPhoneNumber());
        internGetDto.setSecondPhoneNumber(user.getSecondPhoneNumber());
        internGetDto.setSpecialty(user.getSpeciality().getName());
        internGetDto.setCurrentResidence(user.getCurrentResidence());
        internGetDto.setPaymentAmount(user.getSalary());
        internGetDto.setStartStudying(user.getStartStudying());
        return internGetDto;
    }


    public User toUser(UUID userId, InternGetDto internGetDto) {
        User user = new User();
        user.setId(internGetDto.getInterId());
        user.setAddedBy(internGetDto.getAddedBy());
        user.setBirthPlace(internGetDto.getBirthPlace());
        user.setFirstName(internGetDto.getFirsName());
        user.setLastName(internGetDto.getLastName());
        user.setFatherName(internGetDto.getFatherName());
        user.setBirthDate(internGetDto.getBirthDate());
        user.setPassportSeries(internGetDto.getPassportSeries());
        user.setPhoneNumber(internGetDto.getPhoneNumber());
        user.setSecondPhoneNumber(internGetDto.getSecondPhoneNumber());
        user.setSpecialty(internGetDto.getSpecialty());
        user.setCurrentResidence(internGetDto.getCurrentResidence());
        user.setSalary(internGetDto.getPaymentAmount());
        user.setStartWork(internGetDto.getStartStudying());
        user.setRole(new Role("intern", RoleType.INTERN));
        user.setPermissions(List.of(Permissions.CREATE_INTERN));
        user.setCreatedBy(userId);
        user.setCreatedAt(Timestamp.from(Instant.now()));
        return user;
    }
}
