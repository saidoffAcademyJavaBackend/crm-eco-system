package uz.saidoff.crmecosystem.payload.OutcomeAndIncome;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.saidoff.crmecosystem.enums.Currency;
import uz.saidoff.crmecosystem.enums.RoleType;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SalaryHistory {
    private String categoryName;
    private Currency currency;
    private boolean income;
    private RoleType roleType;
}
