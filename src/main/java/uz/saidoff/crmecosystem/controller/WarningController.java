package uz.saidoff.crmecosystem.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.saidoff.crmecosystem.entity.Warning;
import uz.saidoff.crmecosystem.payload.WarningDTO;
import uz.saidoff.crmecosystem.response.ResponseData;
import uz.saidoff.crmecosystem.service.WarningService;
import uz.saidoff.crmecosystem.valid.CheckPermission;

import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/warnings")
public class WarningController {

    private final WarningService warningService;


    @CheckPermission("ADD_WARNING")
    @PostMapping("/addWarning/{userId}")
    public HttpEntity<?> addWarning(@PathVariable("userId") UUID userId,
                                    @RequestParam String reason) {
        ResponseData<?> responseData = warningService.addWarning(userId, reason);
        return ResponseEntity.ok(responseData);
    }

    @CheckPermission("GET_ALL_WARNINGS")
    @GetMapping("/getWarnings")
    public HttpEntity<?> getWarnings() {
        ResponseData<?> warnings = warningService.getWarnings();
        return ResponseEntity.ok(warnings);
    }

    @CheckPermission("GET_WARNING")
    @GetMapping("/getWarning/{warningId}")
    public HttpEntity<?> getWarning(@PathVariable(name = "warningId") UUID warningId) {
        ResponseData<WarningDTO> warning = warningService.getWarning(warningId);
        return ResponseEntity.ok(warning);
    }

    @CheckPermission("GET_ALL_WARNINGS")
    @GetMapping("/getPunishments")
    public HttpEntity<?> getPunishments() {
        ResponseData<?> punishments = warningService.getPunishments();
        return ResponseEntity.ok(punishments);
    }

    @CheckPermission("GET_WARNING")
    @GetMapping("/getWarningsByUserId/{userId}")
    public HttpEntity<?> getWarningsByUserId(@PathVariable(name = "userId") UUID userId) {
        ResponseData<?> allWarningsByUserId = warningService.getAllWarningsByUserId(userId);
        return ResponseEntity.ok(allWarningsByUserId);
    }
}
