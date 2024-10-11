package uz.saidoff.crmecosystem.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import uz.saidoff.crmecosystem.entity.Speciality;
import uz.saidoff.crmecosystem.exception.AlreadyExistException;
import uz.saidoff.crmecosystem.exception.NotFoundException;
import uz.saidoff.crmecosystem.mapper.SpecialityMapper;
import uz.saidoff.crmecosystem.payload.SpecialityCreatDto;
import uz.saidoff.crmecosystem.repository.SpecialityRepository;
import uz.saidoff.crmecosystem.response.ResponseData;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SpecialityService {

    private final SpecialityRepository specialityRepository;
    private final SpecialityMapper specialityMapper;

    public ResponseData<?> create(SpecialityCreatDto specialityCreatDto){
         Boolean exists = specialityRepository.existsByNameAndDeletedFalse(specialityCreatDto.getName());
         if (exists) {
             throw new AlreadyExistException("Special name already exists");
         }
         Speciality speciality = this.specialityMapper.toEntity(specialityCreatDto);
         specialityRepository.save(speciality);

         return ResponseData.successResponse(this.specialityMapper.toDto(speciality));
    }

    public ResponseData<?> get(UUID specialityId) {
        Optional<Speciality> speciality = specialityRepository.findByIdAndDeletedFalse(specialityId);
        if (speciality.isEmpty()) {
            throw new NotFoundException("Special not found");
        }
        return ResponseData.successResponse(this.specialityMapper.toDto(speciality.get()));
    }

    public ResponseData<?> update(UUID specialityId, SpecialityCreatDto specialityCreatDto) {
        Optional<Speciality> specialityOptional = specialityRepository.findByIdAndDeletedFalse(specialityId);
        if (specialityOptional.isEmpty()) {
            throw new NotFoundException("Special not found");
        }
        Speciality speciality = this.specialityMapper.updateEntity(specialityOptional.get(), specialityCreatDto);
        this.specialityRepository.save(speciality);
        return ResponseData.successResponse("Special updated");
    }

    public ResponseData<?> getAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Speciality> specialityPage = specialityRepository.findAllAndDeletedFalse(pageable);
        if (specialityPage.isEmpty()){
            throw new NotFoundException("Special not found");
        }

        Map<String, Object> response = new HashMap<>();
        response.put("data", this.specialityMapper.toDto(specialityPage.toList()));
        response.put("total", specialityPage.getTotalElements());
        response.put("totalPages", specialityPage.getTotalPages());

        return ResponseData.successResponse(response);
    }

    public ResponseData<?> delete(UUID specialityId) {
        Optional<Speciality> specialityOptional = specialityRepository.findByIdAndDeletedFalse(specialityId);
        if (specialityOptional.isEmpty()) {
            throw new NotFoundException("Special not found");
        }
        Speciality speciality = specialityOptional.get();
        speciality.setDeleted(true);
        specialityRepository.save(speciality);
        return ResponseData.successResponse("Special deleted");
    }

}
