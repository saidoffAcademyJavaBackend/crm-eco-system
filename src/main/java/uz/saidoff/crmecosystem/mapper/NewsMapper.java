package uz.saidoff.crmecosystem.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
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
        newsGetByUserIdDto.setRoleType(news.getRoles().get(0).getRoleType().toString());
        newsGetByUserIdDto.setCreatedBy(news.getCreatedBy());
        newsGetByUserIdDto.setCreatedDate(news.getCreatedAt());
        return newsGetByUserIdDto;
    }

    public News fromNewsCreateDtoToNews(User optionalUser, NewsCreateDto newsCreateDto, List<Role> roles) {
        News news = new News();
        news.setAttachmentId(newsCreateDto.getAttachmentId());
        news.setTitle(newsCreateDto.getTitle());
        news.setContent(newsCreateDto.getContent());
        news.setRoles(roles);
        news.setCreatedBy(optionalUser.getId());
        news.setDeleted(false);
        news.setCreatedAt(Timestamp.from(Instant.now()));
        news.setUpdatedAt(Timestamp.from(Instant.now()));
        news.setUpdatedBy(optionalUser.getId());
        return news;
    }
}
