package uz.saidoff.crmecosystem.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.saidoff.crmecosystem.payload.AnswerQuestionsDto;
import uz.saidoff.crmecosystem.response.ResponseData;
import uz.saidoff.crmecosystem.service.AnswerQuestionService;
import uz.saidoff.crmecosystem.valid.CheckPermission;

@RestController
@RequestMapping("/api/answer_question")
@RequiredArgsConstructor
public class AnswerQuestionController {

    private final AnswerQuestionService answerQuestionService;

    @PostMapping("")
    public ResponseEntity<?> setAnswerToQuestion(@RequestBody AnswerQuestionsDto answerQuestionsDto) {

        ResponseData<?> answerQuestion = answerQuestionService.setAnswerQuestions(answerQuestionsDto);

        return ResponseEntity.ok(answerQuestion);
    }

}
