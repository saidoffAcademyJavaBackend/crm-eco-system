package uz.saidoff.crmecosystem.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import uz.saidoff.crmecosystem.entity.Group;
import uz.saidoff.crmecosystem.entity.Room;
import uz.saidoff.crmecosystem.entity.RoomCountEquipment;
import uz.saidoff.crmecosystem.entity.RoomEquipment;
import uz.saidoff.crmecosystem.entity.auth.User;
import uz.saidoff.crmecosystem.exception.NotFoundException;
import uz.saidoff.crmecosystem.payload.*;
import uz.saidoff.crmecosystem.repository.*;
import uz.saidoff.crmecosystem.service.EquipmentService;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class RoomMapper {

    private final RoomEquipmentRepository roomEquipmentRepository;
    private final UserRepository userRepository;
    private final GroupRepository groupRepository;
    private final RoomEquipmentMapper roomEquipmentMapper;
    private final RoomRepository roomRepository;
    private final RoomCountEquipmentRepository roomCountEquipmentRepository;
    private final EquipmentService equipmentService;


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
        room.setGroup(group);
        room.setResponsiblePerson(user);
        return room;
    }

    public List<RoomCountEquipment> toRoomCountEquipmentEntity(List<RoomEquipCountDto> roomCountEquipmentList) {
        List<RoomCountEquipment> countEquipments = new ArrayList<>();
        for (RoomEquipCountDto roomEquipCountDto : roomCountEquipmentList) {
            RoomCountEquipment countEquipment = new RoomCountEquipment();
            countEquipment.setId(roomEquipCountDto.getId());
            countEquipment.setCount(roomEquipCountDto.getCount());
            countEquipments.add(countEquipment);
        }
        return countEquipments;
    }

    public RoomCountEquipment toRoomCountEquipmentEntity(RoomEquipCountDto roomCountEquipment) {
        RoomCountEquipment countDto = new RoomCountEquipment();
        countDto.setId(roomCountEquipment.getId());
        countDto.setCount(roomCountEquipment.getCount());
        return countDto;
    }


    public Room toRoomEntity(RoomCreateUpdateDto roomDto) {
        Room room = new Room();
        room.setRoomName(roomDto.getRoomName());
        room.setCapacity(roomDto.getCapacity());
        room.setComment(roomDto.getComment());
        room.setRoomType(roomDto.getRoomType());
        room.setRoomCountEquipments(getRoomCountEquipmentList(roomDto));
        return room;
    }

    private List<RoomCountEquipment> getRoomCountEquipmentList(RoomCreateUpdateDto roomDto) {
        List<RoomCountEquipment> listEquipmentCount = new ArrayList<>();
        for (RoomEquipCountDto dto : roomDto.getRoomEquipCountDtoList()) {
            var roomCountEquipment = new RoomCountEquipment();
            var equipment = equipmentService.getEquipmentById(dto.getId());
            roomCountEquipment.setCount(dto.getCount());
            roomCountEquipment.setRoomEquipment(equipment);
            roomCountEquipmentRepository.save(roomCountEquipment);
            listEquipmentCount.add(roomCountEquipment);
        }
        return listEquipmentCount;
    }

    public RoomCreateUpdateDto toRoomDto(Room room) {
        RoomCreateUpdateDto roomDto = new RoomCreateUpdateDto();
        roomDto.setRoomName(room.getRoomName());
        roomDto.setCapacity(room.getCapacity());
        roomDto.setComment(room.getComment());
        roomDto.setRoomType(room.getRoomType());
        roomDto.setRoomEquipCountDtoList(room
                .getRoomCountEquipments()
                .stream()
                .map(value -> new RoomEquipCountDto(
                        value.getId(),
                        value.getCount()))
                .toList());
        return roomDto;
    }

    public RoomResponseDto toRoomResponseDto(Room room) {
        RoomResponseDto roomDto = new RoomResponseDto();
        roomDto.setId(room.getId());
        roomDto.setRoomName(room.getRoomName());
        roomDto.setCapacity(room.getCapacity());
        roomDto.setComment(room.getComment());
        roomDto.setRoomType(room.getRoomType());
        roomDto.setRoomEquipCountDtoList(room
                .getRoomCountEquipments()
                .stream()
                .map(value -> new RoomEquipCountDto(
                        value.getId(),
                        value.getCount()))
                .toList());
        return roomDto;
    }

    public RoomDto toRoomDtoAssigning(Room room) {
        RoomDto roomDto = new RoomDto();
        roomDto.setId(room.getId());
        roomDto.setRoomName(room.getRoomName());
        roomDto.setCapacity(room.getCapacity());
        roomDto.setComment(room.getComment());
        roomDto.setRoomType(room.getRoomType());
        roomDto.setGroupId(room.getGroup().getId());
        roomDto.setResponsiblePersonId(room.getResponsiblePerson().getId());
        return roomDto;
    }

    public Room toRoomUpdateEntity(Room room, RoomCreateUpdateDto roomDto) {
        List<RoomEquipCountDto> countDtoList = roomDto.getRoomEquipCountDtoList();
        List<RoomCountEquipment> roomCountEquipmentList = new ArrayList<>();
        for (RoomEquipCountDto countDto : countDtoList) {
            roomCountEquipmentList.add(toRoomCountEquipmentEntity(countDto));
            roomCountEquipmentRepository.saveAll(roomCountEquipmentList);
        }
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
        if (roomDto.getRoomEquipCountDtoList() != null) {
            room.setRoomCountEquipments(roomCountEquipmentList);
        }
        return room;
    }
}
