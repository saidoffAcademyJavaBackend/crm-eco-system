package uz.saidoff.crmecosystem.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.saidoff.crmecosystem.entity.auth.User;
import uz.saidoff.crmecosystem.entity.template.AbsEntity;
import uz.saidoff.crmecosystem.enums.GroupStage;
import uz.saidoff.crmecosystem.enums.WeekDays;
import java.sql.Timestamp;
import java.util.List;

@Entity(name = "groups")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Group extends AbsEntity {

    private String name;

    private Integer countOfStudents;

    @ManyToOne
    private User teacher;

    @ManyToMany
    private List<User> students;

    private Timestamp startTime;

    private Timestamp endTime;

    @Enumerated(EnumType.STRING)
    private GroupStage groupStage;

    private boolean active = true;

    private String linkForTelegram;

    @ManyToOne
    private GroupType groupType;

    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    private List<WeekDays> weekDays;
}
