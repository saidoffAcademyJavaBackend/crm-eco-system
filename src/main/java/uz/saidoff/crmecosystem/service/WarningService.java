package uz.saidoff.crmecosystem.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.saidoff.crmecosystem.entity.Warning;
import uz.saidoff.crmecosystem.entity.auth.User;
import uz.saidoff.crmecosystem.exception.NotFoundException;
import uz.saidoff.crmecosystem.mapper.WarningMapper;
import uz.saidoff.crmecosystem.payload.WarningDTO;
import uz.saidoff.crmecosystem.repository.UserRepository;
import uz.saidoff.crmecosystem.repository.WarningRepository;
import uz.saidoff.crmecosystem.response.ResponseData;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class WarningService {

    private final WarningRepository warningRepository;
    private final WarningMapper warningMapper;
    private final UserRepository userRepository;


    @Transactional
    public ResponseData<?> addWarning(UUID userId, String reason) {
        User user = userRepository.findByIdAndDeletedFalse(userId).orElseThrow(
                () -> new NotFoundException("User not found"));
        List<Warning> warnings = user.getWarnings();
        if ((warnings.size() + 1) % 3 == 0) {

            Warning war = new Warning();
            war.setReason(reason);
            war.setPunishment(true);
            war.setWarningCount(warnings.size() + 1);
            war.setUser(user);

            warnings
                    .stream()
                    .skip(Math.max(0, warnings.size() - 2))
                    .forEach(warning -> warning.setDeleted(true));
            warningRepository.saveAll(warnings);
            warnings.add(war);

        } else {
            Warning warning = Warning
                    .builder()
                    .reason(reason)
                    .warningCount(warnings.size() + 1)
                    .user(user)
                    .build();

            warningRepository.save(warning);
            user.getWarnings().add(warning);
            userRepository.save(user);
        }
        return new ResponseData<>("Warning has been added", true);
    }

    public ResponseData<List<WarningDTO>> getWarnings(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Warning> warnings = warningRepository.findAll(pageable);
        List<WarningDTO> warningDTO = warningMapper.toWarningDTO(warnings);
        return new ResponseData<>(warningDTO, true);
    }

    public ResponseData<WarningDTO> getWarning(UUID warningId) {
        Warning warning = warningRepository.findById(warningId).orElseThrow(()
                -> new NotFoundException("Warning not found"));
        WarningDTO warningDTO = warningMapper.toWarningDTO(warning);
        return new ResponseData<>(warningDTO, true);
    }

    public ResponseData<List<WarningDTO>> getPunishments(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Warning> warningList = warningRepository.findAllByPunishmentIsTrue(pageable);
        List<WarningDTO> warningDTO = warningMapper.toPunishmentDTOList(warningList);
        return new ResponseData<>(warningDTO, true);
    }

    public ResponseData<?> getAllWarningsByUserId(UUID userId) {
        List<Warning> warningList = warningRepository.findAllByUserId(userId);
        if (warningList.isEmpty()) {
            throw new NotFoundException("there is no any warnings");
        }
        List<WarningDTO> warningDTOList = warningList
                .stream()
                .map(warningMapper::toWarningGetDTO)
                .toList();
        return new ResponseData<>(warningDTOList, true);
    }
}
