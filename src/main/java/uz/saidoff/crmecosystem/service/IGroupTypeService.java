package uz.saidoff.crmecosystem.service;

import uz.saidoff.crmecosystem.payload.GroupTypeCreateDto;
import uz.saidoff.crmecosystem.payload.GroupTypeDto;
import uz.saidoff.crmecosystem.response.ResponseData;

import java.util.List;
import java.util.UUID;

public interface IGroupTypeService {
    ResponseData<?> create(GroupTypeCreateDto createDto);

    ResponseData<GroupTypeDto> getById(UUID groupTypeId);

    ResponseData<List<GroupTypeDto>> getAll();
}
