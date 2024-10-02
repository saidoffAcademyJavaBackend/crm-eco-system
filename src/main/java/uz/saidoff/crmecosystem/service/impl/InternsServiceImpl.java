package uz.saidoff.crmecosystem.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import uz.saidoff.crmecosystem.entity.Speciality;
import uz.saidoff.crmecosystem.entity.auth.Role;
import uz.saidoff.crmecosystem.entity.auth.User;
import uz.saidoff.crmecosystem.enums.RoleType;
import uz.saidoff.crmecosystem.exception.NotFoundException;
import uz.saidoff.crmecosystem.mapper.InternsMapper;
import uz.saidoff.crmecosystem.payload.InternGetDto;
import uz.saidoff.crmecosystem.repository.InternsRepository;
import uz.saidoff.crmecosystem.repository.RoleRepository;
import uz.saidoff.crmecosystem.repository.SpecialityRepository;
import uz.saidoff.crmecosystem.repository.UserRepository;
import uz.saidoff.crmecosystem.response.ResponseData;
import uz.saidoff.crmecosystem.service.InternsService;

import java.util.*;

@Service
@RequiredArgsConstructor
public class InternsServiceImpl implements InternsService {
    private final InternsRepository internsRepository;
    private final InternsMapper internsMapper;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final SpecialityRepository specialityRepository;

    @Override
    public ResponseData<?> getAllInterns(int page, int size) {
        Optional<Role> optionalRole = roleRepository.findByRoleType(RoleType.INTERN);
        if(optionalRole.isEmpty()){
            throw new NotFoundException("Role not found");
        }
//        Pageable pageable = PageRequest.of(page, size);
//        Page<User> interns = internsRepository.findAllInternsPageable("INTERN", pageable);
        Page<User> interns = internsRepository.findAllByRoleAndDeletedFalse(optionalRole.get(), PageRequest.of(page, size));
        if (interns.isEmpty()) {
            throw new NotFoundException("Interns not found");
        }
        List<InternGetDto> list = interns.get().map(internsMapper::toInternGetDto).toList();
        Map<String, Object> result = new HashMap<>();
        result.put("date", list);
        result.put("total", interns.getTotalElements());
        result.put("TotalPages", interns.getTotalPages());
        return ResponseData.successResponse(result, userId);
    }

    @Override
    public ResponseData<?> getOneById(UUID interId) {
        Optional<User> optionalUser = internsRepository.findById(interId);
        if (optionalUser.isEmpty()) {
            throw new NotFoundException("User not found");
        }
        InternGetDto internGetDto = internsMapper.toInternGetDto(optionalUser.get());
        return ResponseData.successResponse(internGetDto, userId);
    }

    @Override
    public ResponseData<?> addIntern(UUID userId, InternGetDto internGetDto) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isEmpty()) {
            throw new NotFoundException("User not found");
        }
        Optional<Speciality> optionalSpeciality = specialityRepository.findByName(internGetDto.getSpecialty());
        if (optionalSpeciality.isEmpty()) {
            throw new NotFoundException("Speciality not found");
        }
        Optional<Role> optionalRole = roleRepository.findByRoleType(RoleType.INTERN);
        if (optionalRole.isEmpty()) {
            throw new NotFoundException("Role not found to set as an Intern");
        }
        User user = internsMapper.toUser(userId, internGetDto,optionalSpeciality.get(),optionalRole.get());
        internsRepository.save(user);
        return ResponseData.successResponse("intern added successfully");
    }
}
