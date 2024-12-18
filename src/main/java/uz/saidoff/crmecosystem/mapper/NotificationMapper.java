package uz.saidoff.crmecosystem.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import uz.saidoff.crmecosystem.entity.Notification;
import uz.saidoff.crmecosystem.entity.NotificationResponse;
import uz.saidoff.crmecosystem.entity.UsersNotification;
import uz.saidoff.crmecosystem.entity.auth.User;
import uz.saidoff.crmecosystem.exception.NotFoundException;
import uz.saidoff.crmecosystem.payload.NotificationDto;
import uz.saidoff.crmecosystem.repository.NotificationRepository;
import uz.saidoff.crmecosystem.repository.UserRepository;
import uz.saidoff.crmecosystem.service.UserService;
import uz.saidoff.crmecosystem.service.UsersNotificationService;

import java.util.List;

@Component
@RequiredArgsConstructor
public class NotificationMapper {

    private final UserRepository userRepository;
    private final UserService userService;
    private final UsersNotificationService usersNotificationService;

    public NotificationResponse toDto(Notification notification) {
        NotificationResponse dto = new NotificationResponse();
        dto.setTitle(notification.getTitle());
        dto.setType(notification.getType());
        dto.setDescription(notification.getDescription());
        dto.setObjectId(notification.getObjectId());
        return dto;
    }

    public Notification toEntity(NotificationDto dto) {
        Notification notification = new Notification();
        notification.setTitle(dto.getTitle());
        notification.setDescription(dto.getDescription());
        notification.setObjectId(dto.getObject());
        notification.setType(dto.getType());

        // SET USERS TO NOTIFICATION
        List<User> usersByRolesId = userService.getUsersByRolesId(dto.getRolesId());
        List<UsersNotification> usersNotifications = usersNotificationService.saveUsersNotification(usersByRolesId);
        notification.setUsersNotifications(usersNotifications);


        notification.setType(dto.getType());
        return notification;
    }

}
