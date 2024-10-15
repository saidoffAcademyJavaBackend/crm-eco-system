package uz.saidoff.crmecosystem.payload.PaymentForMonthDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.rmi.server.UID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PaymentForMonthCreatDto {
    private UID groupStudentId;
    private String month;
    private Double paymentAmount;
    private boolean status;
    private boolean active;
    private boolean currentMonth;
}
