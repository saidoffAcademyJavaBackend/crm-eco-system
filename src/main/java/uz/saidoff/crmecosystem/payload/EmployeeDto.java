package uz.saidoff.crmecosystem.payload;

import lombok.*;


import java.sql.Date;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDto {

    private UUID employeeId;
    private String password;
    private String firstName;
    private String lastName;
    private String fatherName;
    private String phoneNumber;
    private String secondPhoneNumber;
    private Date birthDate;
    private String birthPlace;
    private String currentResidence;
    private String specialty;
    private String passportSeries;
    private Double salary;
    private String addedBy;
    private Date startWork;
    private UUID roleId;

}
