package uz.saidoff.crmecosystem.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import uz.saidoff.crmecosystem.entity.RoomEquipment;
import uz.saidoff.crmecosystem.exception.AlreadyExistException;
import uz.saidoff.crmecosystem.exception.NotFoundException;
import uz.saidoff.crmecosystem.mapper.RoomEquipmentMapper;
import uz.saidoff.crmecosystem.payload.RoomDeletedInfoResponse;
import uz.saidoff.crmecosystem.payload.RoomEquipmentDto;
import uz.saidoff.crmecosystem.repository.RoomEquipmentRepository;
import uz.saidoff.crmecosystem.response.ResponseData;

import java.util.*;

@RequiredArgsConstructor
@Service
public class EquipmentService {

    private final RoomEquipmentRepository roomEquipmentRepository;
    private final RoomEquipmentMapper roomEquipmentMapper;


    public ResponseData<?> addEquipment(List<RoomEquipmentDto> equipmentDto) {
        for (RoomEquipmentDto dto : equipmentDto) {
            List<RoomEquipment> equipmentList = roomEquipmentRepository.findAllByIdAndDeletedIsFalse(dto.getId());
            if (!equipmentList.isEmpty()) {
                throw new AlreadyExistException("This Equipment already exists");
            }
        }

        List<RoomEquipment> equipmentEntity = roomEquipmentMapper.toEquipmentEntity(equipmentDto);
        List<RoomEquipment> roomEquipmentList = roomEquipmentRepository.saveAll(equipmentEntity);
        List<RoomEquipmentDto> equipmentDtoList = roomEquipmentMapper.toEquipmentDtoList(roomEquipmentList);
        return new ResponseData<>(equipmentDtoList, true);
    }

    public ResponseData<?> updateEquipment(UUID equipmentId, RoomEquipmentDto roomEquipmentDto) {
        RoomEquipment equipment = roomEquipmentRepository.findById(equipmentId).orElseThrow(()
                -> new NotFoundException("Equipment not found"));
        RoomEquipment equipmentUpdate = roomEquipmentMapper.toEquipmentUpdate(equipment, roomEquipmentDto);
        RoomEquipment roomEquipment = roomEquipmentRepository.save(equipmentUpdate);
        RoomEquipmentDto dto = roomEquipmentMapper.toEquipmentDto(roomEquipment);
        return new ResponseData<>(dto, true);
    }

    public ResponseData<?> getRoomEquipment(UUID equipmentId) {
        RoomEquipment equipment = roomEquipmentRepository.findById(equipmentId).orElseThrow(() -> new NotFoundException("equipment not found"));
        RoomEquipmentDto roomEquipmentDto = roomEquipmentMapper.toEquipmentDto(equipment);
        return new ResponseData<>(roomEquipmentDto, true);
    }

    public ResponseData<?> getAllExistEquipments(int size, int page) {
        Pageable pageable = PageRequest.of(size, page);
        List<RoomEquipment> equipmentList = roomEquipmentRepository.findAllByDeletedIsFalse(pageable);
        List<RoomEquipmentDto> list = equipmentList
                .stream()
                .map(roomEquipmentMapper::toEquipmentDto)
                .toList();
        return new ResponseData<>(list, true);
    }

    public ResponseData<?> getAllEquipments(int size, int page) {
        Pageable pageable = PageRequest.of(size, page);
        Page<RoomEquipment> equipmentPage = roomEquipmentRepository.findAll(pageable);
        List<RoomEquipmentDto> list = equipmentPage
                .stream()
                .map(roomEquipmentMapper::toEquipmentDto)
                .toList();
        return new ResponseData<>(list, true);
    }

    public ResponseData<?> getAllDeletedEquipments(int size, int page) {
        Pageable pageable = PageRequest.of(size, page);
        List<RoomDeletedInfoResponse> deletedEquipments = roomEquipmentRepository.getDeletedEquipments(pageable);
        List<RoomEquipmentDto> list = deletedEquipments
                .stream()
                .map(roomEquipmentMapper::toEquipmentDto)
                .toList();
        return new ResponseData<>(list, true);

    }

    public ResponseData<?> deleteRoomEquipment(UUID equipmentId, int countToDelete) {
        RoomEquipment equipment = roomEquipmentRepository.findByIdAndDeletedFalse(equipmentId)
                .orElseThrow(() -> new NotFoundException("equipment not found"));
        if (equipment.getCount() >= countToDelete) {
            equipment.setCount(equipment.getCount() - countToDelete);
            equipment.setDeletedCount(equipment.getDeletedCount() + countToDelete);
            if (equipment.getCount() == 0) {
                equipment.setDeleted(true);
            }
        } else {
            throw new NotFoundException("Invalid or Insufficient number you required");
        }
        RoomEquipment roomEquipment = roomEquipmentRepository.save(equipment);
        return new ResponseData<>(roomEquipment, true);
    }
}
