package uz.saidoff.crmecosystem.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import uz.saidoff.crmecosystem.entity.Transaction;
import uz.saidoff.crmecosystem.payload.OutcomeCreatDto;
import uz.saidoff.crmecosystem.payload.OutcomeDto;
import uz.saidoff.crmecosystem.payload.OutcomeUpdateDto;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class OutcomeMapper {
    public Transaction toEntity(OutcomeCreatDto outcomeCreatDto) {
        Transaction transaction = new Transaction();
        transaction.setAmount(outcomeCreatDto.getAmount());
        transaction.setDescription(outcomeCreatDto.getDescription());
        transaction.setCurrency(outcomeCreatDto.getCurrency());
        transaction.setIsIncome(false);
        return transaction;
    }
    public OutcomeDto toDto(Transaction transaction) {
        OutcomeDto outcomeDto = new OutcomeDto();
        outcomeDto.setOutcomeId(transaction.getId());
        outcomeDto.setAmount(transaction.getAmount());
        outcomeDto.setDescription(transaction.getDescription());
        outcomeDto.setCurrency(transaction.getCurrency());
        outcomeDto.setIsIncome(transaction.getIsIncome());
        outcomeDto.setAttachmentId(transaction.getAttachment().getId());
        outcomeDto.setCategoryId(transaction.getCategory().getId());
        return outcomeDto;
    }

    public List<OutcomeDto> toDto(List<Transaction> transactions) {
        List<OutcomeDto> outcomeDto = new ArrayList<>();
        for (Transaction transaction : transactions) {
            outcomeDto.add(toDto(transaction));
        }
        return outcomeDto;
    }

    public Transaction toUpdate(Transaction transaction, OutcomeUpdateDto outcomeUpdateDto) {
        if (outcomeUpdateDto.getAmount()!=null) transaction.setAmount(outcomeUpdateDto.getAmount());
        if (outcomeUpdateDto.getDescription()!=null) transaction.setDescription(outcomeUpdateDto.getDescription());
        if (outcomeUpdateDto.getCurrency()!=null) transaction.setCurrency(outcomeUpdateDto.getCurrency());
        if (outcomeUpdateDto.getIsIncome()!=null) transaction.setIsIncome(outcomeUpdateDto.getIsIncome());
        return transaction;
    }
}
