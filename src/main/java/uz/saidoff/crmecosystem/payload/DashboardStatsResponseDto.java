package uz.saidoff.crmecosystem.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DashboardStatsResponseDto {
    private Boolean isIncome;
    private String duration;
    private String currency;
    private List<TransactionSummaryDto> transactions;
}
