package uz.saidoff.crmecosystem.payload.OutcomeAndIncome;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OutcomeDtoForGet {
    private String employee;
    private String advertisement;
    private String another;
}
