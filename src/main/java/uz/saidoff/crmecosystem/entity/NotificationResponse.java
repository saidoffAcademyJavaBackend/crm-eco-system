package uz.saidoff.crmecosystem.entity;

import jakarta.persistence.Entity;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NotificationResponse {

  private String title;

  private String description;

  private UUID objectId;

}
