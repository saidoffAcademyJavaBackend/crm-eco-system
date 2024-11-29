package uz.saidoff.crmecosystem.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TransactionSummaryDto {
    private String transactionDate;
    private String currency;
    private Double totalAmount;
    private Long totalTransactions;
}
