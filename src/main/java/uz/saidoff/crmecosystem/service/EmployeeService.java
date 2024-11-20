package uz.saidoff.crmecosystem.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import uz.saidoff.crmecosystem.entity.auth.User;
import uz.saidoff.crmecosystem.exception.NotFoundException;
import uz.saidoff.crmecosystem.mapper.EmployeeMapper;
import uz.saidoff.crmecosystem.payload.EmployeeCreatDto;
import uz.saidoff.crmecosystem.payload.EmployeeDto;
import uz.saidoff.crmecosystem.payload.EmployeeWarningDto;
import uz.saidoff.crmecosystem.repository.EmployeeRepository;
import uz.saidoff.crmecosystem.response.ResponseData;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final EmployeeMapper employeeMapper;

    public ResponseData<?> create(EmployeeCreatDto employeeCreatDto) {

        Boolean phoneNumber= employeeRepository.existsByPhoneNumber(employeeCreatDto.getPhoneNumber());
        if (phoneNumber) throw new NotFoundException("Phone number already exists");

        User user = this.employeeMapper.toEntity(employeeCreatDto);
        this.employeeRepository.save(user);

        return ResponseData.successResponse("success");
    }

    public ResponseData<?> update(UUID employeeId, EmployeeCreatDto employeeCreatDto) {
        Optional<User> userOptional = this.employeeRepository.findByIdAndDeletedFalse(employeeId);
        if (userOptional.isEmpty()) {
            throw new NotFoundException("Employee not found");
        }

        Boolean phoneNumber= employeeRepository.existsByPhoneNumber(employeeCreatDto.getPhoneNumber());
        if (phoneNumber) throw new NotFoundException("Phone number already exists");

        User user = userOptional.get();
        user=employeeMapper.updateEntity(user, employeeCreatDto);
        this.employeeRepository.save(user);
        return ResponseData.successResponse("success");
    }

    public ResponseData<EmployeeDto> getUser(UUID employeeId) {
        Optional<User> userOptional = this.employeeRepository.findByIdAndDeletedFalse(employeeId);
        if (userOptional.isEmpty()) {
            throw new NotFoundException("Employee not found");
        }
        return new ResponseData<>(employeeMapper.toDto(userOptional.get()),true);
    }

    public ResponseData<?> getAllUser(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<User> users = this.employeeRepository.findAllByDeletedFalse(pageable);
        if (users.isEmpty()) {
            throw new NotFoundException("No users found");
        }

        Map<String, Object> response = new HashMap<>();
        response.put("data", employeeMapper.toDto(users.toList()));
        response.put("total", users.getTotalElements());
        response.put("totalPages", users.getTotalPages());

        return new ResponseData<>(response, true);
    }

    public ResponseData<?> deleteEmployee(UUID employeeId) {
        Optional<User> userOptional = this.employeeRepository.findByIdAndDeletedFalse(employeeId);
        if (userOptional.isEmpty()) {
            throw new NotFoundException("Employee not found");
        }
        User user = userOptional.get();
        user.setDeleted(true);
        this.employeeRepository.save(user);
        return ResponseData.successResponse("delete success");
    }

    public ResponseData<?> warningAddAndSubtraction(UUID employeeId, EmployeeWarningDto employeeWarningDto) {
        Optional<User> userOptional = this.employeeRepository.findByIdAndDeletedFalse(employeeId);
        if (userOptional.isEmpty()) {
            throw new NotFoundException("Employee not found");
        }
        User user = userOptional.get();
        if (employeeWarningDto.isWarning()) user.setWarning(user.getWarning()+1);
        else user.setWarning(user.getWarning()-1);
        this.employeeRepository.save(user);
        return ResponseData.successResponse("success");
    }

}
