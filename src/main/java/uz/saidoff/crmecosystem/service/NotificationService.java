package uz.saidoff.crmecosystem.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.saidoff.crmecosystem.entity.Notification;
import uz.saidoff.crmecosystem.exception.NotFoundException;
import uz.saidoff.crmecosystem.mapper.NotificationMapper;
import uz.saidoff.crmecosystem.payload.NotificationDto;
import uz.saidoff.crmecosystem.repository.NotificationRepository;
import uz.saidoff.crmecosystem.repository.UserRepository;
import uz.saidoff.crmecosystem.util.MessageKey;
import uz.saidoff.crmecosystem.util.MessageService;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final NotificationMapper notificationMapper;
    private final UserRepository userRepository;


    public List<NotificationDto> getNotification(UUID userId) {
        List<Notification> notifications = notificationRepository.findAllByUserIdOrderByCreatedAtDesc(userId);
        if (notifications.isEmpty()) {
            throw new NotFoundException(MessageService.getMessage(MessageKey.NOTIFICATION_NOT_FOUND));
        }
        List<NotificationDto> list = notifications
                .stream()
                .map(notificationMapper::toDto)
                .toList();
       return list;
    }

    public void deleteAllReadIsTrueNotifications(UUID userId) {
        notificationRepository.deleteAllByUserIdAndReadIsTrue(userId);
    }
}