package uz.saidoff.crmecosystem.entity;

import jakarta.persistence.*;
import lombok.*;
import uz.saidoff.crmecosystem.entity.template.AbsEntity;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "equipments")
@Builder
public class RoomEquipment extends AbsEntity {

    private String name;

    private int count;

    private int deletedCount;

    @ManyToOne(fetch = FetchType.LAZY)
    private Room room;


}
