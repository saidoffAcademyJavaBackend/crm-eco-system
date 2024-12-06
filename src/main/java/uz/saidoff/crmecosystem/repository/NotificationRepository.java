package uz.saidoff.crmecosystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import uz.saidoff.crmecosystem.entity.Notification;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


public interface NotificationRepository extends JpaRepository<Notification, UUID> {


    @Query("SELECT n FROM notification n JOIN n.usersNotifications un WHERE un.user.id = :userId and un.read = false ")
    List<Notification> findByUserId(@Param("userId") UUID userId);

}
