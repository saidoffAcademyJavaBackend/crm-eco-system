package uz.saidoff.crmecosystem.entity;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToOne;
import lombok.*;
import uz.saidoff.crmecosystem.entity.auth.User;
import uz.saidoff.crmecosystem.entity.template.AbsEntity;
import uz.saidoff.crmecosystem.enums.PaymentMonthStatus;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor

@Entity
public class UserPayments extends AbsEntity {

  @Enumerated(EnumType.STRING)
  private PaymentMonthStatus paymentMonthStatus;

  @ManyToOne
  private Group group;

  private Integer groupStage;

  @ManyToOne
  private User transactor;

}
