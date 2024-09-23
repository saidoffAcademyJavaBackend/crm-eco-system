package uz.saidoff.crmecosystem.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.*;
import uz.saidoff.crmecosystem.entity.template.AbsEntity;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GroupType extends AbsEntity {

    @Column(nullable = false, unique = true)
    private String specialty;

    private String description;
}
