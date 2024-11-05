package uz.saidoff.crmecosystem.mapper;

import org.springframework.stereotype.Component;
import uz.saidoff.crmecosystem.entity.Room;
import uz.saidoff.crmecosystem.payload.RoomCreateUpdateDto;
import uz.saidoff.crmecosystem.payload.RoomDto;

@Component
public class RoomMapper {

    public Room toRoomEntity(RoomCreateUpdateDto roomDto) {
        Room room = new Room();
        room.setRoomName(roomDto.getRoomName());
        room.setRoomNumber(roomDto.getRoomNumber());
        room.setEquipment(roomDto.getEquipment());
        room.setComment(roomDto.getComment());
        room.setRoomStatus(roomDto.getRoomStatus());
        return room;
    }

    public RoomDto toRoomDto(Room room) {
        RoomDto roomDto = new RoomDto();
        roomDto.setId(room.getId());
        roomDto.setRoomName(room.getRoomName());
        roomDto.setRoomNumber(room.getRoomNumber());
        roomDto.setEquipment(room.getEquipment());
        roomDto.setComment(room.getComment());
        roomDto.setRoomStatus(room.getRoomStatus());
        return roomDto;
    }
    public Room toRoomUpdateEntity(Room room, RoomCreateUpdateDto roomDto){
        room.setRoomName(roomDto.getRoomName());
        room.setRoomNumber(roomDto.getRoomNumber());
        room.setEquipment(roomDto.getEquipment());
        room.setComment(roomDto.getComment());
        room.setRoomStatus(roomDto.getRoomStatus());
        return room;
    }
}
