package uz.saidoff.crmecosystem.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.saidoff.crmecosystem.enums.Permissions;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class InternAddDto {
    private String firsName;
    private String lastName;
    private String fatherName;
    private String phoneNumber;
    private String secondPhoneNumber;
    private String password;
    private UUID attachmentId;
    private Date birthDate;
    private String birthPlace;
    private String currentResidence;
    private String specialty;
    private String passportSeries;
    private Double paymentAmount;
    private Date startStudying;
    private Boolean gender;
    private List<Permissions> permissionsList;
}
