package uz.saidoff.crmecosystem.controller;

import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import uz.saidoff.crmecosystem.payload.GroupCreateDto;
import uz.saidoff.crmecosystem.payload.GroupDto;
import uz.saidoff.crmecosystem.response.ResponseData;
import uz.saidoff.crmecosystem.service.GroupPayHistoryService;
import uz.saidoff.crmecosystem.service.GroupService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/group")
@RequiredArgsConstructor
public class GroupController {

    private final GroupService groupService;
    private final GroupPayHistoryService groupPayHistoryService;

    @PostMapping("create-group")
    private ResponseData<?> createGroup(@RequestBody GroupCreateDto createDto){
        return groupService.create(createDto);
    }

    @PostMapping("attach-student")
    private ResponseData<?> attachStudent(@PathParam("groupId") UUID groupId, @PathParam("studentId") UUID studentId){
        return groupService.attachStudentGroup(studentId, groupId);
    }

    @GetMapping("get-group-by-id/{group-id}")
    private ResponseData<GroupDto> getGroupById(@PathVariable("group-id") UUID groupId){
        return groupService.getById(groupId);
    }

    @GetMapping("get-all")
    private ResponseData<List<GroupDto>> getAllGroup(){
        return groupService.getAll();
    }

    @GetMapping("get-group-pay-by-id/{group-id}")
    private ResponseData<?> getGroupPayById(@PathVariable("group-id") UUID groupId){
        return this.groupPayHistoryService.getGroupPayHistory(groupId);
    }
}
