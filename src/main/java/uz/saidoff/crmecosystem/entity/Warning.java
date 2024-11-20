package uz.saidoff.crmecosystem.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.*;
import uz.saidoff.crmecosystem.entity.auth.User;
import uz.saidoff.crmecosystem.entity.template.AbsEntity;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "warnings")
@Builder
public class Warning extends AbsEntity {

    private String reason;
    private int warningCount;
    private boolean punishment;
    @ManyToOne
    private User user;

}
