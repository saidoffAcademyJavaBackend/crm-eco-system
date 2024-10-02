package uz.saidoff.crmecosystem.payload;

import lombok.Data;
import uz.saidoff.crmecosystem.enums.NotificationType;

import java.sql.Timestamp;
import java.util.UUID;
@Data
public class NotificationDto {

    private UUID id;
    private String title;
    private String description;
    private Timestamp timestamp;
    private UUID object;
    private UUID userId;
    private Boolean isRead;
    private NotificationType type;

}
