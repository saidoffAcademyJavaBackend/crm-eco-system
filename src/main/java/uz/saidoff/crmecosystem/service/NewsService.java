package uz.saidoff.crmecosystem.service;

import uz.saidoff.crmecosystem.entity.News;
import uz.saidoff.crmecosystem.payload.NewsCreateDto;
import uz.saidoff.crmecosystem.payload.NewsUpdateDto;
import uz.saidoff.crmecosystem.response.ResponseData;

import java.util.UUID;

public interface NewsService {
    ResponseData<?> getAllNewsByUserRoles(UUID userId,int size, int page);

    ResponseData<?> addNews(UUID userId, NewsCreateDto news);

    ResponseData<?> updateNews(NewsUpdateDto newsUpdateDto, UUID userId);
}
