package uz.saidoff.crmecosystem.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.saidoff.crmecosystem.entity.Notification;
import uz.saidoff.crmecosystem.entity.NotificationResponse;
import uz.saidoff.crmecosystem.entity.UsersNotification;
import uz.saidoff.crmecosystem.entity.auth.User;
import uz.saidoff.crmecosystem.mapper.NotificationMapper;
import uz.saidoff.crmecosystem.payload.NotificationDto;
import uz.saidoff.crmecosystem.repository.NotificationRepository;
import uz.saidoff.crmecosystem.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class NotificationService {

  private final NotificationRepository notificationRepository;
  private final NotificationMapper notificationMapper;
  private final UserService userService;
  private final UsersNotificationService usersNotificationService;


  public List<NotificationResponse> getNotification() {

    User currentUser = userService.getCurrentUser();

    List<Notification> notificationsList = notificationRepository.findByUserId(currentUser.getId());

//    //MAKE READ NOTIFICATIONS
//    usersNotificationService.readUserNotifications(notificationsList);

    return notificationsList.stream().map(notificationMapper::toDto).toList();
  }



//  public List<NotificationDto> getNotificationByUserId(UUID userId) {
//    List<Notification> allByUserIdAndReadIsFalse = notificationRepository.findAllByUserIdAndReadIsFalse(userId);
//    return null;
//  }

  public void saveNotification(NotificationDto notificationDto) {
    Notification notification = notificationMapper.toEntity(notificationDto);
    notificationRepository.save(notification);
  }

  public void readNotifications(List<UUID> notifications) {
    List<Notification> notificationsList = notificationRepository.findAllById(notifications);
    usersNotificationService.readUserNotifications(notificationsList);
  }
}