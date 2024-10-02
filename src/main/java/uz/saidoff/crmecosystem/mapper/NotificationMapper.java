package uz.saidoff.crmecosystem.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import uz.saidoff.crmecosystem.entity.Notification;
import uz.saidoff.crmecosystem.entity.auth.User;
import uz.saidoff.crmecosystem.payload.NotificationDto;
@Component
@RequiredArgsConstructor
public class NotificationMapper {

    public NotificationDto toDto(Notification notification){
        NotificationDto dto = new NotificationDto();
        dto.setId(notification.getId());
        dto.setTitle(notification.getTitle());
        dto.setDescription(notification.getDescription());
        dto.setReceivedTime(notification.getReceivedTime());
        dto.setObject(notification.getObjectId());
        dto.setUserId(notification.getUser().getId());//?
        dto.setIsRead(notification.getRead());
        dto.setType(notification.getType());
        return dto;
    }

    public Notification toEntity(User user, NotificationDto dto){
        Notification notification = new Notification();
        notification.setId(dto.getId());
        notification.setTitle(dto.getTitle());
        notification.setDescription(dto.getDescription());
        notification.setReceivedTime(dto.getReceivedTime());
        notification.setObjectId(dto.getObject());
        notification.setUser(user);
        notification.setRead(dto.getIsRead());
        notification.setType(dto.getType());
        return notification;
    }

}
