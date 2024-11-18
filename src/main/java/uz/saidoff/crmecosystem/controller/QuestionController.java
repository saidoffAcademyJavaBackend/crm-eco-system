package uz.saidoff.crmecosystem.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.saidoff.crmecosystem.payload.QuestionCreateDto;
import uz.saidoff.crmecosystem.payload.QuestionSetDatesDto;
import uz.saidoff.crmecosystem.response.ResponseData;
import uz.saidoff.crmecosystem.service.QuestionService;
import uz.saidoff.crmecosystem.valid.CheckPermission;

import java.util.UUID;

@RestController
@RequestMapping("/api/questions")
@RequiredArgsConstructor
public class QuestionController {

    private final QuestionService questionService;

    @CheckPermission("CREATE_TASK")
    @PostMapping("/create")
    public ResponseEntity<?> createQuestion(@RequestBody QuestionCreateDto questionDto) {

        ResponseData<?> question = questionService.createQuestion(questionDto);

        return ResponseEntity.ok(question);
    }

    @CheckPermission("UPDATE_TASK")
    @PutMapping("/update/value")
    public ResponseEntity<?> updateQuestion(@RequestBody QuestionCreateDto questionDto) {

        ResponseData<?> updateQuestion = questionService.updateQuestion(questionDto);

        return ResponseEntity.ok(updateQuestion);
    }

    @CheckPermission("UPDATE_TASK")
    @PutMapping("/update/date")
    public ResponseEntity<?> updateQuestionsDate(@RequestBody QuestionSetDatesDto questionSetDatesDto) {

        ResponseData<?> setDate = questionService.setDates(questionSetDatesDto);

        return ResponseEntity.ok(setDate);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<?> getQuestion(@RequestParam UUID id) {

        ResponseData<?> getQuestion =  questionService.getQuestion(id);

        return ResponseEntity.ok(getQuestion);
    }

    @GetMapping("/get_all")
    public ResponseEntity<?> getAllQuestions() {

        ResponseData<?> getAllQuestions =  questionService.getAllQuestions();

        return ResponseEntity.ok(getAllQuestions);
    }
}
