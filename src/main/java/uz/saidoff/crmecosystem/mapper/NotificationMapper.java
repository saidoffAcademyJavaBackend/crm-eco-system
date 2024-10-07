package uz.saidoff.crmecosystem.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import uz.saidoff.crmecosystem.entity.Notification;
import uz.saidoff.crmecosystem.entity.auth.User;
import uz.saidoff.crmecosystem.exception.NotFoundException;
import uz.saidoff.crmecosystem.payload.NotificationDto;
import uz.saidoff.crmecosystem.repository.NotificationRepository;
import uz.saidoff.crmecosystem.repository.UserRepository;

@Component
@RequiredArgsConstructor
public class NotificationMapper {

    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository;

    public NotificationDto toDto(Notification notification) {
        NotificationDto dto = new NotificationDto();
        dto.setId(notification.getId());
        dto.setTitle(notification.getTitle());
        dto.setDescription(notification.getDescription());
        dto.setObject(notification.getObjectId());
        dto.setUserId(notification.getUser().getId());//?
        dto.setIsRead(notification.getRead());
        dto.setType(notification.getType());
        return dto;
    }

    public Notification toEntity(NotificationDto dto) {
        Notification notification = new Notification();
        notification.setId(dto.getId());
        notification.setTitle(dto.getTitle());
        notification.setDescription(dto.getDescription());
        notification.setObjectId(dto.getObject());

        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new NotFoundException("user not found"));
        notification.setUser(user);
        notification.setRead(dto.getIsRead());
        notification.setType(dto.getType());
        return notification;
    }

}
