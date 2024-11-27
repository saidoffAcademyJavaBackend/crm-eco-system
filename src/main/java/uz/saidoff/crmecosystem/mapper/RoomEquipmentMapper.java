package uz.saidoff.crmecosystem.mapper;

import org.springframework.stereotype.Component;
import uz.saidoff.crmecosystem.entity.RoomEquipment;
import uz.saidoff.crmecosystem.payload.RoomEquipCreateUpdateDto;
import uz.saidoff.crmecosystem.payload.RoomDeletedInfoResponse;
import uz.saidoff.crmecosystem.payload.RoomEquipmentDto;
import uz.saidoff.crmecosystem.repository.RoomCountEquipmentRepository;

import java.util.ArrayList;
import java.util.List;

@Component
public class RoomEquipmentMapper {

    private final RoomCountEquipmentRepository roomCountEquipmentRepository;

    public RoomEquipmentMapper(RoomCountEquipmentRepository roomCountEquipmentRepository) {
        this.roomCountEquipmentRepository = roomCountEquipmentRepository;
    }

    public List<RoomEquipment> toEquipmentEntity(List<RoomEquipCreateUpdateDto> dto) {
        List<RoomEquipment> roomEquipmentList = new ArrayList<>();
        for (RoomEquipCreateUpdateDto equipmentDto : dto) {
            RoomEquipment equipment = new RoomEquipment();
            equipment.setName(equipmentDto.getName());
            equipment.setTotalNumber(equipmentDto.getTotalNumber());
            roomEquipmentList.add(equipment);
        }
        return roomEquipmentList;
    }

    public RoomEquipment toEquipmentEntity(RoomEquipCreateUpdateDto dto) {
        RoomEquipment equipment = new RoomEquipment();
        equipment.setName(dto.getName());
        equipment.setTotalNumber(dto.getTotalNumber());
        return equipment;
    }

    public List<RoomEquipment> toEquipmentEntityResponse(List<RoomEquipCreateUpdateDto> dto) {
        List<RoomEquipment> roomEquipmentList = new ArrayList<>();
        for (RoomEquipCreateUpdateDto roomEquipCreateUpdateDto : dto) {
            RoomEquipment equipment = new RoomEquipment();
            equipment.setName(roomEquipCreateUpdateDto.getName());
            roomEquipmentList.add(equipment);
        }
        return roomEquipmentList;
    }

    public List<RoomEquipCreateUpdateDto> toRoomEquipDto(List<RoomEquipment> dto) {
        List<RoomEquipCreateUpdateDto> roomEquipmentList = new ArrayList<>();
        for (RoomEquipment roomEquipment : dto) {
            RoomEquipCreateUpdateDto equipDto = new RoomEquipCreateUpdateDto();
            equipDto.setName(roomEquipment.getName());
            roomEquipmentList.add(equipDto);
        }
        return roomEquipmentList;

    }

    public RoomEquipmentDto toEquipmentDto(RoomEquipment roomEquipment) {
        RoomEquipmentDto dto = new RoomEquipmentDto();
        dto.setId(roomEquipment.getId());
        dto.setName(roomEquipment.getName());
        dto.setCount(roomEquipment.getTotalNumber());
        return dto;
    }

    public RoomEquipmentDto toEquipmentResponseDto(RoomEquipment roomEquipment) {
        RoomEquipmentDto dto = new RoomEquipmentDto();
        dto.setId(roomEquipment.getId());
        dto.setName(roomEquipment.getName());
        dto.setCount(roomEquipment.getTotalNumber());
        return dto;
    }

    public List<RoomEquipmentDto> toEquipmentDtoList(List<RoomEquipment> roomEquipment) {
        List<RoomEquipmentDto> equipmentDtoList = new ArrayList<>();
        for (RoomEquipment equipment : roomEquipment) {
            RoomEquipmentDto dto = new RoomEquipmentDto();
            dto.setId(equipment.getId());
            dto.setName(equipment.getName());
            dto.setCount(equipment.getTotalNumber());
            equipmentDtoList.add(dto);
        }
        return equipmentDtoList;
    }

    public RoomEquipment toEquipmentUpdateEntity(RoomEquipment roomEquipment, RoomEquipCreateUpdateDto roomEquipmentDto) {

        if (roomEquipmentDto.getName() != null) {
            roomEquipment.setName(roomEquipmentDto.getName());
        }
        if (roomEquipmentDto.getTotalNumber() != 0) {
            roomEquipment.setTotalNumber(roomEquipmentDto.getTotalNumber());
        }
        return roomEquipment;
    }
}
