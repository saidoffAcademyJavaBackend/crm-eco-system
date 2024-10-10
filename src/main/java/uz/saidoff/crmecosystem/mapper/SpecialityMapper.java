package uz.saidoff.crmecosystem.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import uz.saidoff.crmecosystem.entity.Speciality;
import uz.saidoff.crmecosystem.payload.SpecialityCreatDto;
import uz.saidoff.crmecosystem.payload.SpecialityDto;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class SpecialityMapper {

    public Speciality toEntity(SpecialityCreatDto specialityCreatDto){
        Speciality speciality = new Speciality();
        speciality.setName(specialityCreatDto.getName());
        speciality.setDescription(specialityCreatDto.getDescription());
        return speciality;
    }

    public SpecialityDto toDto(Speciality speciality){
        SpecialityDto specialityDto = new SpecialityDto();
        specialityDto.setName(speciality.getName());
        specialityDto.setDescription(speciality.getDescription());
        return specialityDto;
    }

    public Speciality updateEntity(Speciality speciality, SpecialityCreatDto specialityCreatDto){
        if (specialityCreatDto.getName()!=null) speciality.setName(specialityCreatDto.getName());
        if (specialityCreatDto.getName()!=null) speciality.setDescription(specialityCreatDto.getDescription());
        return speciality;
    }

    public List<SpecialityDto> toDto(List<Speciality> specialities){
        List<SpecialityDto> specialityDto = new ArrayList<>();
        for (Speciality speciality : specialities){
            specialityDto.add(toDto(speciality));
        }
        return specialityDto;
    }



}
