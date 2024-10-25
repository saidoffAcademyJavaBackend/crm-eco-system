package uz.saidoff.crmecosystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import uz.saidoff.crmecosystem.entity.Balance;
import uz.saidoff.crmecosystem.enums.Currency;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface BalanceRepository extends JpaRepository<Balance, UUID> {
   Optional<Balance> findByCurrency(Currency currency);
}
