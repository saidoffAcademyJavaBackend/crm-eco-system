package uz.saidoff.crmecosystem.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
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
    private Timestamp birthDate;
    private String birthPlace;
    private String currentResidence;
    private String specialty;
    private String passportSeries;
    private Double salary;
    private String addedBy;
    private Timestamp startWork;
    private UUID roleId;

}
