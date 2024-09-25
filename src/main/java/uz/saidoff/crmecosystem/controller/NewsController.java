package uz.saidoff.crmecosystem.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import uz.saidoff.crmecosystem.entity.News;
import uz.saidoff.crmecosystem.payload.NewsCreateDto;
import uz.saidoff.crmecosystem.payload.NewsUpdateDto;
import uz.saidoff.crmecosystem.response.ResponseData;
import uz.saidoff.crmecosystem.service.NewsService;

import java.util.UUID;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/news")
public class NewsController {
    private final NewsService newsService;

    @GetMapping("get-allNews-by-userId/{userId}")
    public ResponseData<?> getAllNews(@PathVariable UUID userId) {
        return this.newsService.getAllNewsByUserRoles(userId);
    }

    @PostMapping("add-news/{userId}")
    public ResponseData<?> addNews(@PathVariable UUID userId, @RequestBody NewsCreateDto news) {
        return this.newsService.addNews(userId,news);
    }

    @PutMapping("update-one-by-id/{newsId}/{userId}")
    public ResponseData<?> updateOneById(@RequestBody NewsUpdateDto newsUpdateDto, @PathVariable UUID userId) {
        return this.newsService.updateNews(newsUpdateDto,userId);
    }
}
