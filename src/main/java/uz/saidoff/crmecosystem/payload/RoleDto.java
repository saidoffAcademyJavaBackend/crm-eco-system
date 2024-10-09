package uz.saidoff.crmecosystem.payload;

import lombok.Getter;
import lombok.Setter;
import uz.saidoff.crmecosystem.enums.RoleType;

@Setter
@Getter
public class RoleDto {

    private String name;
    private RoleType roleType;


}
