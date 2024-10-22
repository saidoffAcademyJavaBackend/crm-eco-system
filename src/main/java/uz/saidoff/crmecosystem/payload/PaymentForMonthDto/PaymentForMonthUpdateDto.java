package uz.saidoff.crmecosystem.payload.PaymentForMonthDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PaymentForMonthUpdateDto {
    private UUID paymentForMonthId;
    private UUID groupStudentId;
    private UUID userId;
    private Date month;
    private Integer startMonth;
    private Integer allMonths;
    private Double paymentAmount;
    private Double allPaymentAmount;
    private boolean active;
    private boolean currentMonth;
}
