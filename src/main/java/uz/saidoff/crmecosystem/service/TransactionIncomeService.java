package uz.saidoff.crmecosystem.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import uz.saidoff.crmecosystem.entity.*;
import uz.saidoff.crmecosystem.entity.auth.User;
import uz.saidoff.crmecosystem.exception.NotFoundException;
import uz.saidoff.crmecosystem.mapper.TransactionIncomeMapper;
import uz.saidoff.crmecosystem.payload.BalanceUpdateIncomeOutcomeDto;
import uz.saidoff.crmecosystem.payload.TransactionIncomeAddDto;
import uz.saidoff.crmecosystem.repository.*;
import uz.saidoff.crmecosystem.response.ResponseData;

import java.util.*;

@Service
@RequiredArgsConstructor
public class TransactionIncomeService {
    private final TransactionRepository transactionRepository;
    private final TransactionIncomeMapper transactionIncomeMapper;
    private final CategoryRepository categoryRepository;
    private final BalanceService balanceService;
    private final UserRepository userRepository;
    private final AttachmentRepository attachmentRepository;
    private final GroupRepository groupRepository;


    public ResponseData<?> addTransactionIncome(TransactionIncomeAddDto transactionIncomeAddDto) {
        Optional<Category> optionalCategory = categoryRepository.findById(transactionIncomeAddDto.getCategoryId());
        if (optionalCategory.isEmpty()) {
            throw new NotFoundException("Category not found");
        }
        Optional<Group> optionalGroup = Optional.empty();
        if (transactionIncomeAddDto.getGroupId() != null) {
            optionalGroup = groupRepository.findById(transactionIncomeAddDto.getGroupId());
            if (optionalGroup.isEmpty()) {
                throw new NotFoundException("Group not found");
            }
        }
        Attachment attachment = new Attachment();
        if (transactionIncomeAddDto.getAttachmentId() != null) {
            Optional<Attachment> optionalAttachment = attachmentRepository.findById(transactionIncomeAddDto.getAttachmentId());
            if (optionalAttachment.isEmpty()) {
                throw new NotFoundException("Attachment not found");
            }
            attachment = optionalAttachment.get();
        }
        if (transactionIncomeAddDto.getTransactorId() != null) {
            Optional<User> optionalUser = userRepository.findByIdAndDeletedFalse(transactionIncomeAddDto.getTransactorId());
            if (optionalUser.isEmpty()) {
                throw new NotFoundException("User not found");
            }
            User user = optionalUser.get();
            if (user.getRole().getRoleType().name().equals("STUDENT") || user.getRole().getRoleType().name().equals("INTERN")) {
                if (user.getSalary() != null && transactionIncomeAddDto.getAmount() > user.getSalary()) {
                    user.setBalance(transactionIncomeAddDto.getAmount() - user.getSalary());
                    transactionIncomeAddDto.setAmount(user.getSalary());
                } else if (optionalGroup.isPresent() && transactionIncomeAddDto.getAmount() > optionalGroup.get().getPaymentAmount()) {
                    user.setBalance(transactionIncomeAddDto.getAmount() - optionalGroup.get().getPaymentAmount());
                    transactionIncomeAddDto.setAmount(optionalGroup.get().getPaymentAmount());
                }
            }
        }

        Transaction transaction = transactionIncomeMapper.toIncomeTransaction(transactionIncomeAddDto, optionalCategory.get(), attachment, optionalGroup);
        transactionRepository.save(transaction);
        BalanceUpdateIncomeOutcomeDto balanceUpdateIncomeOutcomeDto = new BalanceUpdateIncomeOutcomeDto();
        balanceUpdateIncomeOutcomeDto.setIncome(true);
        balanceUpdateIncomeOutcomeDto.setAmount(transactionIncomeAddDto.getAmount());
        balanceUpdateIncomeOutcomeDto.setCurrency(transactionIncomeAddDto.getCurrency());
        balanceService.editBalance(balanceUpdateIncomeOutcomeDto);
        return ResponseData.successResponse("Transaction added successfully");
    }

    public ResponseData<?> updateTransactionIncome(TransactionIncomeAddDto transactionIncomeAddDto, UUID transactionId) {
        Optional<Transaction> optionalTransaction = transactionRepository.findById(transactionId);
        if (optionalTransaction.isEmpty()) {
            throw new NotFoundException("Transaction not found");
        }
        Optional<Category> optionalCategory = categoryRepository.findById(transactionIncomeAddDto.getCategoryId());
        if (optionalCategory.isEmpty()) {
            throw new NotFoundException("Category not found");
        }
        Transaction transaction = optionalTransaction.get();
        balanceService.editBalance(new BalanceUpdateIncomeOutcomeDto(transactionIncomeAddDto.getAmount(), true, transactionIncomeAddDto.getCurrency()));
        balanceService.editBalance(new BalanceUpdateIncomeOutcomeDto(transaction.getAmount(), false, transaction.getCurrency()));
        Optional<User> optionalUser = userRepository.findById(transactionIncomeAddDto.getTransactorId());
        if (optionalUser.isEmpty()) {
            throw new NotFoundException("Transactor not found");
        }
        Attachment attachment = new Attachment();
        if (transactionIncomeAddDto.getAttachmentId() != null) {
            Optional<Attachment> optionalAttachment = attachmentRepository.findById(transactionIncomeAddDto.getAttachmentId());
            if (optionalAttachment.isEmpty()) {
                throw new NotFoundException("Attachment not found");
            }
            attachment = optionalAttachment.get();
        }
        transaction.setAttachment(attachment);
        transaction.getUserPayments().setTransactor(optionalUser.get());
        transaction.setCategory(optionalCategory.get());
        transaction.setCurrency(transactionIncomeAddDto.getCurrency());
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
