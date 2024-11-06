package uz.saidoff.crmecosystem.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import uz.saidoff.crmecosystem.entity.Group;
import uz.saidoff.crmecosystem.entity.Room;
import uz.saidoff.crmecosystem.entity.RoomAssignment;
import uz.saidoff.crmecosystem.entity.auth.User;
import uz.saidoff.crmecosystem.enums.RoomStatus;
import uz.saidoff.crmecosystem.enums.WeekDays;
import uz.saidoff.crmecosystem.exception.AlreadyExistException;
import uz.saidoff.crmecosystem.exception.NotFoundException;
import uz.saidoff.crmecosystem.mapper.RoomMapper;
import uz.saidoff.crmecosystem.payload.GroupInfoInRoomResponse;
import uz.saidoff.crmecosystem.payload.RoomCreateUpdateDto;
import uz.saidoff.crmecosystem.payload.RoomDto;
import uz.saidoff.crmecosystem.repository.GroupRepository;
import uz.saidoff.crmecosystem.repository.RoomAssignmentRepository;
import uz.saidoff.crmecosystem.repository.RoomRepository;
import uz.saidoff.crmecosystem.repository.UserRepository;
import uz.saidoff.crmecosystem.response.ResponseData;

import java.util.*;

@RequiredArgsConstructor
@Service
public class RoomService {

    private final RoomRepository roomRepository;
    private final RoomAssignmentRepository roomAssignmentRepository;
    private final RoomMapper roomMapper;
    private final UserRepository userRepository;
    private final GroupRepository groupRepository;


    public ResponseData<?> addRoom(RoomCreateUpdateDto roomDto) {
        Optional<Room> optionalRoom = roomRepository.findByRoomNameAndDeletedFalse(roomDto.getRoomName());
        if (optionalRoom.isPresent()) {
            throw new AlreadyExistException("Room already exists");
        }
        Room room = roomMapper.toRoomEntity(roomDto);
        roomRepository.save(room);
        return new ResponseData<>("Room has been added", true);
    }

    public ResponseData<?> updateRoom(UUID roomId, RoomCreateUpdateDto roomDto) {
        Room room = roomRepository.findRoomByIdAndDeletedFalse(roomId).orElseThrow(()
                -> new NotFoundException("room not found"));
        Room updatedRoom = roomMapper.toRoomUpdateEntity(room, roomDto);
        Room savedRoom = roomRepository.save(updatedRoom);
        roomMapper.toRoomDto(savedRoom);
        return new ResponseData<>("Room has been successfully updated", true);
    }

    public ResponseData<?> getRoom(UUID roomId) {
        Room room = roomRepository.findById(roomId).orElseThrow(() -> new NotFoundException("room not found"));
        RoomDto roomDto = roomMapper.toRoomDto(room);
        return new ResponseData<>(roomDto, true);
    }

    public ResponseData<?> getAllRooms(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Room> roomPage = roomRepository.findAll(pageable);
        List<RoomDto> roomDtoList = roomPage
                .stream()
                .map(roomMapper::toRoomDto)
                .toList();
        Map<String, Object> response = new HashMap<>();
        response.put("Rooms:", roomDtoList);
        response.put("Total Elements: ", roomPage.getTotalElements());
        response.put("Total pages: ", roomPage.getTotalPages());
        return new ResponseData<>(response, true);
    }

    public ResponseData<?> getAvailableRoom(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        List<Room> roomList = roomRepository.findAllByDeletedIsFalse(pageable);
        List<RoomDto> roomDtoList = roomList
                .stream()
                .map(roomMapper::toRoomDto)
                .toList();
        return new ResponseData<>(roomDtoList, true);
    }

    public ResponseData<?> getUnAvailableRoom(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        List<Room> roomList = roomRepository.findAllByDeletedIsTrue(pageable);
        List<RoomDto> roomDtoList = roomList
                .stream()
                .map(roomMapper::toRoomDto)
                .toList();
        return new ResponseData<>(roomDtoList, true);
    }

    public ResponseData<?> deleteRoom(UUID roomId) {
        Room room = roomRepository.findById(roomId).orElseThrow(()
                -> new NotFoundException("room not found"));
        room.setRoomStatus(RoomStatus.UNAVAILABLE);
        room.setDeleted(true);
        roomRepository.save(room);
        return new ResponseData<>("Room successfully has been deleted", true);
    }

    public ResponseData<?> assignRoom(UUID roomId, UUID userId, UUID groupId, List<WeekDays> daysAssigned) {
        if (daysAssigned == null || daysAssigned.isEmpty()) {
            throw new NotFoundException("Days assigned cannot be empty");
        }
        Room room = roomRepository.findRoomByIdAndDeletedFalse(roomId).orElseThrow(()
                -> new NotFoundException("room not found"));
        User user = userRepository.findByIdAndDeletedFalse(userId).orElseThrow(()
                -> new NotFoundException("user not found"));
        Group group = groupRepository.findByIdAndDeletedIsFalse(groupId).orElseThrow(()
                -> new NotFoundException("group not found"));
        RoomAssignment roomAssignment = new RoomAssignment();
        roomAssignment.setRoom(room);
        roomAssignment.setGroup(group);
        roomAssignment.setResponsiblePerson(user);
        roomAssignment.setWeekDays(daysAssigned);
        roomAssignmentRepository.save(roomAssignment);
        return new ResponseData<>("Room has been assigned: ", true);
    }

    public ResponseData<?> getGroupsByRoomId(UUID roomId) {
        Room room = roomRepository.findRoomByIdAndDeletedFalse(roomId).orElseThrow(() -> new NotFoundException("room not found"));
        List<GroupInfoInRoomResponse> groupsByRoomId = roomAssignmentRepository.getGroupsByRoomId(room.getId());
        if (groupsByRoomId.isEmpty()) {
            throw new NotFoundException("groups not found");
        }
        return new ResponseData<>(groupsByRoomId, true);
    }
}
