package uz.saidoff.crmecosystem.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import uz.saidoff.crmecosystem.payload.GroupCreateDto;
import uz.saidoff.crmecosystem.payload.GroupDto;
import uz.saidoff.crmecosystem.response.ResponseData;
import uz.saidoff.crmecosystem.service.IGroupService;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/group")
@RequiredArgsConstructor
public class GroupController {

    private final IGroupService groupService;

    @PostMapping("create-group")
    private ResponseData<?> createGroup(@RequestBody GroupCreateDto createDto){
        return groupService.create(createDto);
    }

    @GetMapping("get-group-by-id/{group-id}")
    private ResponseData<GroupDto> getGroupById(@PathVariable("group-id") UUID groupId){
        return groupService.getById(groupId);
    }

    @GetMapping("get-all")
    private ResponseData<List<GroupDto>> getAllGroup(){
        return groupService.getAll();
    }
}
