package uz.saidoff.crmecosystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.saidoff.crmecosystem.entity.PaymentForMonth;

import java.util.UUID;

public interface PaymentForServiceRepository extends JpaRepository<PaymentForMonth, UUID> {

}
