package uz.saidoff.crmecosystem.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.saidoff.crmecosystem.entity.Group;
import uz.saidoff.crmecosystem.exception.NotFoundException;
import uz.saidoff.crmecosystem.mapper.GroupMapper;
import uz.saidoff.crmecosystem.payload.GroupCreateDto;
import uz.saidoff.crmecosystem.payload.GroupDto;
import uz.saidoff.crmecosystem.repository.GroupRepository;
import uz.saidoff.crmecosystem.response.ResponseData;
import uz.saidoff.crmecosystem.service.IGroupService;
import uz.saidoff.crmecosystem.util.MessageKey;
import uz.saidoff.crmecosystem.util.MessageService;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GroupService implements IGroupService {

    private final GroupRepository groupRepository;
    private final GroupMapper groupMapper;


    @Override
    public ResponseData<?> create(GroupCreateDto createDto) {
        Group group = groupMapper.toEntity(createDto);
        groupRepository.save(group);
        return ResponseData.successResponse("Success");
    }

    @Override
    public ResponseData<GroupDto> getById(UUID groupId) {
        Group group =  groupRepository.findByIdAndDeletedIsFalse(groupId).orElseThrow(
                () -> new NotFoundException(MessageService.getMessage(MessageKey.NO_CONTENT)));
        return ResponseData.successResponse(groupMapper.toDto(group));
    }

    @Override
    public ResponseData<List<GroupDto>> getAll() {
        List<Group> groups = groupRepository.findAllByDeletedIsFalse();
        if (groups.isEmpty())
            throw new NotFoundException(MessageService.getMessage(MessageKey.NO_CONTENT));
        return ResponseData.successResponse(
                groups.stream().map(groupMapper::toDto).toList());
    }
}
