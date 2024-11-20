package uz.saidoff.crmecosystem.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.saidoff.crmecosystem.payload.RoomEquipCreateUpdateDto;
import uz.saidoff.crmecosystem.payload.RoomEquipmentDto;
import uz.saidoff.crmecosystem.response.ResponseData;
import uz.saidoff.crmecosystem.service.EquipmentService;
import uz.saidoff.crmecosystem.valid.CheckPermission;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/equipments")
public class RoomEquipmentController {

    private final EquipmentService equipmentService;

    @CheckPermission("ADD_EQUIPMENT")
    @PostMapping("add_equipment")
    public HttpEntity<?> addEquipment(@RequestBody List<RoomEquipCreateUpdateDto> equipmentDto) {
        ResponseData<?> responseData = equipmentService.addEquipment(equipmentDto);
        return ResponseEntity.ok(responseData);
    }

    @CheckPermission("UPDATE_EQUIPMENT")
    @PutMapping("update_equipment/{equipmentId}")
    public HttpEntity<?> updateEquipment(@PathVariable("equipmentId") UUID equipmentId, @RequestBody RoomEquipCreateUpdateDto roomEquipmentDto) {
        ResponseData<?> responseData = equipmentService.updateEquipment(equipmentId, roomEquipmentDto);
        return ResponseEntity.ok(responseData);
    }

    @CheckPermission("GET_EQUIPMENT")
    @GetMapping("getEquipment/{equipmentId}")
    public HttpEntity<?> getEquipment(@PathVariable("equipmentId") UUID equipmentId) {
        ResponseData<?> roomEquipment = equipmentService.getRoomEquipment(equipmentId);
        return ResponseEntity.ok(roomEquipment);
    }

    @CheckPermission("GET_ALL_EXIST_EQUIPMENTS")
    @GetMapping("get_all_exist_equipments")
    public HttpEntity<?> getAllExistEquipments(@RequestParam(value = "size", defaultValue = "0") int size,
                                               @RequestParam(value = "page", defaultValue = "10") int page) {
        ResponseData<?> allEquipments = equipmentService.getAllExistEquipments(size, page);
        return ResponseEntity.ok(allEquipments);
    }

    @CheckPermission("GET_ALL_EQUIPMENTS")
    @GetMapping("get_all_equipments")
    public HttpEntity<?> getAllEquipments(@RequestParam(value = "size", defaultValue = "0") int size,
                                               @RequestParam(value = "page", defaultValue = "10") int page) {
        ResponseData<?> allEquipments = equipmentService.getAllEquipments(size, page);
        return ResponseEntity.ok(allEquipments);
    }
    @CheckPermission("GET_ALL_DELETED_EQUIPMENTS")
    @GetMapping("get_all_deleted_equipments")
    public HttpEntity<?> getAllDeletedEquipments(@RequestParam(value = "size", defaultValue = "0") int size,
                                          @RequestParam(value = "page", defaultValue = "10") int page) {
//        ResponseData<?> allEquipments = equipmentService.getAllDeletedEquipments(size, page);
        return ResponseEntity.ok(null);
    }

    @CheckPermission("DELETE_EQUIPMENT")
    @DeleteMapping("/delete_equipment/{equipmentId}")
    public HttpEntity<?> deleteEquipment(@PathVariable("equipmentId") UUID equipmentId, @RequestParam("number") int number) {
        ResponseData<?> responseData = equipmentService.deleteRoomEquipment(equipmentId, number);
        return ResponseEntity.ok(responseData);
    }
}
