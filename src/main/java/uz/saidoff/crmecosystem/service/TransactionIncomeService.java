package uz.saidoff.crmecosystem.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import uz.saidoff.crmecosystem.entity.Category;
import uz.saidoff.crmecosystem.entity.Transaction;
import uz.saidoff.crmecosystem.exception.NotFoundException;
import uz.saidoff.crmecosystem.mapper.TransactionIncomeMapper;
import uz.saidoff.crmecosystem.payload.TransactionIncomeAddDto;
import uz.saidoff.crmecosystem.repository.CategoryRepository;
import uz.saidoff.crmecosystem.repository.TransactionRepository;
import uz.saidoff.crmecosystem.response.ResponseData;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TransactionIncomeService {
    private final TransactionRepository transactionRepository;
    private final TransactionIncomeMapper transactionIncomeMapper;
    private final CategoryRepository categoryRepository;


    public ResponseData<?> addTransactionIncome(TransactionIncomeAddDto transactionIncomeAddDto) {
        Optional<Category> optionalCategory = categoryRepository.findByName(transactionIncomeAddDto.getCategoryName());
        if (optionalCategory.isEmpty()) {
            throw new NotFoundException("Category not found");
        }
        Transaction transaction = transactionIncomeMapper.toIncomeTransaction(transactionIncomeAddDto, optionalCategory.get());
        transactionRepository.save(transaction);
        return ResponseData.successResponse("Transaction added successfully");
    }

    public ResponseData<?> updateTransactionIncome(TransactionIncomeAddDto transactionIncomeAddDto, UUID transactionId) {
        Optional<Transaction> optionalTransaction = transactionRepository.findById(transactionId);
        if (optionalTransaction.isEmpty()) {
            throw new NotFoundException("Transaction not found");
        }
        Optional<Category> optionalCategory = categoryRepository.findByName(transactionIncomeAddDto.getCategoryName());
        if (optionalCategory.isEmpty()) {
            throw new NotFoundException("Category not found");
        }
        Transaction transaction = optionalTransaction.get();
        transaction.setCategory(optionalCategory.get());
        transaction.setAmount(transactionIncomeAddDto.getAmount());
        transaction.setDescription(transactionIncomeAddDto.getDescription());
        transactionRepository.save(transaction);
        return ResponseData.successResponse("Transaction updated successfully");
    }

    public ResponseData<?> getAllIncomeTransactions(int page, int size) {
        Page<Transaction> incomeTransactions = transactionRepository.findAllByIsIncomeTrueOrderByCreatedAtDesc(PageRequest.of(page, size));
        if (incomeTransactions.getTotalElements() == 0) {
            return ResponseData.successResponse("No income transactions found");
        }
        Map<String, Object> result = new HashMap<>();
        result.put("data", incomeTransactions.get());
        result.put("total", incomeTransactions.getTotalElements());
        result.put("TotalPages", incomeTransactions.getTotalPages());
        return ResponseData.successResponse(result);
    }

    public ResponseData<?> getOneIncomeTransaction(UUID transactionId) {
        Optional<Transaction> optionalTransaction = transactionRepository.findById(transactionId);
        if (optionalTransaction.isEmpty()) {
            throw new NotFoundException("Transaction not found");
        }

        return ResponseData.successResponse(optionalTransaction.get());
    }
}
