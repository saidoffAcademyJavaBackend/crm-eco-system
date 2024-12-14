package uz.saidoff.crmecosystem.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.sql.Date;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class StudentResponseDto {

    private UUID groupId;
    private UUID attachmentId;
    private String firstName;
    private String lastName;
    private String fatherName;
    private String phoneNumber;
    private String secondPhoneNumber;
    private String password;
    private Date dateOfBirth;
    private String placeOfBirth; // tug'ulgan joyi
    private String currentResidence; // hozirgi yashash joyi
    private String specialty; // mutahasisligi
    private String passportSeries; // pasport seriasi

    private String role ;
    private Double salary;
    private Date startWork;



}
