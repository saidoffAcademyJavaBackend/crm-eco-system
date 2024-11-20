package uz.saidoff.crmecosystem.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.saidoff.crmecosystem.enums.Currency;
import uz.saidoff.crmecosystem.exception.NotFoundException;
import uz.saidoff.crmecosystem.payload.OutcomeAndIncome.IncomeHistoryList;
import uz.saidoff.crmecosystem.repository.FromOutcomeAndIncome.IncomeTransactionRepository;
import uz.saidoff.crmecosystem.response.ResponseData;

import java.sql.Date;

@Service
@RequiredArgsConstructor
public class IncomeService {

    private final IncomeTransactionRepository incomeTransaction;

    public ResponseData<?> income(Date start, Date end, Currency currency) {
        IncomeHistoryList incomeHistory = this.incomeTransaction.getIncomeBreakdownBetweenDates(start, end, currency);
        if (incomeHistory==null) {
            throw new NotFoundException("Income history not found");
        }
        return ResponseData.successResponse(incomeHistory);
    }
}
