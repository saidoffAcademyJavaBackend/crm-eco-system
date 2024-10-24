package uz.saidoff.crmecosystem.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import uz.saidoff.crmecosystem.entity.Attachment;
import uz.saidoff.crmecosystem.entity.Category;
import uz.saidoff.crmecosystem.entity.Transaction;
import uz.saidoff.crmecosystem.exception.NotFoundException;
import uz.saidoff.crmecosystem.mapper.OutcomeMapper;
import uz.saidoff.crmecosystem.payload.OutcomeCreatDto;
import uz.saidoff.crmecosystem.payload.OutcomeUpdateDto;
import uz.saidoff.crmecosystem.repository.AttachmentRepository;
import uz.saidoff.crmecosystem.repository.CategoryRepository;
import uz.saidoff.crmecosystem.repository.TransactionRepository;
import uz.saidoff.crmecosystem.response.ResponseData;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OutcomeService {

    private final OutcomeMapper outcomeMapper;
    private final TransactionRepository transactionRepository;
    private final CategoryRepository categoryRepository;
    private final AttachmentRepository attachmentRepository;

    public ResponseData<?> creat(OutcomeCreatDto outcomeCreatDto) {
        Optional<Category> category = categoryRepository.findByIdAndDeletedFalse(outcomeCreatDto.getCategoryId());
        if (category.isEmpty()) {
            throw new NotFoundException("Category not found");
        }
        Optional<Attachment> attachment = attachmentRepository.findByIdAndDeletedFalse(outcomeCreatDto.getAttachmentId());
        Transaction transaction = outcomeMapper.toEntity(outcomeCreatDto);
        transaction.setCategory(category.get());
        attachment.ifPresent(transaction::setAttachment);
        transactionRepository.save(transaction);
        return ResponseData.successResponse(this.outcomeMapper.toDto(transaction));
    }

    public ResponseData<?> update(UUID outcomeId, OutcomeUpdateDto outcomeDto) {
        Optional<Transaction> transactionOptional = transactionRepository.findByIdAndDeletedFalse(outcomeId);
        if (transactionOptional.isEmpty()) {
            throw new NotFoundException("Outcome not found");
        }
        Transaction transaction = outcomeMapper.toUpdate(transactionOptional.get(), outcomeDto);
        if (outcomeDto.getCategoryId()!=null){
            Optional<Category> category = categoryRepository.findByIdAndDeletedFalse(outcomeDto.getCategoryId());
            if (category.isEmpty()) {
            throw new NotFoundException("Category not found");
            }
            transaction.setCategory(category.get());
        }
        if (outcomeDto.getAttachmentId()!=null){
            Optional<Attachment> attachment = attachmentRepository.findByIdAndDeletedFalse(outcomeDto.getAttachmentId());
            attachment.ifPresent(transaction::setAttachment);
        }
        transactionRepository.save(transaction);

        return ResponseData.successResponse(this.outcomeMapper.toDto(transaction));
    }

    public ResponseData<?> get(UUID outcomeId) {
        Optional<Transaction> transaction = transactionRepository.findByIdAndDeletedFalse(outcomeId);
        if (transaction.isEmpty()) {
            throw new NotFoundException("Outcome not found");
        }
        return ResponseData.successResponse(this.outcomeMapper.toDto(transaction.get()));
    }

    public ResponseData<?> getAllUser(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Transaction> transactions = transactionRepository.findAllByDeletedFalse(pageable);
        if (transactions.isEmpty()) {
            throw new NotFoundException("No transactions found");
        }

        Map<String, Object> response = new HashMap<>();
        response.put("data", outcomeMapper.toDto(transactions.toList()));
        response.put("total", transactions.getTotalElements());
        response.put("totalPages", transactions.getTotalPages());

        return ResponseData.successResponse(response);
    }

    public ResponseData<?> delete(UUID outcomeId) {
        Optional<Transaction> transaction = transactionRepository.findByIdAndDeletedFalse(outcomeId);
        if (transaction.isEmpty()) {
            throw new NotFoundException("Outcome not found");
        }
        Transaction transactionToDelete = transaction.get();
        transactionToDelete.setDeleted(true);
        transactionRepository.save(transactionToDelete);
        return ResponseData.successResponse("success");
    }
}
