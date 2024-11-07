package uz.saidoff.crmecosystem.mapper;

import org.springframework.stereotype.Component;
import uz.saidoff.crmecosystem.entity.RoomEquipment;
import uz.saidoff.crmecosystem.payload.RoomCreateUpdateDto;
import uz.saidoff.crmecosystem.payload.RoomCreateUpdateEquipDto;
import uz.saidoff.crmecosystem.payload.RoomDeletedInfoResponse;
import uz.saidoff.crmecosystem.payload.RoomEquipmentDto;
import uz.saidoff.crmecosystem.repository.RoomEquipmentRepository;

import java.util.ArrayList;
import java.util.List;

@Component
public class RoomEquipmentMapper {

    public List<RoomEquipment> toEquipmentEntity(List<RoomEquipmentDto> dto) {
        List<RoomEquipment> roomEquipmentList = new ArrayList<>();
        for (RoomEquipmentDto equipmentDto : dto) {
            RoomEquipment equipment = new RoomEquipment();
            equipment.setName(equipmentDto.getName());
            equipment.setCount(equipmentDto.getNumber());
            equipment.setDeleted(false);
            roomEquipmentList.add(equipment);
        }
        return roomEquipmentList;
    }

    public List<RoomEquipment> toEquipmentEntityResponse(List<RoomCreateUpdateEquipDto> dto) {
        List<RoomEquipment> roomEquipmentList = new ArrayList<>();
        for (RoomCreateUpdateEquipDto roomCreateUpdateEquipDto : dto) {
            RoomEquipment equipment = new RoomEquipment();
            equipment.setName(roomCreateUpdateEquipDto.getName());
            equipment.setCount(roomCreateUpdateEquipDto.getCount());
            roomEquipmentList.add(equipment);
        }
        return roomEquipmentList;
    }
    public List<RoomCreateUpdateEquipDto> toRoomEquipDto(List<RoomEquipment> dto) {
        List<RoomCreateUpdateEquipDto> roomEquipmentList = new ArrayList<>();
        for (RoomEquipment roomEquipment : dto) {
            RoomCreateUpdateEquipDto equipDto = new RoomCreateUpdateEquipDto();
            equipDto.setName(roomEquipment.getName());
            equipDto.setCount(roomEquipment.getCount());
            roomEquipmentList.add(equipDto);
        }
        return roomEquipmentList;

    }

    public RoomEquipmentDto toEquipmentDto(RoomEquipment roomEquipment) {
        RoomEquipmentDto dto = new RoomEquipmentDto();
        dto.setId(roomEquipment.getId());
        dto.setName(roomEquipment.getName());
        dto.setNumber(roomEquipment.getCount());
        return dto;
    }

    public RoomEquipmentDto toEquipmentDto(RoomDeletedInfoResponse roomEquipment) {
        RoomEquipmentDto dto = new RoomEquipmentDto();
        dto.setId(roomEquipment.getId());
        dto.setName(roomEquipment.getName());
        dto.setNumber(roomEquipment.getDeletedCount());
        return dto;
    }

    public List<RoomEquipmentDto> toEquipmentDtoList(List<RoomEquipment> roomEquipment) {
        List<RoomEquipmentDto> equipmentDtoList = new ArrayList<>();
        for (RoomEquipment equipment : roomEquipment) {
            RoomEquipmentDto dto = new RoomEquipmentDto();
            dto.setName(equipment.getName());
            dto.setNumber(equipment.getCount());
            dto.setId(equipment.getId());
            equipmentDtoList.add(dto);
        }
        return equipmentDtoList;
    }

    public RoomEquipment toEquipmentUpdate(RoomEquipment roomEquipment, RoomEquipmentDto roomEquipmentDto) {

        if (roomEquipmentDto.getName() != null) {
            roomEquipment.setName(roomEquipmentDto.getName());
        }
        if (roomEquipmentDto.getNumber() != 0) {
            roomEquipment.setCount(roomEquipmentDto.getNumber());
        }
        return roomEquipment;
    }
}
