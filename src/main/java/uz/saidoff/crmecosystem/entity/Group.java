package uz.saidoff.crmecosystem.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.saidoff.crmecosystem.entity.auth.User;
import uz.saidoff.crmecosystem.entity.template.AbsEntity;
import uz.saidoff.crmecosystem.enums.WeekDays;
import java.sql.Date;
import java.sql.Time;
import java.util.List;

@Entity(name = "groups")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Group extends AbsEntity {

    private String name;

    @ManyToOne
    private User teacher;

    private Time startTime;

    private Time endTime;

    private boolean active = true;

    private String linkForTelegram;

    @ManyToOne
    private GroupType groupType;

    private Date startedDate;

    private Double paymentAmount;

    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    private List<WeekDays> weekDays;

    private boolean student;

    @ManyToMany
    private List<User> students;

    @ManyToOne
    private Room room;
}
