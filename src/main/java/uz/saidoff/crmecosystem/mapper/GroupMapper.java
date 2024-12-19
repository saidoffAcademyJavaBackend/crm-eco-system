package uz.saidoff.crmecosystem.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import uz.saidoff.crmecosystem.entity.Group;
import uz.saidoff.crmecosystem.payload.GroupCreateDto;
import uz.saidoff.crmecosystem.payload.GroupDto;
import uz.saidoff.crmecosystem.service.GroupTypeService;
import uz.saidoff.crmecosystem.service.UserService;

@Component
@RequiredArgsConstructor
public class GroupMapper {

    private final UserService userService;
    private final GroupTypeService groupTypeService;

    public Group toEntity(GroupCreateDto createDto) {
        Group group = new Group();
        group.setName(createDto.getName());
        group.setTeacher(userService.getUserById(createDto.getTeacherId()));
        group.setStartTime(createDto.getStartTime());
        group.setEndTime(createDto.getEndTime());
        group.setStartedDate(createDto.getStartDate());
        group.setLinkForTelegram(createDto.getLinkOfTelegram());
        group.setGroupType(groupTypeService.getGroupTypeById(createDto.getGroupTypeId()));
        group.setWeekDays(createDto.getWeekDays());
        group.setActive(createDto.isActive());
        group.setStudent(createDto.isStudent());
        group.setPaymentAmount(createDto.getPaymentAmount());
        return group;
    }

    public GroupDto toDto(Group group) {
        GroupDto groupDto = new GroupDto();
        groupDto.setId(group.getId());
        groupDto.setName(group.getName());
        groupDto.setTeacherId(group.getTeacher().getId());
        groupDto.setStartTime(group.getStartTime());
        groupDto.setEndTime(group.getEndTime());
        groupDto.setStartDate(group.getStartedDate());
        groupDto.setLinkOfTelegram(group.getLinkForTelegram());
        groupDto.setGroupTypeId(group.getGroupType().getId());
        groupDto.setWeekDays(group.getWeekDays());
        groupDto.setActive(group.isActive());
        groupDto.setStudent(group.isStudent());
        groupDto.setGroupStage(group.getGroupStage());
        return groupDto;
    }


}
