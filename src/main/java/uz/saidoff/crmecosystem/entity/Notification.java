package uz.saidoff.crmecosystem.entity;

import jakarta.persistence.*;
import lombok.*;
import uz.saidoff.crmecosystem.entity.auth.User;
import uz.saidoff.crmecosystem.entity.template.AbsEntity;
import uz.saidoff.crmecosystem.enums.NotificationType;

import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Entity(name = "notification")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Notification extends AbsEntity {

    private String title;

    private String description;

    private UUID objectId;

    @ManyToOne
    private User user;

    private Boolean read = false;

    @Enumerated(EnumType.STRING)
    private NotificationType type;

}
