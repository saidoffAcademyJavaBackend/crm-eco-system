package uz.saidoff.crmecosystem.mapper;

import org.springframework.stereotype.Component;
import uz.saidoff.crmecosystem.entity.Group;
import uz.saidoff.crmecosystem.entity.Room;
import uz.saidoff.crmecosystem.entity.RoomEquipment;
import uz.saidoff.crmecosystem.entity.auth.User;
import uz.saidoff.crmecosystem.exception.NotFoundException;
import uz.saidoff.crmecosystem.payload.RoomCreateUpdateDto;
import uz.saidoff.crmecosystem.payload.RoomDto;
import uz.saidoff.crmecosystem.repository.GroupRepository;
import uz.saidoff.crmecosystem.repository.RoomEquipmentRepository;
import uz.saidoff.crmecosystem.repository.UserRepository;

import java.util.List;

@Component
public class RoomMapper {

    private final RoomEquipmentRepository roomEquipmentRepository;
    private final UserRepository userRepository;
    private final GroupRepository groupRepository;
    private final RoomEquipmentMapper roomEquipmentMapper;

    public RoomMapper(RoomEquipmentRepository roomEquipmentRepository, UserRepository userRepository, GroupRepository groupRepository, RoomEquipmentMapper roomEquipmentMapper) {
        this.roomEquipmentRepository = roomEquipmentRepository;
        this.userRepository = userRepository;
        this.groupRepository = groupRepository;
        this.roomEquipmentMapper = roomEquipmentMapper;
    }

    public Room toRoomEntity(RoomDto roomDto) {
        User user = userRepository.findByIdAndDeletedFalse(roomDto.getResponsiblePersonId()).orElseThrow(
                () -> new NotFoundException("User not found"));
        Group group = groupRepository.findByIdAndDeletedIsFalse(roomDto.getGroupId()).orElseThrow(
                () -> new NotFoundException("Group not found"));
        RoomEquipment equipment = roomEquipmentRepository.findByIdAndDeletedFalse(roomDto.getEquipmentId())
                .orElseThrow(() -> new NotFoundException("Equipment not found"));
        Room room = new Room();
        room.setRoomName(roomDto.getRoomName());
        room.setCapacity(roomDto.getCapacity());
        room.setComment(roomDto.getComment());
        room.setRoomType(roomDto.getRoomType());
        room.setRoomStatus(roomDto.getRoomStatus());
        room.setEquipments(List.of(equipment));
        room.setGroup(group);
        room.setResponsiblePerson(user);
        return room;
    }

    public Room toRoomEntity(RoomCreateUpdateDto roomDto) {
        Room room = new Room();
        room.setRoomName(roomDto.getRoomName());
        room.setCapacity(roomDto.getCapacity());
        room.setComment(roomDto.getComment());
        room.setRoomType(roomDto.getRoomType());
        room.setRoomStatus(roomDto.getRoomStatus());
        room.setEquipments(roomEquipmentMapper.toEquipmentEntityResponse(roomDto.getEquipmentsList()));
        return room;
    }

    public RoomCreateUpdateDto toRoomDto(Room room) {
        RoomCreateUpdateDto roomDto = new RoomCreateUpdateDto();
        roomDto.setId(room.getId());
        roomDto.setRoomName(room.getRoomName());
        roomDto.setCapacity(room.getCapacity());
        roomDto.setComment(room.getComment());
        roomDto.setRoomType(room.getRoomType());
        roomDto.setRoomStatus(room.getRoomStatus());
        roomDto.setEquipmentsList(roomEquipmentMapper.toRoomEquipDto(room.getEquipments()));
        return roomDto;
    }
    public RoomDto toRoomDtoAssigning(Room room) {
        RoomDto roomDto = new RoomDto();
        roomDto.setId(room.getId());
        roomDto.setRoomName(room.getRoomName());
        roomDto.setCapacity(room.getCapacity());
        roomDto.setComment(room.getComment());
        roomDto.setRoomType(room.getRoomType());
        roomDto.setRoomStatus(room.getRoomStatus());
        roomDto.setGroupId(room.getGroup().getId());
        roomDto.setResponsiblePersonId(room.getResponsiblePerson().getId());
        return roomDto;
    }

    public Room toRoomUpdateEntity(Room room, RoomCreateUpdateDto roomDto) {

//        List<RoomEquipment> equipments = roomEquipmentRepository.findAllByIdAndDeletedFalse(roomDto.getEquipmentId());
//        if (equipments.isEmpty()) {
//            throw new NotFoundException("Equipment not found");
//        }
        if (roomDto.getRoomName() != null) {
            room.setRoomName(roomDto.getRoomName());
        }
        if (roomDto.getCapacity() != 0) {
            room.setCapacity(roomDto.getCapacity());
        }
        if (roomDto.getComment() != null) {
            room.setComment(roomDto.getComment());
        }
        if (roomDto.getRoomType() != null) {
            room.setRoomType(roomDto.getRoomType());
        }
        if (roomDto.getRoomStatus() != null) {
            room.setRoomStatus(roomDto.getRoomStatus());
        }
        if (roomDto.getEquipmentsList() != null) {
            room.setEquipments(roomEquipmentMapper.toEquipmentEntityResponse(roomDto.getEquipmentsList()));
        }
        return room;
    }
}
