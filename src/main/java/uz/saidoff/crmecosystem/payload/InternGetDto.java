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
public class InternGetDto {
    private UUID interId;
    private String firsName;
    private String lastName;
    private String fatherName;
    private String phoneNumber;
    private String secondPhoneNumber;
    private Timestamp birthDate;
    private String birthPlace;
    private String currentResidence;
    private String specialty;
    private String passportSeries;
    private Double paymentAmount;
    private String addedBy;
    private Timestamp startStudying;
    private String role = "INTERN";
}
