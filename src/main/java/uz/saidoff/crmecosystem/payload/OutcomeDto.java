package uz.saidoff.crmecosystem.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.saidoff.crmecosystem.enums.Currency;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OutcomeDto {
    private Double amount;
    private String description;
    private Currency currency;
    private UUID categoryId;
    private Boolean isIncome;
    private UUID attachmentId;
}
