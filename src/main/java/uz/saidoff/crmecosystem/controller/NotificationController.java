package uz.saidoff.crmecosystem.controller;

import io.swagger.annotations.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.saidoff.crmecosystem.payload.NotificationDto;
import uz.saidoff.crmecosystem.response.ResponseData;
import uz.saidoff.crmecosystem.service.NotificationService;
import uz.saidoff.crmecosystem.service.StudentService;
import uz.saidoff.crmecosystem.service.UserService;
import uz.saidoff.crmecosystem.util.MessageKey;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/notification")
public class NotificationController {

    private final NotificationService notificationService;

    @GetMapping("/")
    public ResponseData<?> getNotification() {
        return ResponseData.successResponse(notificationService.getNotification());
    }

    @PostMapping("read-notifications/")
    public HttpEntity<?> readUserNotifications(@RequestBody List<UUID> notifications) {
        notificationService.readNotifications(notifications);
        return ResponseEntity.ok(new ResponseData<>(MessageKey.READ_NOTIFICATIONS,true));
    }


}
