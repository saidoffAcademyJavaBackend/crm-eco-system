package uz.saidoff.crmecosystem.payload.OutcomeAndIncome;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OutcomeHistoryList {
    private Double allAmount;
    private List<OutcomeHistory> outcomeHistory;
}
