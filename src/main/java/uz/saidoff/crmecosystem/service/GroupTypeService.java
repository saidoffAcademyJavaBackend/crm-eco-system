package uz.saidoff.crmecosystem.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.saidoff.crmecosystem.entity.GroupType;
import uz.saidoff.crmecosystem.exception.NotFoundException;
import uz.saidoff.crmecosystem.mapper.GroupTypeMapper;
import uz.saidoff.crmecosystem.payload.GroupTypeCreateDto;
import uz.saidoff.crmecosystem.payload.GroupTypeDto;
import uz.saidoff.crmecosystem.repository.GroupTypeRepository;
import uz.saidoff.crmecosystem.response.ResponseData;
import uz.saidoff.crmecosystem.util.MessageKey;
import uz.saidoff.crmecosystem.util.MessageService;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GroupTypeService {

    private final GroupTypeRepository groupTypeRepository;
    private final GroupTypeMapper groupTypeMapper;

    public ResponseData<?> create(GroupTypeCreateDto createDto) {
        GroupType groupType = groupTypeMapper.toEntity(createDto);
        groupTypeRepository.save(groupType);
        return ResponseData.successResponse("Success");
    }

    public ResponseData<GroupTypeDto> getById(UUID groupTypeId) {
        GroupType groupType = groupTypeRepository.findById(groupTypeId).orElseThrow(
                () -> new NotFoundException(MessageService.getMessage(MessageKey.NO_CONTENT)));
        return ResponseData.successResponse(groupTypeMapper.toDto(groupType));
    }

    public ResponseData<List<GroupTypeDto>> getAll() {
        List<GroupType> groupTypeList = groupTypeRepository.findAllByDeletedIsFalse();
        if (groupTypeList.isEmpty())
            throw new NotFoundException(MessageService.getMessage(MessageKey.NO_CONTENT));
        return ResponseData.successResponse(groupTypeList
                .stream()
                .map(groupTypeMapper::toDto)
                .toList());
    }

    public GroupType getGroupTypeById(UUID groupTypeId) {
        return groupTypeRepository.findByIdAndDeletedIsFalse(groupTypeId).orElseThrow(
                () -> new NotFoundException(MessageService.getMessage(MessageKey.NO_CONTENT)));
    }
}
