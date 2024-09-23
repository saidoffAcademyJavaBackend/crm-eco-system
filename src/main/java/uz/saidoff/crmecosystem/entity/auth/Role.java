package uz.saidoff.crmecosystem.entity.auth;

import jakarta.persistence.*;
import lombok.*;
import uz.saidoff.crmecosystem.entity.template.AbsEntity;
import uz.saidoff.crmecosystem.enums.RoleType;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Role extends AbsEntity  {

    private String name;

    @Enumerated(EnumType.STRING)
    private RoleType roleType;
}
