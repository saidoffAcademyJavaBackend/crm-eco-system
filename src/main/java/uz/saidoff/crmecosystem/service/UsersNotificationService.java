package uz.saidoff.crmecosystem.service;

import org.springframework.stereotype.Service;
import uz.saidoff.crmecosystem.entity.Notification;
import uz.saidoff.crmecosystem.entity.UsersNotification;
import uz.saidoff.crmecosystem.entity.auth.User;
import uz.saidoff.crmecosystem.repository.UsersNotificationRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class UsersNotificationService {


  private final UsersNotificationRepository usersNotificationRepository;
  private final UserService userService;

  public UsersNotificationService(UsersNotificationRepository usersNotificationRepository, UserService userService) {
    this.usersNotificationRepository = usersNotificationRepository;
    this.userService = userService;
  }

  public List<UsersNotification> saveUsersNotification(List<User> users) {

    var usersNotifications = new ArrayList<UsersNotification>();
    UsersNotification usersNotification = new UsersNotification();
    for (User user : users) {
      usersNotification.setUser(user);
      usersNotificationRepository.save(usersNotification);
      usersNotifications.add(usersNotification);
    }
    return usersNotifications;
  }


  public void readUserNotifications(List<Notification> notificationsList) {
    notificationsList.forEach(notification -> {
      notification.getUsersNotifications()
        .stream().filter(
          a -> a.getUser().getId().equals(userService.getCurrentUser().getId()))
        .map(a ->{ a.setRead(true); return a;}).forEach(usersNotificationRepository::save);
    });
  }
}
