package uz.saidoff.crmecosystem.service;

import org.springframework.stereotype.Service;
import uz.saidoff.crmecosystem.entity.UsersNotification;
import uz.saidoff.crmecosystem.entity.auth.User;
import uz.saidoff.crmecosystem.repository.UsersNotificationRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class UsersNotificationService {


  private final UsersNotificationRepository usersNotificationRepository;

  public UsersNotificationService(UsersNotificationRepository usersNotificationRepository) {
    this.usersNotificationRepository = usersNotificationRepository;
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


}
