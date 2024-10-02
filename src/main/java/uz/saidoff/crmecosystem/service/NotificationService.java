package uz.saidoff.crmecosystem.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.saidoff.crmecosystem.entity.Notification;
import uz.saidoff.crmecosystem.exception.NotFoundException;
import uz.saidoff.crmecosystem.mapper.NotificationMapper;
import uz.saidoff.crmecosystem.payload.NotificationDto;
import uz.saidoff.crmecosystem.repository.NotificationRepository;
import uz.saidoff.crmecosystem.response.ResponseData;
import uz.saidoff.crmecosystem.util.MessageKey;
import uz.saidoff.crmecosystem.util.MessageService;

import java.util.UUID;

@Service
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final NotificationMapper notificationMapper;

    public NotificationService(NotificationRepository notificationRepository, NotificationMapper notificationMapper) {
        this.notificationRepository = notificationRepository;
        this.notificationMapper = notificationMapper;
    }

    public ResponseData<NotificationDto> getId(UUID id) {
        Notification notification = notificationRepository.findById(id).orElseThrow(
                () -> new NotFoundException(MessageService.getMessage(MessageKey.NOTIFICATION_NOT_FOUND)));
        return new ResponseData<>();
    }

    public ResponseData<NotificationDto> getByReadIsFalse(UUID userId){
        return new ResponseData<>();
    }
    public ResponseData<String> notificationDelete(UUID userId) {
        return ResponseData.successResponse("Success");
    }
}
