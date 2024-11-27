package uz.saidoff.crmecosystem.payload;

import lombok.Getter;
import lombok.Setter;
import uz.saidoff.crmecosystem.enums.RoomType;

import java.util.List;
import java.util.UUID;

@Setter
@Getter
public class RoomResponseDto {


    private UUID id;
    private String roomName;
    private int capacity;
    private String comment;
    private RoomType roomType;
    private List<RoomEquipCountDto> roomEquipCountDtoList;
}
