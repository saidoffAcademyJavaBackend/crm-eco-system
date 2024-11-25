package uz.saidoff.crmecosystem.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DashboardStatsRequestDto {
    private Date from;
    private Date to;
    private String duration;
    private String currency;
    private Boolean isIncome;
}
