package uz.saidoff.crmecosystem.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.saidoff.crmecosystem.payload.QuestionCreateDto;
import uz.saidoff.crmecosystem.response.ResponseData;
import uz.saidoff.crmecosystem.service.QuestionService;
import uz.saidoff.crmecosystem.valid.CheckPermission;

import java.util.UUID;

@RestController
@RequestMapping("/api/questions")
@RequiredArgsConstructor
public class QuestionController {

    private final QuestionService questionService;

//    @CheckPermission("CREATE_TASK")
    @PostMapping("")
    public ResponseEntity<?> createQuestion(@RequestBody QuestionCreateDto questionDto) {

        ResponseData<?> question = questionService.createQuestion(questionDto);

        return ResponseEntity.ok(question);
    }

//    @CheckPermission("UPDATE_TASK")
    @PutMapping("")
    public ResponseEntity<?> updateQuestion(@RequestBody QuestionCreateDto questionDto) {

        ResponseData<?> question = questionService.updateQuestion(questionDto);

        return ResponseEntity.ok(question);
    }

//    @CheckPermission("GET_TASK")
    @GetMapping("/all")
    public ResponseEntity<?> getAllQuestions() {

        ResponseData<?> question = questionService.getAllQuestions();

        return ResponseEntity.ok(question);
    }

//    @CheckPermission("GET_TASK")
    @GetMapping("/{id}")
    public ResponseEntity<?> getQuestion(@PathVariable UUID id) {

        ResponseData<?> question = questionService.getQuestionById(id);

        return ResponseEntity.ok(question);
    }

    @GetMapping("/for_student/{id}")
    public ResponseEntity<?> getQuestionById(@PathVariable UUID id) {

        ResponseData<?> question = questionService.getQuestionForStudents(id);

        return ResponseEntity.ok(question);
    }

//    @CheckPermission("DELETE_TASK")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteQuestionById(@PathVariable UUID id) {

        ResponseData<?> question = questionService.deleteQuestion(id);

        return ResponseEntity.ok(question);
    }
}
