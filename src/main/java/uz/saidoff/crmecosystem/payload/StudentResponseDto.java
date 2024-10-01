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
public class StudentResponseDto {

    private String firstName;
    private String lastName;
    private String fatherName;
    private String phoneNumber;
    private String secondPhoneNumber;
    private Date dateOfBirth;
    private String placeOfBirth; // tug'ulgan joyi
    private String currentResidence; // hozirgi yashash joyi
    private Speciality specialty; // mutahasisligi
    private String passportSeries; // pasport seriasi

    private Role role;
    private Double salary;
    private String addedBy;
    private java.sql.Date startWork;



}
