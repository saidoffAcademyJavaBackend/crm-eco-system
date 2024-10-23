package uz.saidoff.crmecosystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import uz.saidoff.crmecosystem.entity.Balance;

import java.util.Currency;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface BalanceRepository extends JpaRepository<Balance, UUID> {
//    @Query(value = "select b from Balance b " +
//            "where b.currency = :USD " +
//            "and b.deleted = false  ")
   Optional<Balance> findByIdAndCurrency(UUID balanceId,Currency currency);
}
