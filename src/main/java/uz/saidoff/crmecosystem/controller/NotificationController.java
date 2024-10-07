package uz.saidoff.crmecosystem.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import uz.saidoff.crmecosystem.payload.NotificationDto;
import uz.saidoff.crmecosystem.response.ResponseData;
import uz.saidoff.crmecosystem.service.NotificationService;
import uz.saidoff.crmecosystem.service.StudentService;
import uz.saidoff.crmecosystem.service.UserService;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/notification")
public class NotificationController {

    private final NotificationService notificationService;

    @GetMapping("/{userId}")
    public ResponseData<?> getNotification(@PathVariable("userId") UUID userId) {
        return ResponseData.successResponse(notificationService.getNotification(userId));
    }
    @GetMapping("/{userId}")
    public ResponseData<?> getNotificationByUserId(@PathVariable("userId") UUID userId) {
        return ResponseData.successResponse(notificationService.getNotificationByUserId(userId));
    }

    @DeleteMapping("/{userId}")
    public ResponseData<?> deleteNotification(@PathVariable("userId") UUID userId) {
        notificationService.deleteAllReadIsTrueNotifications(userId);
        return new ResponseData<>(true);
    }

}
