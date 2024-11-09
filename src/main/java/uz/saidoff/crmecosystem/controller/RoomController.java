package uz.saidoff.crmecosystem.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.saidoff.crmecosystem.payload.RoomCreateUpdateDto;
import uz.saidoff.crmecosystem.payload.RoomEquipCountDto;
import uz.saidoff.crmecosystem.response.ResponseData;
import uz.saidoff.crmecosystem.service.RoomService;
import uz.saidoff.crmecosystem.valid.CheckPermission;

import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/rooms")
public class RoomController {

    private final RoomService roomService;


    @CheckPermission("ADD_ROOM")
    @PostMapping("/add_room")
    public ResponseEntity<?> addRoom(@RequestBody RoomCreateUpdateDto roomDto) {
        ResponseData<?> responseData = roomService.addRoom(roomDto);
        return ResponseEntity.ok(responseData);
    }

    @CheckPermission("UPDATE_ROOM")
    @PutMapping("updateRoom/{roomId}")
    public HttpEntity<?> updateRoom(@PathVariable(name = "roomId") UUID roomId, @RequestBody RoomCreateUpdateDto roomDto) {
        ResponseData<?> responseData = roomService.updateRoom(roomId, roomDto);
        return ResponseEntity.ok(responseData);
    }

    @CheckPermission("GET_ROOM")
    @GetMapping("/getRoom/{roomId}")
    public ResponseEntity<?> getRoom(@PathVariable("roomId") UUID roomId) {
        ResponseData<?> room = roomService.getRoom(roomId);
        return ResponseEntity.ok(room);
    }

    @CheckPermission("GET_ALL_ROOMS")
    @GetMapping("/getAllRooms")
    public ResponseEntity<?> getRoom(@RequestParam(name = "page", defaultValue = "0") int page,
                                     @RequestParam(name = "size", defaultValue = "10") int size) {
        ResponseData<?> room = roomService.getAllRooms(page, size);
        return ResponseEntity.ok(room);
    }

    @CheckPermission("DELETE_ROOM")
    @DeleteMapping("/deleteRoom/{roomId}")
    public HttpEntity<?> removeRoom(@PathVariable(name = "roomId") UUID roomId) {
        ResponseData<?> responseData = roomService.deleteRoom(roomId);
        return ResponseEntity.ok(responseData);
    }

    @CheckPermission("GET_ALL_ROOMS")
    @GetMapping("/getAllAvailableRooms")
    public ResponseEntity<?> getAvailableRoom(@RequestParam(name = "page", defaultValue = "0") int page,
                                              @RequestParam(name = "size", defaultValue = "10") int size) {
        ResponseData<?> room = roomService.getAvailableRoom(page, size);
        return ResponseEntity.ok(room);
    }

    @CheckPermission("GET_ALL_ROOMS")
    @GetMapping("/getUnAllAvailableRooms")
    public ResponseEntity<?> getUnAvailableRoom(@RequestParam(name = "page", defaultValue = "0") int page,
                                                @RequestParam(name = "size", defaultValue = "10") int size) {
        ResponseData<?> room = roomService.getUnAvailableRoom(page, size);
        return ResponseEntity.ok(room);
    }

    @CheckPermission("ASSIGN_ROOM")
    @PostMapping("/assignRoom")
    public HttpEntity<?> assignRoom(
            @RequestParam UUID roomId,
            @RequestParam UUID groupId,
            @RequestParam UUID userId) {
        ResponseData<?> responseData = roomService.assignRoom(roomId, userId, groupId);
        return ResponseEntity.ok(responseData);
    }

    @CheckPermission("GET_GROUPS_IN_ROOM")
    @GetMapping("/getGroupsByRoom/{roomId}")
    public HttpEntity<?> getGroupsByRoomId(@PathVariable(name = "roomId") UUID roomId) {
        ResponseData<?> groupsByRoomId = roomService.getGroupsByRoomId(roomId);
        return ResponseEntity.ok(groupsByRoomId);
    }


}
