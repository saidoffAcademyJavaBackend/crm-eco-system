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
public class Room extends AbsEntity {

    @Column(unique = true)
    private String roomName;

    private int capacity;

    private String comment;

    @Enumerated(EnumType.STRING)
    private RoomType roomType;

    @OneToMany
    public List<RoomCountEquipment> roomCountEquipments;

    @ManyToOne
    @JoinColumn(name = "group_id")
    private Group group;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "responsible_person_id")
    private User responsiblePerson;

}
