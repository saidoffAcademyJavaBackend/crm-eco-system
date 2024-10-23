package uz.saidoff.crmecosystem.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.saidoff.crmecosystem.enums.Currency;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class BalanceUpdateIncomeOutcomeDto {
    private UUID balanceId;
    private Double amount;
    private boolean isIncome;
    private Currency currency;
}
