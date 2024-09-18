package uz.saidoff.crmecosystem.entity;

import jakarta.persistence.Entity;
import lombok.*;
import uz.saidoff.crmecosystem.entity.template.AbsEntity;
import uz.saidoff.crmecosystem.enums.RoleType;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Role extends AbsEntity {

    private RoleType roleType;
}
