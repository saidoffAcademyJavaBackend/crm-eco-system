package uz.saidoff.crmecosystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.saidoff.crmecosystem.entity.Balance;

import java.util.UUID;

public interface BalanceRepository extends JpaRepository<Balance, UUID> {
}
