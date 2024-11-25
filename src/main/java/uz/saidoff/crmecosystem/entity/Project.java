package uz.saidoff.crmecosystem.entity;

import jakarta.persistence.*;
import lombok.*;
import uz.saidoff.crmecosystem.entity.template.AbsEntity;
import uz.saidoff.crmecosystem.enums.Currency;

import java.util.Date;
import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Project extends AbsEntity {

    private String name;
    private Date startDate;
    private Date endDate;
    private Double price;
    @Enumerated(EnumType.STRING)
    private Currency currency;

    @OneToMany
    private List<ProjectUser> projectUsers;

}
