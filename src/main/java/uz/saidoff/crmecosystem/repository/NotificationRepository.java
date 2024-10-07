package uz.saidoff.crmecosystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.saidoff.crmecosystem.entity.Notification;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


public interface NotificationRepository extends JpaRepository<Notification, UUID> {

    List<Notification> findAllByUserIdAndReadIsFalse(UUID userId);

    List<Notification> findAllByUserIdOrderByCreatedAtDesc(UUID user_id);

    void deleteAllByUserIdAndReadIsTrue(UUID userId);

    Optional<Notification> findUserByUserId(UUID userId);

    
}
