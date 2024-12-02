package uz.saidoff.crmecosystem.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
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

    @GetMapping("get-all-news-by-user")
    public ResponseData<?> getAllNews(@RequestParam int page, @RequestParam int size) {
        return this.newsService.getAllNewsByUserRoles(size, page);
    }

    @GetMapping("get-by-newsId/{newsId}")
    public ResponseData<?> getNewsById(@PathVariable UUID newsId) {
        return this.newsService.getByNewsId(newsId);
    }

    @PostMapping("add-news")
    public ResponseData<?> addNews(@RequestBody NewsCreateDto news) {
        return this.newsService.addNews(news);
    }

    @PutMapping("update-one-by-id")
    public ResponseData<?> updateOneById(@RequestBody NewsUpdateDto newsUpdateDto) {
        return this.newsService.updateNews(newsUpdateDto);
    }
}
