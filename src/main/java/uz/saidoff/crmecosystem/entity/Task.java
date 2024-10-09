package uz.saidoff.crmecosystem.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.*;
import uz.saidoff.crmecosystem.entity.template.AbsEntity;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Task extends AbsEntity {

    private String title;
    private String description;
    private Date deadline;

    @ManyToOne
    private Stage stage;

    private int order;
}
