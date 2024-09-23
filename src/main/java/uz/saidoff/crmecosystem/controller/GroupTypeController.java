package uz.saidoff.crmecosystem.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import uz.saidoff.crmecosystem.payload.GroupTypeCreateDto;
import uz.saidoff.crmecosystem.payload.GroupTypeDto;
import uz.saidoff.crmecosystem.response.ResponseData;
import uz.saidoff.crmecosystem.service.IGroupTypeService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/group-type")
@RequiredArgsConstructor
public class GroupTypeController {

    private final IGroupTypeService groupTypeService;

    @PostMapping("create")
    private ResponseData<?> create(@RequestBody GroupTypeCreateDto createDto){
        return groupTypeService.create(createDto);
    }

    @GetMapping("get-by-id/{group-type-id}")
    private ResponseData<GroupTypeDto> getById(@PathVariable("group-type-id") UUID groupTypeId){
        return groupTypeService.getById(groupTypeId);
    }

    @GetMapping("get-all")
    private ResponseData<List<GroupTypeDto>> getAll(){
        return groupTypeService.getAll();
    }
}
