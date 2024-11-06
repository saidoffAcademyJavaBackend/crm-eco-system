package uz.saidoff.crmecosystem.payload;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import uz.saidoff.crmecosystem.entity.RoomAssignment;
import uz.saidoff.crmecosystem.enums.Equipment;
import uz.saidoff.crmecosystem.enums.RoomStatus;
import uz.saidoff.crmecosystem.enums.RoomType;

import java.util.List;
import java.util.UUID;

@Setter
@Getter
public class RoomDto {

    private UUID id;
    private String roomName;
    private int capacity;
    private List<Equipment> equipments;
    private String comment;
    private RoomType roomType;
    private RoomStatus roomStatus;
}
