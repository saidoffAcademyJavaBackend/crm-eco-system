package uz.saidoff.crmecosystem.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import uz.saidoff.crmecosystem.entity.Attachment;
import uz.saidoff.crmecosystem.entity.Category;
import uz.saidoff.crmecosystem.entity.Transaction;
import uz.saidoff.crmecosystem.entity.auth.User;
import uz.saidoff.crmecosystem.exception.NotFoundException;
import uz.saidoff.crmecosystem.payload.TransactionIncomeAddDto;
import uz.saidoff.crmecosystem.repository.AttachmentRepository;
import uz.saidoff.crmecosystem.repository.UserRepository;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class TransactionIncomeMapper {
    private final UserRepository userRepository;
    public Transaction toIncomeTransaction(TransactionIncomeAddDto transactionIncomeAddDto, Category category, Attachment attachment) {
        Transaction transaction = new Transaction();
        transaction.setCategory(category);
        transaction.setAmount(transactionIncomeAddDto.getAmount());
        transaction.setDescription(transactionIncomeAddDto.getDescription());
        transaction.setIsIncome(true);
        transaction.setCurrency(transactionIncomeAddDto.getCurrency());
        if (transactionIncomeAddDto.getTransactorId()!=null){
            Optional<User> optionalUser = userRepository.findById(transactionIncomeAddDto.getTransactorId());
            if (optionalUser.isEmpty()){
                throw new NotFoundException("Transactor not found");
            }
            User user = optionalUser.get();
            transaction.setTransactor(user);
        }
        if (attachment!=null){
            transaction.setAttachment(attachment);
        }
        return transaction;
    }
}
