package uz.saidoff.crmecosystem.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import uz.saidoff.crmecosystem.entity.Category;
import uz.saidoff.crmecosystem.entity.Transaction;
import uz.saidoff.crmecosystem.payload.TransactionIncomeAddDto;

@Component
@RequiredArgsConstructor
public class TransactionIncomeMapper {
    public Transaction toIncomeTransaction(TransactionIncomeAddDto transactionIncomeAddDto, Category category) {
        Transaction transaction = new Transaction();
        transaction.setCategory(category);
        transaction.setAmount(transactionIncomeAddDto.getAmount());
        transaction.setDescription(transactionIncomeAddDto.getDescription());
        transaction.setIsIncome(true);
        transaction.setCurrency(transactionIncomeAddDto.getCurrency());
        return transaction;
    }
}
