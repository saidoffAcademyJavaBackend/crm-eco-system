package uz.saidoff.crmecosystem.entity;

import jakarta.persistence.*;
import lombok.*;
import uz.saidoff.crmecosystem.entity.auth.User;
import uz.saidoff.crmecosystem.entity.template.AbsEntity;
import uz.saidoff.crmecosystem.enums.NotificationType;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity(name = "notification")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class Notification extends AbsEntity {

    private String title;

    private String description;

    private UUID objectId;

    @ManyToOne
    private User user;

    private Boolean read = false;


    private Timestamp receivedTime; // do we really need this?!

    @Enumerated(EnumType.STRING)
    private NotificationType type;

}
