package uz.saidoff.crmecosystem.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.*;
import uz.saidoff.crmecosystem.entity.template.AbsEntity;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Stage extends AbsEntity {
    private String name;
    private int order;
    private boolean is_done;

    @ManyToOne
    private Project project;
}
