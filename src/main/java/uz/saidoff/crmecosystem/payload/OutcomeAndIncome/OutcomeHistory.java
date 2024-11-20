package uz.saidoff.crmecosystem.payload.OutcomeAndIncome;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OutcomeHistory {
    private Double amount;
    private String nameCategory;
    private Double categoryInterest;
}
