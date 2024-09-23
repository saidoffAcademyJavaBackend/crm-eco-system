package uz.saidoff.crmecosystem.service;

import uz.saidoff.crmecosystem.payload.GroupCreateDto;
import uz.saidoff.crmecosystem.payload.GroupDto;
import uz.saidoff.crmecosystem.response.ResponseData;
import java.util.List;
import java.util.UUID;

public interface IGroupService {

    ResponseData<?> create(GroupCreateDto createDto);

    ResponseData<GroupDto> getById(UUID groupId);

    ResponseData<List<GroupDto>> getAll();
}
