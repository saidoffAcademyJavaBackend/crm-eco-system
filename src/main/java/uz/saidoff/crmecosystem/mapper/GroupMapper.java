package uz.saidoff.crmecosystem.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import uz.saidoff.crmecosystem.entity.Group;
import uz.saidoff.crmecosystem.entity.auth.User;
import uz.saidoff.crmecosystem.entity.template.AbsEntity;
import uz.saidoff.crmecosystem.exception.NotFoundException;
import uz.saidoff.crmecosystem.payload.GroupCreateDto;
import uz.saidoff.crmecosystem.payload.GroupDto;
import uz.saidoff.crmecosystem.repository.GroupTypeRepository;
import uz.saidoff.crmecosystem.repository.UserRepository;
import uz.saidoff.crmecosystem.util.MessageKey;
import uz.saidoff.crmecosystem.util.MessageService;

import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class GroupMapper {

    private final UserRepository userRepository;
    private final GroupTypeRepository groupTypeRepository;

    public Group toEntity(GroupCreateDto createDto){
        Group group = new Group();
        group.setName(createDto.getName());
        group.setActive(createDto.isActive());
//        group.setStartTime(createDto.getStartTime());
//        group.setEndTime(createDto.getEndTime());
        group.setLinkForTelegram(createDto.getLinkOfTelegram());
        group.setTeacher(userRepository.findById(createDto.getTeacherId()).orElseThrow(
                () -> new NotFoundException(MessageService.getMessage(MessageKey.USER_NOT_FOUND))));
        group.setWeekDays(createDto.getWeekDays());
        List<User> students = createDto.getStudentsId().stream()
                .map(id -> userRepository.findById(id).orElseThrow(
                        () -> new NotFoundException(MessageService.getMessage(MessageKey.USER_NOT_FOUND)))).toList();
        group.setStudents(students);
        group.setGroupType(groupTypeRepository.findById(createDto.getGroupTypeId()).orElseThrow(
                () -> new NotFoundException(MessageService.getMessage(MessageKey.NO_CONTENT))));

        return group;
    }

    public GroupDto toDto(Group group){
        GroupDto groupDto = new GroupDto();
        groupDto.setId(group.getId());
        groupDto.setName(group.getName());
        groupDto.setActive(group.isActive());
//        groupDto.setGroupStage(group.getGroupStage());
//        groupDto.setCountOfStudents(group.getCountOfStudents());
//        groupDto.setStartTime(group.getStartTime());
//        groupDto.setEndTime(group.getEndTime());
        groupDto.setLinkOfTelegram(group.getLinkForTelegram());
        groupDto.setWeekDays(group.getWeekDays());
        List<UUID> studentsId = group.getStudents().stream().map(AbsEntity::getId).toList();
        groupDto.setStudentsId(studentsId);
        groupDto.setTeacherId(group.getTeacher().getId());
//        groupDto.setGroupStage(group.getGroupStage());
        return groupDto;
    }


}
