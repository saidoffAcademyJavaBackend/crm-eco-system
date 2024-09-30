package uz.saidoff.crmecosystem.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.saidoff.crmecosystem.enums.Permissions;

import java.sql.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeCreatDto {

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

//    private List<Permissions> permissions;

}
