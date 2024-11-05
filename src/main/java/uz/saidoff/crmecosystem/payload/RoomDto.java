package uz.saidoff.crmecosystem.payload;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import uz.saidoff.crmecosystem.entity.RoomAssignment;
import uz.saidoff.crmecosystem.enums.RoomStatus;

import java.util.List;
import java.util.UUID;

@Setter
@Getter
public class RoomDto {

    private UUID id;
    private String roomName;
    private Integer roomNumber;
    private String equipment;
    private String comment;
    private RoomStatus roomStatus;
//    private List<RoomAssignment> roomAssignments;
}
