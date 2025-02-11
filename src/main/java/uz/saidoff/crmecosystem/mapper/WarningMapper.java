package uz.saidoff.crmecosystem.mapper;

import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import uz.saidoff.crmecosystem.entity.Warning;
import uz.saidoff.crmecosystem.entity.auth.User;
import uz.saidoff.crmecosystem.exception.NotFoundException;
import uz.saidoff.crmecosystem.payload.WarningDTO;
import uz.saidoff.crmecosystem.repository.UserRepository;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class WarningMapper {

    private final UserRepository userRepository;

    public WarningMapper(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Warning toWarning(WarningDTO dto) {
        User user = userRepository.findByIdAndDeletedFalse(dto.getUserId()).orElseThrow(()
                -> new NotFoundException("user not found"));
        Warning warning = new Warning();
        warning.setReason(dto.getReason());
        warning.setUser(user);
        warning.setDeleted(dto.isActive());
        return warning;
    }

    public WarningDTO toWarningDTO(Warning warning) {
        WarningDTO dto = new WarningDTO();
        dto.setId(warning.getId());
        dto.setReason(warning.getReason());
        dto.setUserId(warning.getUser().getId());
        dto.setActive(warning.isDeleted());
        return dto;
    }

    public WarningDTO toWarningGetDTO(Warning warning) {
        WarningDTO dto = new WarningDTO();
        dto.setId(warning.getId());
        dto.setReason(warning.getReason());
        dto.setActive(warning.isPunishment());
        dto.setActive(warning.isDeleted());
        return dto;
    }
    public WarningDTO toPunishmentDTO(Warning warning) {
        WarningDTO dto = new WarningDTO();
        dto.setId(warning.getId());
        dto.setReason(warning.getReason());
        dto.setUserId(warning.getUser().getId());
        return dto;
    }

    public List<WarningDTO> toWarningDTO(Page<Warning> warnings) {
        List<WarningDTO> dtos = new ArrayList<>();
        for (Warning warning : warnings) {
            dtos.add(toWarningDTO(warning));
        }
        return dtos;
    }

    public List<WarningDTO> toPunishmentDTOList(Page<Warning> warnings) {
        List<WarningDTO> dtos = new ArrayList<>();
        for (Warning warning : warnings) {
            dtos.add(toPunishmentDTO(warning));
        }
        return dtos;
    }
}
