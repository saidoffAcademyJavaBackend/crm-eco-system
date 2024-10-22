package uz.saidoff.crmecosystem.entity;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.saidoff.crmecosystem.entity.template.AbsEntity;
import uz.saidoff.crmecosystem.enums.PaymentMonthStatus;

import java.sql.Date;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class PaymentForMonth extends AbsEntity {
    private UUID groupStudentId;
    private Date month;
    private Double paymentAmount=0.0;
    private PaymentMonthStatus status;
    private boolean active;
    private boolean currentMonth;
}
