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
        room.setCapacity(roomDto.getCapacity());
        room.setEquipments(roomDto.getEquipments());
        room.setComment(roomDto.getComment());
        room.setRoomType(roomDto.getRoomType());
        room.setRoomStatus(roomDto.getRoomStatus());
        return room;
    }

    public RoomDto toRoomDto(Room room) {
        RoomDto roomDto = new RoomDto();
        roomDto.setId(room.getId());
        roomDto.setRoomName(room.getRoomName());
        roomDto.setCapacity(room.getCapacity());
        roomDto.setEquipments(room.getEquipments());
        roomDto.setComment(room.getComment());
        roomDto.setRoomType(room.getRoomType());
        roomDto.setRoomStatus(room.getRoomStatus());
        return roomDto;
    }

    public Room toRoomUpdateEntity(Room room, RoomCreateUpdateDto roomDto) {
        room.setRoomName(roomDto.getRoomName());
        room.setCapacity(roomDto.getCapacity());
        room.setEquipments(roomDto.getEquipments());
        room.setComment(roomDto.getComment());
        room.setRoomType(roomDto.getRoomType());
        room.setRoomStatus(roomDto.getRoomStatus());
        return room;
    }
}
