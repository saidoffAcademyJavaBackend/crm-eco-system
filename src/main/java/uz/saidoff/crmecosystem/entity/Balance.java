package uz.saidoff.crmecosystem.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.saidoff.crmecosystem.entity.template.AbsEntity;
import uz.saidoff.crmecosystem.enums.Currency;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Balance extends AbsEntity {
    private Double amount;

    @Enumerated(EnumType.STRING)
    private Currency currency;
}
