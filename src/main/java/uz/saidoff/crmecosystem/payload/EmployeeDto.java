package uz.saidoff.crmecosystem.payload;

import lombok.*;
import uz.saidoff.crmecosystem.enums.Permissions;


import java.sql.Date;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDto {

    private UUID employeeId;
    private UUID attachmentId;
    private String password;
    private String firstName;
    private String lastName;
    private String fatherName;
    private String phoneNumber;
    private String secondPhoneNumber;
    private Date birthDate;
    private String birthPlace;
    private String currentResidence;
    private UUID specialtyId;
    private String passportSeries;
    private Double salary;
    private String addedBy;
    private Date startWork;
    private UUID roleId;

    private List<Permissions> permissions;


}
