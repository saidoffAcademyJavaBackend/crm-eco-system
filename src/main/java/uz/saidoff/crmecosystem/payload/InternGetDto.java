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
public class InternGetDto {
    /* parol qoshih kk
    gender
    gurugha shotta biriktirish kk
     */

    private UUID interId;
    private String firsName;
    private String lastName;
    private String fatherName;
    private String phoneNumber;
    private String secondPhoneNumber;
    private UUID attachmentId;
    private Date birthDate;
    private String birthPlace;
    private String currentResidence;
    private UUID specialtyId;
    private String passportSeries;
    private Double paymentAmount;
    private Date startStudying;
    private boolean gender;
    private List<Permissions> permissionsList;
}
