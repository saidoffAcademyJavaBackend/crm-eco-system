package uz.saidoff.crmecosystem.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import uz.saidoff.crmecosystem.entity.Group;
import uz.saidoff.crmecosystem.entity.Room;
import uz.saidoff.crmecosystem.entity.RoomCountEquipment;
import uz.saidoff.crmecosystem.entity.RoomEquipment;
import uz.saidoff.crmecosystem.entity.auth.User;
import uz.saidoff.crmecosystem.enums.RoomStatus;
import uz.saidoff.crmecosystem.exception.AlreadyExistException;
import uz.saidoff.crmecosystem.exception.NotFoundException;
import uz.saidoff.crmecosystem.mapper.RoomEquipmentMapper;
import uz.saidoff.crmecosystem.mapper.RoomMapper;
import uz.saidoff.crmecosystem.payload.*;
import uz.saidoff.crmecosystem.repository.*;
import uz.saidoff.crmecosystem.response.ResponseData;

import java.util.*;

@RequiredArgsConstructor
@Service
public class RoomService {

    private final RoomRepository roomRepository;
    private final RoomEquipmentRepository roomAssignmentRepository;
    private final RoomMapper roomMapper;
    private final UserRepository userRepository;
    private final GroupRepository groupRepository;
    private final RoomEquipmentRepository roomEquipmentRepository;
    private final RoomEquipmentMapper roomEquipmentMapper;
    private final RoomCountEquipmentRepository roomCountEquipmentRepository;


    public ResponseData<?> addRoom(RoomCreateUpdateDto roomDto) {
        Optional<Room> optionalRoom = roomRepository.findByRoomNameAndDeletedFalse(roomDto.getRoomName());
        if (optionalRoom.isPresent()) {
            throw new AlreadyExistException("Room already exists");
        }
        Room room = roomMapper.toRoomEntity(roomDto);
        Room addedRoom = roomRepository.save(room);
        RoomResponseDto mapperRoomDto = roomMapper.toRoomResponseDto(addedRoom);
        return new ResponseData<>(mapperRoomDto, true);
    }

    public ResponseData<?> updateRoom(UUID roomId, RoomCreateUpdateDto roomDto) {
        Room room = roomRepository.findRoomByIdAndDeletedFalse(roomId).orElseThrow(()
                -> new NotFoundException("room not found"));
        Room updatedRoom = roomMapper.toRoomUpdateEntity(room, roomDto);
        Room savedRoom = roomRepository.save(updatedRoom);
//        RoomResponseDto mapperRoomDto = roomMapper.toRoomResponseDto(savedRoom);
        return new ResponseData<>("Room has been successfully updated: " + savedRoom, true);
    }

    public ResponseData<?> getRoom(UUID roomId) {
        Room room = roomRepository.findById(roomId).orElseThrow(
                () -> new NotFoundException("room not found"));
        RoomResponseDto mapperRoomDto = roomMapper.toRoomResponseDto(room);
        return new ResponseData<>(mapperRoomDto, true);
    }

    public ResponseData<?> getAllRooms(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Room> roomPage = roomRepository.findAll(pageable);
        List<RoomResponseDto> list = roomPage
                .stream()
                .map(roomMapper::toRoomResponseDto)
                .toList();
        Map<String, Object> response = new HashMap<>();
        response.put("Rooms:", list);
        response.put("Total Elements: ", roomPage.getTotalElements());
        response.put("Total pages: ", roomPage.getTotalPages());
        return new ResponseData<>(response, true);
    }

    public ResponseData<?> getAvailableRoom(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        List<Room> roomList = roomRepository.findAllByDeletedIsFalse(pageable);
        List<RoomResponseDto> list = roomList
                .stream()
                .map(roomMapper::toRoomResponseDto)
                .toList();
        return new ResponseData<>(list, true);
    }

    public ResponseData<?> getUnAvailableRoom(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        List<Room> roomList = roomRepository.findAllByDeletedIsTrue(pageable);
        List<RoomCreateUpdateDto> list = roomList
                .stream()
                .map(roomMapper::toRoomDto)
                .toList();
        return new ResponseData<>(list, true);
    }

    public ResponseData<?> deleteRoom(UUID roomId) {
        Room room = roomRepository.findById(roomId).orElseThrow(()
                -> new NotFoundException("room not found"));
        room.setDeleted(true);
        roomRepository.save(room);
        return new ResponseData<>("Room successfully has been deleted", true);
    }

    public ResponseData<?> assignRoom(UUID roomId, UUID userId, UUID groupId) {
        Room room = roomRepository.findRoomByIdAndDeletedFalse(roomId).orElseThrow(
                () -> new NotFoundException("room not found"));
        Group group = groupRepository.findByIdAndDeletedIsFalse(groupId).orElseThrow(
                () -> new NotFoundException("group not found"));
        User user = userRepository.findByIdAndDeletedFalse(userId).orElseThrow(
                () -> new NotFoundException("User not found"));

        room.setGroup(group);
        room.setResponsiblePerson(user);
        Room savedRoom = roomRepository.save(room);
        RoomDto roomDtoAssigning = roomMapper.toRoomDtoAssigning(savedRoom);
        return new ResponseData<>(roomDtoAssigning, true);
    }

    public ResponseData<?> getGroupsByRoomId(UUID roomId) {
        Room room = roomRepository.findRoomByIdAndDeletedFalse(roomId).orElseThrow(() -> new NotFoundException("room not found"));
        List<GroupInfoInRoomResponse> groupsByRoomId = roomAssignmentRepository.getGroupsByRoomId(room.getId());
        if (groupsByRoomId.isEmpty()) {
            throw new NotFoundException("groups not found");
        }
        return new ResponseData<>(groupsByRoomId, true);
    }
//
//    public ResponseData<?> updateAssignRoom(UUID roomId, UUID userId, UUID groupId, List<WeekDays> daysAssigned) {
//        if (daysAssigned == null || daysAssigned.isEmpty()) {
//            throw new NotFoundException("Days assigned cannot be empty");
//        }
//        Room room = roomRepository.findRoomByIdAndDeletedFalse(roomId).orElseThrow(()
//                -> new NotFoundException("room not found"));
//        User user = userRepository.findByIdAndDeletedFalse(userId).orElseThrow(()
//                -> new NotFoundException("user not found"));
//        Group group = groupRepository.findByIdAndDeletedIsFalse(groupId).orElseThrow(()
//                -> new NotFoundException("group not found"));
//
//        RoomEquipment assignment = roomAssignmentRepository.findByRoomIdAndResponsiblePersonIdAndGroupId(roomId, userId, groupId).orElseThrow(
//                () -> new NotFoundException("assignment not found"));
//
//        assignment.setRoom(room);
//        assignment.setResponsiblePerson(user);
//        assignment.setGroup(group);
//        assignment.setWeekDays(daysAssigned);
//        roomAssignmentRepository.save(assignment);
//        return new ResponseData<>("successfully updated", true);
//    }
//
//    public ResponseData<?> getTeacherAvailableDaysAndTime(UUID teacherId) {
//
//        return null;
//    }
}
