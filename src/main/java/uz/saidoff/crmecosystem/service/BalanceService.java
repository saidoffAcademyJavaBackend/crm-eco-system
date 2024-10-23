package uz.saidoff.crmecosystem.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.saidoff.crmecosystem.entity.Balance;
import uz.saidoff.crmecosystem.enums.Currency;
import uz.saidoff.crmecosystem.exception.NotFoundException;
import uz.saidoff.crmecosystem.payload.BalanceUpdateIncomeOutcomeDto;
import uz.saidoff.crmecosystem.repository.BalanceRepository;
import uz.saidoff.crmecosystem.response.ResponseData;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BalanceService {
    private final BalanceRepository balanceRepository;

    @Transactional
    public ResponseData<?> editBalance(BalanceUpdateIncomeOutcomeDto balanceUpdateIncomeOutcomeDto) {

        Optional<Balance> optionalBalance = balanceRepository.findById(balanceUpdateIncomeOutcomeDto.getBalanceId());
        if (optionalBalance.isEmpty()) {
            throw new NotFoundException("balance not found");
        }
        Balance balance = optionalBalance.get();
        if (balanceUpdateIncomeOutcomeDto.isIncome()) {
            balance.setAmount(balance.getAmount() + balanceUpdateIncomeOutcomeDto.getAmount());
            balance.setCurrency(balanceUpdateIncomeOutcomeDto.getCurrency());
            balanceRepository.save(balance);
        } else {
            balance.setAmount(balance.getAmount() - balanceUpdateIncomeOutcomeDto.getAmount());
            balance.setCurrency(balanceUpdateIncomeOutcomeDto.getCurrency());
            balanceRepository.save(balance);
        }
        return ResponseData.successResponse("succes added amount");
    }
}
