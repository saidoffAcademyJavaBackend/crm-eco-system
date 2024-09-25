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
    public ResponseData<?> getAllNews(@PathVariable UUID userId,@RequestParam int page,@RequestParam int size) {
        return this.newsService.getAllNewsByUserRoles(userId,size,page);
    }

    @GetMapping("get-by-newsId")
    public ResponseData<?> getNewsById(@RequestParam UUID newsId) {
        return this.newsService.getByNewsId(newsId);
    }

    @PostMapping("add-news/{userId}")
    public ResponseData<?> addNews(@PathVariable UUID userId, @RequestBody NewsCreateDto news) {
        return this.newsService.addNews(userId,news);
    }

    @PutMapping("update-one-by-id/{userId}")
    public ResponseData<?> updateOneById(@RequestBody NewsUpdateDto newsUpdateDto, @PathVariable UUID userId) {
        return this.newsService.updateNews(newsUpdateDto,userId);
    }
}
