package uz.saidoff.crmecosystem.payload;

import lombok.Getter;
import lombok.Setter;
import uz.saidoff.crmecosystem.entity.RoomCountEquipment;
import uz.saidoff.crmecosystem.entity.RoomEquipment;
import uz.saidoff.crmecosystem.enums.RoomStatus;
import uz.saidoff.crmecosystem.enums.RoomType;

import java.util.List;
import java.util.UUID;

@Setter
@Getter
public class RoomCreateUpdateDto {

    private String roomName;
    private int capacity;
    private String comment;
    private RoomType roomType;
    private List<RoomEquipmentDto> roomEquipmentDtoList;
}
