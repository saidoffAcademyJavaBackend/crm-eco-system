package uz.saidoff.crmecosystem.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.saidoff.crmecosystem.entity.auth.User;
import uz.saidoff.crmecosystem.entity.template.AbsEntity;
import uz.saidoff.crmecosystem.enums.Currency;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Transaction extends AbsEntity {

    private Double amount;

    private String description;

    @ManyToOne
    private Category category;

    @Enumerated(EnumType.STRING)
    private Currency currency;

    private Boolean isIncome;

    @ManyToOne
    private Attachment attachment;

    // TRANSACTION FOR SOMEONE OF FROM SOMEONE
    @OneToOne
    private UserPayments userPayments;

}
