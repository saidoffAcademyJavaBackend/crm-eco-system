package uz.saidoff.crmecosystem.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.saidoff.crmecosystem.payload.QuestionCreateDto;
import uz.saidoff.crmecosystem.response.ResponseData;
import uz.saidoff.crmecosystem.service.QuestionService;
import uz.saidoff.crmecosystem.valid.CheckPermission;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class QuestionController {

    private final QuestionService questionService;

//    @CheckPermission("CREATE_QUESTION")
    @PostMapping("/create_question")
    public ResponseEntity<?> createQuestion(@RequestBody QuestionCreateDto questionDto) {

        ResponseData<?> question = questionService.createQuestion(questionDto);

        return ResponseEntity.ok(question);
    }
}
