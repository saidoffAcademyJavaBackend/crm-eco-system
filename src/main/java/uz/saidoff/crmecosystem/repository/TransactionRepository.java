package uz.saidoff.crmecosystem.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import uz.saidoff.crmecosystem.entity.Transaction;
import uz.saidoff.crmecosystem.payload.TransactionSummaryDto;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TransactionRepository extends JpaRepository<Transaction, UUID> {

    Page<Transaction> findAllByIsIncomeTrueOrderByCreatedAtDesc(Pageable pageable);

    Optional<Transaction> findByIdAndDeletedFalse(UUID transactionId);

    Page<Transaction> findAllByDeletedFalse(Pageable pageable);


    @Query(nativeQuery = true, value = "SELECT  cast(created_at as DATE) AS transactionDate, currency, SUM(amount) AS totalAmount, COUNT(*) AS totalTransactions " +
            "FROM transaction " +
            "WHERE created_at >= ?1 AND created_at <= ?2 AND currency = ?3 AND is_income = ?4 " +
            "GROUP BY cast(created_at as DATE), currency " +
            "ORDER BY transactionDate")
    List<Object[]> getTransactionSummaryByDateAndCurrency(
            Date startDate,
            Date endDate,
            String currency,
            Boolean isIncome
    );


    @Query(nativeQuery = true, value = "SELECT TO_CHAR(created_at, 'YYYY-MM') AS transaction_month, " +
            "currency, SUM(amount) AS total_amount, COUNT(*) AS total_transactions " +
            "FROM transaction " +
            "WHERE created_at >= ?1 AND created_at <= ?2 AND currency = ?3 AND is_income = ?4 " +
            "GROUP BY TO_CHAR(created_at, 'YYYY-MM'), currency " +
            "ORDER BY transaction_month")
    List<Object[]> getTransactionSummaryByMonthAndCurrency(
            Date startDate,
            Date endDate,
            String currency,
            Boolean isIncome
    );

    @Query(nativeQuery = true, value = "SELECT TO_CHAR(created_at, 'YYYY') AS transaction_year, " +
            "currency, SUM(amount) AS total_amount, COUNT(*) AS total_transactions " +
            "FROM transaction " +
            "WHERE created_at >= ?1 AND created_at <= ?2 AND currency = ?3 AND is_income = ?4 " +
            "GROUP BY TO_CHAR(created_at, 'YYYY'), currency " +
            "ORDER BY transaction_year")
    List<Object[]> getTransactionSummaryByYearAndCurrency(
            Date startDate,
            Date endDate,
            String currency,
            Boolean isIncome
    );
}
