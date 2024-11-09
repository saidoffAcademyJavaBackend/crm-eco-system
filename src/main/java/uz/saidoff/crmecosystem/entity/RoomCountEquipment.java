package uz.saidoff.crmecosystem.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.*;
import uz.saidoff.crmecosystem.entity.template.AbsEntity;
import uz.saidoff.crmecosystem.payload.RoomEquipmentDto;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "room_count_equipments")
@Builder
public class RoomCountEquipment extends AbsEntity {

    private Integer count;

    @ManyToOne
    private RoomEquipment roomEquipment;

}
