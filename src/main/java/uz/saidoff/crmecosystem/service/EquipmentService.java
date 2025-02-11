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
import uz.saidoff.crmecosystem.payload.RoomEquipCreateUpdateDto;
import uz.saidoff.crmecosystem.payload.RoomEquipmentDto;
import uz.saidoff.crmecosystem.repository.RoomEquipmentRepository;
import uz.saidoff.crmecosystem.response.ResponseData;

import java.util.*;

@RequiredArgsConstructor
@Service
public class EquipmentService {

    private final RoomEquipmentRepository roomEquipmentRepository;
    private final RoomEquipmentMapper roomEquipmentMapper;


    public ResponseData<?> addEquipment(List<RoomEquipCreateUpdateDto> equipmentDto) {
        for (RoomEquipCreateUpdateDto dto : equipmentDto) {
            List<RoomEquipment> equipmentList = roomEquipmentRepository.findAllByNameAndDeletedIsFalse(dto.getName());
            if (!equipmentList.isEmpty()) {
                throw new AlreadyExistException("This Equipment already exists");
            }
        }
        List<RoomEquipment> equipmentEntity = roomEquipmentMapper.toEquipmentEntity(equipmentDto);
        List<RoomEquipment> roomEquipmentList = roomEquipmentRepository.saveAll(equipmentEntity);
        List<RoomEquipmentDto> equipmentDtoList = roomEquipmentMapper.toEquipmentDtoList(roomEquipmentList);
        return new ResponseData<>(equipmentDtoList, true);
    }

    public ResponseData<?> updateEquipment(UUID equipmentId, RoomEquipCreateUpdateDto roomEquipmentDto) {
        RoomEquipment equipment = roomEquipmentRepository.findById(equipmentId).orElseThrow(()
                -> new NotFoundException("Equipment not found"));
        RoomEquipment equipmentUpdate = roomEquipmentMapper.toEquipmentUpdateEntity(equipment, roomEquipmentDto);
        RoomEquipment roomEquipment = roomEquipmentRepository.save(equipmentUpdate);
        RoomEquipmentDto dto = roomEquipmentMapper.toEquipmentDto(roomEquipment);
        return new ResponseData<>(dto, true);
    }

    public ResponseData<?> getRoomEquipment(UUID equipmentId) {
        RoomEquipment equipment = roomEquipmentRepository.findByIdAndDeletedFalse(equipmentId).orElseThrow(
                () -> new NotFoundException("equipment not found"));
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
    public ResponseData<?> deleteRoomEquipment(UUID equipmentId) {
        RoomEquipment equipment = roomEquipmentRepository.findByIdAndDeletedFalse(equipmentId)
                .orElseThrow(() -> new NotFoundException("Equipment not found"));
        equipment.setDeleted(true);
        RoomEquipment roomEquipment = roomEquipmentRepository.save(equipment);
        return new ResponseData<>(roomEquipment, true);
    }

    public RoomEquipment getEquipmentById(UUID equipmentId) {
        return roomEquipmentRepository.findByIdAndDeletedFalse(equipmentId).orElseThrow(
                () -> new NotFoundException("Not Found Equipment"));
    }

    public ResponseData<?> addEquipmentToExistEquipment(UUID equipmentId, int equipmentCount) {
        RoomEquipment roomEquipment = getEquipmentById(equipmentId);
        roomEquipment.setTotalNumber(roomEquipment.getTotalNumber() + equipmentCount);
        RoomEquipment equipment = roomEquipmentRepository.save(roomEquipment);
        RoomEquipmentDto roomEquipmentDto = roomEquipmentMapper.toEquipmentResponseDto(equipment);
        return new ResponseData<>(roomEquipmentDto, true);
    }

    public ResponseData<?> deleteSomeEquipment(UUID equipmentId, int toDeleteCount) {
        RoomEquipment roomEquipment = getEquipmentById(equipmentId);
        roomEquipment.setTotalNumber(roomEquipment.getTotalNumber() - toDeleteCount);
        RoomEquipment equipment = roomEquipmentRepository.save(roomEquipment);
        RoomEquipmentDto equipmentResponseDto = roomEquipmentMapper.toEquipmentResponseDto(equipment);
        return new ResponseData<>(equipmentResponseDto, true);
    }
}
