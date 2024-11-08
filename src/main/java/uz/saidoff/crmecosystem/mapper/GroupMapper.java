package uz.saidoff.crmecosystem.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import uz.saidoff.crmecosystem.entity.Group;
import uz.saidoff.crmecosystem.exception.NotFoundException;
import uz.saidoff.crmecosystem.payload.GroupCreateDto;
import uz.saidoff.crmecosystem.payload.GroupDto;
import uz.saidoff.crmecosystem.repository.GroupTypeRepository;
import uz.saidoff.crmecosystem.repository.RoomRepository;
import uz.saidoff.crmecosystem.repository.UserRepository;
import uz.saidoff.crmecosystem.util.MessageKey;
import uz.saidoff.crmecosystem.util.MessageService;

@Component
@RequiredArgsConstructor
public class GroupMapper {

    private final UserRepository userRepository;
    private final GroupTypeRepository groupTypeRepository;
    private final RoomRepository roomRepository;

    public Group toEntity(GroupCreateDto createDto){
        Group group = new Group();
        group.setName(createDto.getName());
        group.setActive(createDto.isActive());
        group.setStartTime(createDto.getStartTime());
        group.setEndTime(createDto.getEndTime());
        group.setStartedDate(createDto.getStartDate());
        group.setLinkForTelegram(createDto.getLinkOfTelegram());
        group.setStudent(createDto.isStudent());
        group.setPaymentAmount(createDto.getPaymentAmount());
        group.setTeacher(userRepository.findById(createDto.getTeacherId()).orElseThrow(
                () -> new NotFoundException(MessageService.getMessage(MessageKey.USER_NOT_FOUND))));
        group.setWeekDays(createDto.getWeekDays());
        group.setGroupType(groupTypeRepository.findById(createDto.getGroupTypeId()).orElseThrow(
                () -> new NotFoundException(MessageService.getMessage(MessageKey.NO_CONTENT))));
        group.setStudents(userRepository.findAllById(createDto.getStudents()));
        group.setRoom(roomRepository.findById(createDto.getRoom()).orElseThrow(
                () -> new NotFoundException(MessageService.getMessage(MessageKey.NO_CONTENT))));

        return group;
    }

    public GroupDto toDto(Group group){
        GroupDto groupDto = new GroupDto();
        groupDto.setId(group.getId());
        groupDto.setName(group.getName());
        groupDto.setActive(group.isActive());
        groupDto.setStartTime(group.getStartTime());
        groupDto.setEndTime(group.getEndTime());
        groupDto.setLinkOfTelegram(group.getLinkForTelegram());
        groupDto.setWeekDays(group.getWeekDays());
        groupDto.setTeacherId(group.getTeacher().getId());
        groupDto.setRoom(group.getRoom().getId());
        groupDto.setTotalStudents(group.getStudents().size());
        return groupDto;
    }


}
