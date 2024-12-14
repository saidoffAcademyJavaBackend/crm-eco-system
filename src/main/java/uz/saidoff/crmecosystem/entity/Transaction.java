package uz.saidoff.crmecosystem.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToOne;
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
    @ManyToOne
    private User transactor;

    @ManyToOne
    private Group group;

}
