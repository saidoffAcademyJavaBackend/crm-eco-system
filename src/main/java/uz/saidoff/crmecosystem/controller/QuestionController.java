package uz.saidoff.crmecosystem.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.saidoff.crmecosystem.payload.QuestionCreateDto;
import uz.saidoff.crmecosystem.response.ResponseData;
import uz.saidoff.crmecosystem.service.QuestionService;

import java.util.UUID;

@RestController
@RequestMapping("/api/questions")
@RequiredArgsConstructor
public class QuestionController {

    private final QuestionService questionService;

    /**
     * CREATE QUESTIONNAIRE
     * @param questionDto, QuestionCreateDto
     * @return ResponseEntity.ok
     */

    @PostMapping("")
    public ResponseEntity<?> createQuestion(@RequestBody QuestionCreateDto questionDto) {

        ResponseData<?> question = questionService.createQuestion(questionDto);

        return ResponseEntity.ok(question);
    }

    /**
     * UPDATE QUESTIONNAIRE
     * @param questionDto, QuestionCreateDto
     * @return ResponseEntity.ok
     */

    @PutMapping("")
    public ResponseEntity<?> updateQuestion(@RequestBody QuestionCreateDto questionDto) {

        ResponseData<?> question = questionService.updateQuestion(questionDto);

        return ResponseEntity.ok(question);
    }

    /**
     * GET ALL QUESTIONNAIRES
     * @return ResponseEntity.ok
     */

    @GetMapping("/all")
    public ResponseEntity<?> getAllQuestions() {

        ResponseData<?> question = questionService.getAllQuestions();

        return ResponseEntity.ok(question);
    }

    /**
     * GET ONLY ONE QUESTIONNAIRE BY ID
     * @param id, UUID
     * @return ResponseEntity.ok
     */

    @GetMapping("/{id}")
    public ResponseEntity<?> getQuestion(@PathVariable UUID id) {

        ResponseData<?> question = questionService.getQuestionById(id);

        return ResponseEntity.ok(question);
    }

    /**
     * GET QUESTIONNAIRE BY ID FOR STUDENT
     * @param id, UUID
     * @return ResponseEntity.ok
     */

    @GetMapping("/for_student/{id}")
    public ResponseEntity<?> getQuestionById(@PathVariable UUID id) {

        ResponseData<?> question = questionService.getQuestionForStudents(id);

        return ResponseEntity.ok(question);
    }

    /**
     * DELETE QUESTIONNAIRE BY ID (CHANGE deleted STATUS)
     * @param id, UUID
     * @return ResponseEntity.ok
     */

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteQuestionById(@PathVariable UUID id) {

        ResponseData<?> question = questionService.deleteQuestion(id);

        return ResponseEntity.ok(question);
    }
}
