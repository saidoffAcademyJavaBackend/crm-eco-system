package uz.saidoff.crmecosystem.entity;

import jakarta.persistence.Entity;
import lombok.*;
import uz.saidoff.crmecosystem.entity.template.AbsEntity;
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Speciality extends AbsEntity {
    private String name;
    private String description;
}
