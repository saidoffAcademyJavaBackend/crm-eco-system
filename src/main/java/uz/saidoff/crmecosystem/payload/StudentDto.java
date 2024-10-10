package uz.saidoff.crmecosystem.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.saidoff.crmecosystem.entity.Group;
import uz.saidoff.crmecosystem.entity.Speciality;
import uz.saidoff.crmecosystem.entity.auth.Role;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class StudentDto {
    private UUID attachmentId;
    private UUID teacherId;
    private String firstName;
    private String lastName;
    private String fatherName;
    private String phoneNumber;
    private String secondPhoneNumber;
    private Date dateOfBirth;
    private String placeOfBirth; // tug'ulgan joyi
    private String currentResidence; // hozirgi yashash joyi
    private UUID specialtyId; // mutahasisligi
    private String passportSeries; // pasport seriasi

    private UUID roleId;
    private Double paymentAmount;
    private UUID addedBy;
    private Date startWork;
    private UUID groupId;
}
