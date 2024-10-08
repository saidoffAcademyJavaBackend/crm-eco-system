package uz.saidoff.crmecosystem.entity;

import jakarta.persistence.Entity;
import lombok.*;
import uz.saidoff.crmecosystem.entity.auth.User;
import uz.saidoff.crmecosystem.entity.template.AbsEntity;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class TaskUser extends AbsEntity {

    private User user;


    private Task task;
}
