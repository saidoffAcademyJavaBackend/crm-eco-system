package uz.saidoff.crmecosystem.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.saidoff.crmecosystem.entity.auth.Role;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class StudentUpdateDto {

    private String firstName;
    private String lastName;
    private String phoneNumber;
    private boolean enabled;
    private String numberOfChildren;
    private Double salary;
    private Role role;
}
