package uz.saidoff.crmecosystem.entity;

import jakarta.persistence.*;
import lombok.*;
import uz.saidoff.crmecosystem.entity.auth.User;
import uz.saidoff.crmecosystem.entity.template.AbsEntity;
import uz.saidoff.crmecosystem.enums.Equipment;
import uz.saidoff.crmecosystem.enums.RoomStatus;
import uz.saidoff.crmecosystem.enums.RoomType;
import uz.saidoff.crmecosystem.enums.WeekDays;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity(name = "rooms")
public class Room extends AbsEntity { //todo 1. equipment new table, 2. remove three fields  (status, weekdays, )

    @Column(unique = true)
    private String roomName;

    private int capacity;

    private String comment;

    @Enumerated(EnumType.STRING)
    private RoomType roomType;

    @Enumerated(EnumType.STRING)
    private RoomStatus roomStatus;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<RoomEquipment> equipments;

    @ManyToOne
    @JoinColumn(name = "group_id")
    private Group group;

    @ManyToOne(fetch = FetchType.LAZY)
    private User responsiblePerson;

}
