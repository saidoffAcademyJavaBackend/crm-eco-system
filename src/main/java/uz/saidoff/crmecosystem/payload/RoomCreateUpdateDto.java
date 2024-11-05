package uz.saidoff.crmecosystem.payload;

import lombok.Getter;
import lombok.Setter;
import uz.saidoff.crmecosystem.enums.RoomStatus;

@Setter
@Getter
public class RoomCreateUpdateDto {

    private String roomName;
    private Integer roomNumber;
    private String equipment;
    private String comment;
    private RoomStatus roomStatus;
}
