package uz.saidoff.crmecosystem.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import uz.saidoff.crmecosystem.entity.Attachment;
import uz.saidoff.crmecosystem.entity.News;
import uz.saidoff.crmecosystem.entity.auth.Role;
import uz.saidoff.crmecosystem.entity.auth.User;
import uz.saidoff.crmecosystem.payload.NewsCreateDto;
import uz.saidoff.crmecosystem.payload.NewsGetByUserIdDto;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class NewsMapper {
    public NewsGetByUserIdDto toNewsGetByUserId(News news) {
        NewsGetByUserIdDto newsGetByUserIdDto = new NewsGetByUserIdDto();
        newsGetByUserIdDto.setId(news.getId());
        newsGetByUserIdDto.setTitle(news.getTitle());
        newsGetByUserIdDto.setContent(news.getContent());
        newsGetByUserIdDto.setAttachmentId(news.getAttachment().getId());
        newsGetByUserIdDto.setRoleType(news.getRoles().stream().map(Role::getRoleType).toList());
        newsGetByUserIdDto.setCreatedBy(news.getCreatedBy());
        newsGetByUserIdDto.setCreatedDate(news.getCreatedAt());
        return newsGetByUserIdDto;
    }

    public News fromNewsCreateDtoToNews(NewsCreateDto newsCreateDto, List<Role> roles, Attachment attachment) {
        News news = new News();
        news.setTitle(newsCreateDto.getTitle());
        news.setContent(newsCreateDto.getContent());
        news.setRoles(roles);
        news.setDeleted(false);
        return news;
    }
}
