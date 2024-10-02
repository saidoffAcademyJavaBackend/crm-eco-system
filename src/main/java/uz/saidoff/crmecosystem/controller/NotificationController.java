package uz.saidoff.crmecosystem.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.saidoff.crmecosystem.service.NotificationService;
import uz.saidoff.crmecosystem.service.StudentService;
import uz.saidoff.crmecosystem.service.UserService;
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/notification")
public class NotificationController {

    private final NotificationService notificationService;

    private final StudentService studentService;

    private final UserService userService;




}
