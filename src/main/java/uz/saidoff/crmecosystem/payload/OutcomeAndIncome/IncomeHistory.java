package uz.saidoff.crmecosystem.payload.OutcomeAndIncome;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IncomeHistory {
    private Double FromProject;
    private Double InterestOnProject;
    private Double FromStudent;
    private Double InterestOnStudent;
    private Double FromIntern;
    private Double InterestOnIntern;
    private Double FromAdvertisement;
    private Double InterestOnAdvertisement;
    private Double FromAnother;
    private Double InterestOnAnother;
    private Double AllOutcomesSum;
}
