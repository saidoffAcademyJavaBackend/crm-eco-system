package uz.saidoff.crmecosystem.entity;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.saidoff.crmecosystem.entity.template.AbsEntity;
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Balance extends AbsEntity {
    private Double amount;
}
