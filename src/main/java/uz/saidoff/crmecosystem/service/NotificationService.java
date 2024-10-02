package uz.saidoff.crmecosystem.service;

import org.springframework.stereotype.Service;
import uz.saidoff.crmecosystem.entity.Notification;
import uz.saidoff.crmecosystem.entity.auth.Role;
import uz.saidoff.crmecosystem.entity.auth.User;
import uz.saidoff.crmecosystem.exception.NotFoundException;
import uz.saidoff.crmecosystem.mapper.NotificationMapper;
import uz.saidoff.crmecosystem.payload.NotificationDto;
import uz.saidoff.crmecosystem.repository.NotificationRepository;
import uz.saidoff.crmecosystem.repository.UserRepository;
import uz.saidoff.crmecosystem.response.ResponseData;
import uz.saidoff.crmecosystem.util.MessageKey;
import uz.saidoff.crmecosystem.util.MessageService;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final NotificationMapper notificationMapper;
    private final UserRepository userRepository;

    public NotificationService(
            NotificationRepository notificationRepository,
            NotificationMapper notificationMapper,
            UserRepository userRepository) {
        this.notificationRepository = notificationRepository;
        this.notificationMapper = notificationMapper;
        this.userRepository = userRepository;
    }

    public ResponseData<NotificationDto> getId(UUID id) {
        Notification notification = notificationRepository.findById(id).orElseThrow(
                () -> new NotFoundException(MessageService.getMessage(MessageKey.NOTIFICATION_NOT_FOUND)));
        return new ResponseData<>();
    }

    public ResponseData<NotificationDto> getByReadIsFalse(UUID userId) {
        return new ResponseData<>();
    }

    public ResponseData<String> notificationDelete(UUID userId) {
        return ResponseData.successResponse("Success");
    }

    List<NotificationDto> notificationDtoList = new ArrayList<>();

    public List<NotificationDto> sendMessage(UUID userId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isEmpty()) {
            throw new NotFoundException(MessageService.getMessage(MessageKey.NOTIFICATION_NOT_FOUND));
        }
        Role role = optionalUser.get().getRole();
        List<User> byRoleName = userRepository.findByRole_Name(role.getName());
        for (User user : byRoleName) {
            Notification notification = new Notification();
            notification.setTitle(notification.getTitle());
            notification.setDescription(notification.getDescription());
            notification.setUser(user);
            notification.setRead(false);
            notification.setReceivedTime(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES));
            notification.setType(notification.getType());
            Notification savedNotification = notificationRepository.save(notification);
            NotificationDto notDto = notificationMapper.toNotDto(savedNotification);
            notificationDtoList.add(notDto);
        }
        return notificationDtoList;
    }

//    public List<Notification> getUnsentNotifications(UUID userId) {
//        List<Notification> readIsFalse = notificationRepository.findAllByUserIdAndReadIsFalse(userId);
//
//
//    }

    public ResponseData<?> getNotificationById(UUID id) {
        Notification notification = notificationRepository.findById(id).orElseThrow(() -> new NotFoundException("Notification not found"));
        NotificationDto notDto = notificationMapper.toNotDto(notification);
        return new ResponseData<>(notDto, true);
    }
}
