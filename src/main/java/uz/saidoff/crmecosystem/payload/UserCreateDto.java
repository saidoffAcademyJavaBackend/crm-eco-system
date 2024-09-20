package uz.saidoff.crmecosystem.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.saidoff.crmecosystem.entity.auth.Role;
import uz.saidoff.crmecosystem.valid.PasswordValidate;

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
    private String username;
    private String phoneNumber;
    private boolean enabled;
    private Role role;
}
