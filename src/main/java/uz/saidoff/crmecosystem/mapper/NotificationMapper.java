package uz.saidoff.crmecosystem.mapper;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import uz.saidoff.crmecosystem.entity.Notification;
import uz.saidoff.crmecosystem.entity.auth.User;
import uz.saidoff.crmecosystem.payload.NotificationDto;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class NotificationMapper {

    private final Notification notification;

    public NotificationDto toNotDto(Notification notification){
        NotificationDto dto = new NotificationDto();
        dto.setId(notification.getId());
        dto.setTitle(notification.getTitle());
        dto.setDescription(notification.getDescription());
        dto.setTimestamp(notification.getReceivedTime());
        dto.setObject(notification.getObjectId());
        dto.setUserId(notification.getUser().getId());//?
        dto.setIsRead(notification.getRead());
        dto.setType(notification.getType());
        return dto;
    }

    public Notification toNotEntity(User user, NotificationDto dto){
        Notification notification = new Notification();
        notification.setId(dto.getId());
        notification.setTitle(dto.getTitle());
        notification.setDescription(dto.getDescription());
        notification.setReceivedTime(dto.getTimestamp());
        notification.setObjectId(dto.getObject());
        notification.setUser(user);
        notification.setRead(dto.getIsRead());
        notification.setType(dto.getType());
        return notification;
    }

}
