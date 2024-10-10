package uz.saidoff.crmecosystem.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.saidoff.crmecosystem.entity.Group;
import uz.saidoff.crmecosystem.entity.GroupStudent;
import uz.saidoff.crmecosystem.entity.auth.User;
import uz.saidoff.crmecosystem.exception.NotFoundException;
import uz.saidoff.crmecosystem.mapper.GroupMapper;
import uz.saidoff.crmecosystem.payload.GroupCreateDto;
import uz.saidoff.crmecosystem.payload.GroupDto;
import uz.saidoff.crmecosystem.repository.GroupRepository;
import uz.saidoff.crmecosystem.repository.GroupStudentRepository;
import uz.saidoff.crmecosystem.repository.UserRepository;
import uz.saidoff.crmecosystem.response.ResponseData;
import uz.saidoff.crmecosystem.util.MessageKey;
import uz.saidoff.crmecosystem.util.MessageService;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GroupService {

    private final GroupRepository groupRepository;
    private final GroupStudentRepository groupStudentRepository;
    private final UserRepository userRepository;
    private final GroupMapper groupMapper;


    public ResponseData<?> create(GroupCreateDto createDto) {
        Group group = groupMapper.toEntity(createDto);
        groupRepository.save(group);
        return ResponseData.successResponse("Success");
    }

    public ResponseData<?> attachStudentGroup(UUID studentId, UUID groupId) {
        User student = userRepository.findById(studentId).orElseThrow(
                () -> new NotFoundException(MessageService.getMessage(MessageKey.USER_NOT_FOUND)));
        Group group = groupRepository.findById(groupId).orElseThrow(
                () -> new NotFoundException(MessageService.getMessage(MessageKey.GROUP_NOT_FOUND)));
        GroupStudent groupStudent = new GroupStudent();
        groupStudent.setStudentId(student);
        groupStudent.setGroupId(group);
        groupStudentRepository.save(groupStudent);

        return ResponseData.successResponse(groupStudent.getId());
    }

    public ResponseData<GroupDto> getById(UUID groupId) {
        Group group =  groupRepository.findByIdAndDeletedIsFalse(groupId).orElseThrow(
                () -> new NotFoundException(MessageService.getMessage(MessageKey.NO_CONTENT)));
        return ResponseData.successResponse(groupMapper.toDto(group));
    }

    public ResponseData<List<GroupDto>> getAll() {
        List<Group> groups = groupRepository.findAllByDeletedIsFalse();
        if (groups.isEmpty())
            throw new NotFoundException(MessageService.getMessage(MessageKey.NO_CONTENT));
        return ResponseData.successResponse(
                groups.stream().map(groupMapper::toDto).toList());
    }
}
