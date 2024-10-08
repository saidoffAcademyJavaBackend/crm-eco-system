package uz.saidoff.crmecosystem.entity;

import jakarta.persistence.Entity;
import lombok.*;
import uz.saidoff.crmecosystem.entity.auth.User;
import uz.saidoff.crmecosystem.entity.template.AbsEntity;

import java.util.Date;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Project extends AbsEntity {

    private String name;
    private Date startDate;
    private Date endDate;
    private User owner;
}
