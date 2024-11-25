package uz.saidoff.crmecosystem.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import uz.saidoff.crmecosystem.entity.Attachment;
import uz.saidoff.crmecosystem.entity.ProjectUser;
import uz.saidoff.crmecosystem.entity.Speciality;
import uz.saidoff.crmecosystem.entity.auth.Role;
import uz.saidoff.crmecosystem.entity.auth.User;
import uz.saidoff.crmecosystem.enums.Permissions;
import uz.saidoff.crmecosystem.payload.InternAddDto;
import uz.saidoff.crmecosystem.payload.InternGetDto;
import uz.saidoff.crmecosystem.payload.ProjectResponseDto;

import java.sql.Date;
import java.util.*;

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
        internGetDto.setSpecialtyId(user.getSpeciality().getId());
        internGetDto.setCurrentResidence(user.getCurrentResidence());
        internGetDto.setPaymentAmount(user.getSalary());
        internGetDto.setStartStudying(user.getStartStudying());
        internGetDto.setPermissionsList(user.getPermissions());
        if (user.getAttachment() != null)
            internGetDto.setAttachmentId(user.getAttachment().getId());
        return internGetDto;
    }


    public User toUser(UUID userId, InternAddDto internAddDto, Speciality speciality, Role role, Optional<Attachment> attachment) {
        User user = new User();
        user.setBirthPlace(internAddDto.getBirthPlace());
        user.setFirstName(internAddDto.getFirsName());
        user.setLastName(internAddDto.getLastName());
        user.setFatherName(internAddDto.getFatherName());
        user.setBirthDate(new Date(internAddDto.getBirthDate().getTime()));
        user.setPassportSeries(internAddDto.getPassportSeries());
        user.setPhoneNumber(internAddDto.getPhoneNumber());
        user.setSecondPhoneNumber(internAddDto.getSecondPhoneNumber());
        user.setSpeciality(speciality);
        user.setCurrentResidence(internAddDto.getCurrentResidence());
        user.setStartStudying(new Date(internAddDto.getStartStudying().getTime()));
        user.setRole(role);
        user.setPermissions(internAddDto.getPermissionsList() == null ?
                Collections.singletonList(Permissions.GET_INTERN)
                : internAddDto.getPermissionsList());
        user.setCreatedBy(userId);
        attachment.ifPresent(user::setAttachment);
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
        intern.setAttachment(attachment.getId() != null ? attachment : null);
        return intern;
    }

    public ProjectResponseDto getInternProjects(ProjectUser userProjects) {
        ProjectResponseDto projectResponseDto = new ProjectResponseDto();
        projectResponseDto.setProjectId(userProjects.getProject().getId());
        projectResponseDto.setName(userProjects.getProject().getName());
        projectResponseDto.setStartDate(userProjects.getProject().getStartDate());
        projectResponseDto.setEndDate(userProjects.getProject().getEndDate());
        //  owner Id qoshib ketish kk
        return projectResponseDto;
    }
}
