package uz.saidoff.crmecosystem.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.saidoff.crmecosystem.entity.Attachment;
import uz.saidoff.crmecosystem.entity.News;
import uz.saidoff.crmecosystem.entity.auth.Role;
import uz.saidoff.crmecosystem.entity.auth.User;
import uz.saidoff.crmecosystem.exception.NotFoundException;
import uz.saidoff.crmecosystem.mapper.NewsMapper;
import uz.saidoff.crmecosystem.payload.NewsCreateDto;
import uz.saidoff.crmecosystem.payload.NewsGetByUserIdDto;
import uz.saidoff.crmecosystem.payload.NewsUpdateDto;
import uz.saidoff.crmecosystem.repository.AttachmentRepository;
import uz.saidoff.crmecosystem.repository.NewsRepository;
import uz.saidoff.crmecosystem.repository.RoleRepository;
import uz.saidoff.crmecosystem.repository.UserRepository;
import uz.saidoff.crmecosystem.response.ResponseData;
import uz.saidoff.crmecosystem.service.NewsService;
import uz.saidoff.crmecosystem.util.MessageKey;
import uz.saidoff.crmecosystem.util.MessageService;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class NewsServiceImpl implements NewsService {
    private final NewsMapper newsMapper;
    private final NewsRepository newsRepository;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final AttachmentRepository fileRepository;

    @Override
    public ResponseData<?> getAllNewsByUserRoles(UUID userId, int size, int page) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isEmpty()) {
            throw new NotFoundException(MessageService.getMessage(MessageKey.USER_NOT_FOUND));
        }
        Role role = optionalUser.get().getRole();
        List<News> news = newsRepository.findByRolesAndNewsId(role.getRoleType().name(), size, page * size);
        if (news.isEmpty()) {
            throw new NotFoundException(MessageService.getMessage(MessageKey.NO_CONTENT));
        }
        List<NewsGetByUserIdDto> list = news.stream().map(newsMapper::toNewsGetByUserId).toList();
        return ResponseData.successResponse(list);
    }

    @Override
    public ResponseData<?> addNews(UUID userId, NewsCreateDto newsCreateDto) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isEmpty()) {
            throw new NotFoundException(MessageService.getMessage(MessageKey.USER_NOT_FOUND));
        }
        List<Role> roles = roleRepository.findAllById(newsCreateDto.getRoleId());
        if (roles.isEmpty()) {
            throw new NotFoundException("Role not found");
        }
        Optional<Attachment> optionalAttachment = fileRepository.findById(newsCreateDto.getAttachmentId());
        if (optionalAttachment.isEmpty()) {
            throw new NotFoundException("attachment not found");
        }
        News news = newsMapper.fromNewsCreateDtoToNews(optionalUser.get(), newsCreateDto, roles,optionalAttachment.get());
        newsRepository.save(news);
        return ResponseData.successResponse("News added successfully");
    }

    @Override
    public ResponseData<?> updateNews(NewsUpdateDto newsUpdateDto, UUID userId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isEmpty()) {
            throw new NotFoundException(MessageService.getMessage(MessageKey.USER_NOT_FOUND));
        }
        Optional<News> byId = newsRepository.findById(newsUpdateDto.getNewsId());
        if (byId.isEmpty()) {
            throw new NotFoundException(MessageService.getMessage(MessageKey.NO_CONTENT));
        }
        Optional<Attachment> optionalAttachment = fileRepository.findById(newsUpdateDto.getAttachmentId());
        if (optionalAttachment.isEmpty()) {
            throw new NotFoundException("attachment not found");
        }
        News news = byId.get();
        news.setUpdatedBy(userId);
        news.setContent(newsUpdateDto.getContent());
        news.setTitle(newsUpdateDto.getTitle());
        news.setUpdatedAt(Timestamp.from(Instant.now()));
        news.setAttachment(optionalAttachment.get());
        if (newsUpdateDto.getRoleId() != null) {
            List<Role> allById = roleRepository.findAllById(newsUpdateDto.getRoleId());
            if (allById.isEmpty()) {
                throw new NotFoundException("Role not found");
            }
            news.setRoles(allById);
        }
        newsRepository.save(news);
        return ResponseData.successResponse("News updated successfully");
    }

    @Override
    public ResponseData<?> getByNewsId(UUID newsId) {
        Optional<News> optionalNews = newsRepository.findById(newsId);
        if (optionalNews.isEmpty()) {
            throw new NotFoundException(MessageService.getMessage(MessageKey.NO_CONTENT));
        }
        return ResponseData.successResponse(newsMapper.toNewsGetByUserId(optionalNews.get()));
    }
}
