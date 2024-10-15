package uz.saidoff.crmecosystem.entity;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.saidoff.crmecosystem.entity.template.AbsEntity;

import java.rmi.server.UID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class PaymentForMonth extends AbsEntity {
    private UID groupStudentId;
    private String month;
    private Double paymentAmount;
    private boolean status;
    private boolean active;
    private boolean currentMonth;
}
