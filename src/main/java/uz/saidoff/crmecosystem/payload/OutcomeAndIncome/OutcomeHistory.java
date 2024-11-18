package uz.saidoff.crmecosystem.payload.OutcomeAndIncome;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OutcomeHistory {
    private Double FromEmployee;
    private Double InterestOnEmployee;
    private Double FromAdvertisement;
    private Double InterestOnAdvertisement;
    private Double FromAnother;
    private Double InterestOnAnother;
    private Double AllOutcomesSum;
}
