package uz.saidoff.crmecosystem.payload.PaymentForMonthDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GroupPayDto {
    private Double allIncomeAmount;
    private Double allIncomeReceivedAmount;
    private Double allIncomeUnearnedAmount;
}
