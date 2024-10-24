package uz.saidoff.crmecosystem.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import uz.saidoff.crmecosystem.entity.Transaction;

import java.util.Optional;
import java.util.UUID;

public interface TransactionRepository extends JpaRepository<Transaction, UUID> {
    Page<Transaction> findAllByIsIncomeTrueOrderByCreatedAtDesc(Pageable pageable);
    Optional<Transaction> findByIdAndDeletedFalse(UUID transactionId);
    Page<Transaction> findAllByDeletedFalse(Pageable pageable);
}
