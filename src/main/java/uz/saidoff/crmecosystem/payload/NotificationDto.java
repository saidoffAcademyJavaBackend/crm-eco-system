package uz.saidoff.crmecosystem.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.saidoff.crmecosystem.enums.NotificationType;

import java.time.LocalDateTime;
import java.util.UUID;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotificationDto {

    private UUID id;
    private String title;
    private String description;
    private UUID object;
    private UUID userId;
    private Boolean isRead;
    private NotificationType type;

}
