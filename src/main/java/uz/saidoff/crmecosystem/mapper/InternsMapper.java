package uz.saidoff.crmecosystem.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import uz.saidoff.crmecosystem.entity.Attachment;
import uz.saidoff.crmecosystem.entity.Speciality;
import uz.saidoff.crmecosystem.entity.auth.Role;
import uz.saidoff.crmecosystem.entity.auth.User;
import uz.saidoff.crmecosystem.enums.Permissions;
import uz.saidoff.crmecosystem.payload.InternGetDto;

import java.sql.Date;
import java.util.Collections;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class InternsMapper {
    public InternGetDto toInternGetDto(User user) {
        InternGetDto internGetDto = new InternGetDto();
        internGetDto.setInterId((user.getId()));
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
        internGetDto.setPermissionsList(user.getPermissions());
        internGetDto.setAttachmentId(user.getAttachment().getId());
        return internGetDto;
    }


    public User toUser(UUID userId, InternGetDto internGetDto, Speciality speciality, Role role, Attachment attachment) {
        User user = new User();
        user.setId(internGetDto.getInterId());
        user.setBirthPlace(internGetDto.getBirthPlace());
        user.setFirstName(internGetDto.getFirsName());
        user.setLastName(internGetDto.getLastName());
        user.setFatherName(internGetDto.getFatherName());
        user.setBirthDate(new Date(internGetDto.getBirthDate().getTime()));
        user.setPassportSeries(internGetDto.getPassportSeries());
        user.setPhoneNumber(internGetDto.getPhoneNumber());
        user.setSecondPhoneNumber(internGetDto.getSecondPhoneNumber());
        user.setSpeciality(speciality);
        user.setCurrentResidence(internGetDto.getCurrentResidence());
        user.setStartStudying(new Date(internGetDto.getStartStudying().getTime()));
        user.setRole(role);
        user.setPermissions(internGetDto.getPermissionsList()==null?
                Collections.singletonList(Permissions.GET_INTERN)
                :internGetDto.getPermissionsList());
        user.setCreatedBy(userId);
        if (attachment != null) {
            user.setAttachment(attachment);
        }
        return user;
    }

    public User toUpdateUser(User intern, InternGetDto internGetDto, Attachment attachment, Role role, Speciality speciality) {
        intern.setFirstName(internGetDto.getFirsName());
        intern.setLastName(internGetDto.getLastName());
        intern.setFatherName(internGetDto.getFatherName());
        intern.setBirthDate(new Date(internGetDto.getBirthDate().getTime()));
        intern.setPassportSeries(internGetDto.getPassportSeries());
        intern.setPhoneNumber(internGetDto.getPhoneNumber());
        intern.setSecondPhoneNumber(internGetDto.getSecondPhoneNumber());
        intern.setRole(role);
        intern.setPermissions(internGetDto.getPermissionsList());
        intern.setBirthPlace(internGetDto.getBirthPlace());
        intern.setCurrentResidence(internGetDto.getCurrentResidence());
        intern.setStartStudying(new Date(internGetDto.getStartStudying().getTime()));
        intern.setSpeciality(speciality);
        intern.setAttachment(attachment);
        return intern;
    }
}
