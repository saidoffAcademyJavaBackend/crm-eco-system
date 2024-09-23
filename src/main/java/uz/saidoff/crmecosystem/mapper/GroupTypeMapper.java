package uz.saidoff.crmecosystem.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import uz.saidoff.crmecosystem.entity.GroupType;
import uz.saidoff.crmecosystem.payload.GroupTypeCreateDto;
import uz.saidoff.crmecosystem.payload.GroupTypeDto;
import uz.saidoff.crmecosystem.repository.GroupTypeRepository;

@Component
@RequiredArgsConstructor
public class GroupTypeMapper {

    private final GroupTypeRepository groupTypeRepository;

    public GroupType toEntity(GroupTypeCreateDto createDto){
        GroupType groupType = new GroupType();
        groupType.setSpecialty(createDto.getSpecialty());
        groupType.setDescription(createDto.getDescription());

        return groupType;
    }
    public GroupTypeDto toDto(GroupType groupType){
        GroupTypeDto groupTypeDto = new GroupTypeDto();
        groupTypeDto.setId(groupType.getId());
        groupTypeDto.setSpecialty(groupType.getSpecialty());
        groupTypeDto.setDescription(groupType.getDescription());
        return groupTypeDto;
    }
}
