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

    private final StudentService studentService;

    private final UserService userService;

    @GetMapping
    public ResponseData<List<NotificationDto>> sendNotMessage(@RequestBody NotificationDto dto, UUID userId){
        List<NotificationDto> notificationDtos = notificationService.sendMessage(dto, userId);
        return ResponseData.successResponse(notificationDtos);
    }

    @GetMapping("/{id}")
    public ResponseData<?> getNotificationById(@PathVariable UUID id){
        return this.notificationService.getNotificationById(id);
    }




}
