package uz.saidoff.crmecosystem.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import uz.saidoff.crmecosystem.entity.Room;
import uz.saidoff.crmecosystem.entity.RoomCountEquipment;
import uz.saidoff.crmecosystem.payload.RoomCreateUpdateDto;
import uz.saidoff.crmecosystem.payload.RoomDto;
import uz.saidoff.crmecosystem.payload.RoomEquipCountDto;
import uz.saidoff.crmecosystem.payload.RoomResponseDto;
import uz.saidoff.crmecosystem.repository.RoomCountEquipmentRepository;
import uz.saidoff.crmecosystem.service.EquipmentService;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class RoomMapper {
  private final RoomCountEquipmentRepository roomCountEquipmentRepository;
  private final EquipmentService equipmentService;



  public List<RoomCountEquipment> toRoomCountEquipmentEntity(List<RoomCountEquipment> existEquipments,
                                                             List<RoomEquipCountDto> roomCountEquipmentList) {
    roomCountEquipmentRepository.deleteAll(existEquipments);
    return getRoomCountEquipmentList(roomCountEquipmentList);
  }




  public Room toRoomEntity(RoomCreateUpdateDto roomDto) {
    Room room = new Room();
    room.setRoomName(roomDto.getRoomName());
    room.setCapacity(roomDto.getCapacity());
    room.setComment(roomDto.getComment());
    room.setRoomType(roomDto.getRoomType());
    var roomEquipCountDtoList = roomDto.getRoomEquipCountDtoList();
    room.setRoomCountEquipments(getRoomCountEquipmentList(roomEquipCountDtoList));
    return room;
  }

  private List<RoomCountEquipment> getRoomCountEquipmentList(List<RoomEquipCountDto> roomDto) {
    List<RoomCountEquipment> listEquipmentCount = new ArrayList<>();
    for (RoomEquipCountDto dto : roomDto) {
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
        value.getRoomEquipment().getId(),
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

      room.setRoomCountEquipments(toRoomCountEquipmentEntity(room.getRoomCountEquipments(), roomDto.getRoomEquipCountDtoList()));
    }
    return room;
  }
}
