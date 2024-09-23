package uz.saidoff.crmecosystem.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.saidoff.crmecosystem.enums.Permissions;
import uz.saidoff.crmecosystem.valid.PasswordValidate;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserCreateDto {
    private UUID id;
    private String firstName;
    private String lastName;
    @PasswordValidate
    private String password;
    private String phoneNumber;
    private UUID roleId;
    private List<Permissions> permissions;
}
