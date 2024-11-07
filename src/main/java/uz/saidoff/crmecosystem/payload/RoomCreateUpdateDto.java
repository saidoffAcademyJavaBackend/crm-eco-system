package uz.saidoff.crmecosystem.payload;

import lombok.Getter;
import lombok.Setter;
import uz.saidoff.crmecosystem.enums.Equipment;
import uz.saidoff.crmecosystem.enums.RoomStatus;
import uz.saidoff.crmecosystem.enums.RoomType;

import java.util.List;
import java.util.UUID;

@Setter
@Getter
public class RoomCreateUpdateDto {

    private UUID id;
    private String roomName;
    private int capacity;
    private String comment;
    private RoomType roomType;
    private RoomStatus roomStatus;
    private List<RoomCreateUpdateEquipDto> equipmentsList;
}
