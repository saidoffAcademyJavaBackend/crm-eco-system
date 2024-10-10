package uz.saidoff.crmecosystem.entity;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.saidoff.crmecosystem.entity.template.AbsEntity;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class TaskComment extends AbsEntity {
    private String text;
    private UUID userId;
    private UUID taskId;
    private UUID replayId;
}
