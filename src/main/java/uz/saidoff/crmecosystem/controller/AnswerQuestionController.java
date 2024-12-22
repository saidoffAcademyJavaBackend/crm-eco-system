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

@RestController
@RequestMapping("/api/answer_question")
@RequiredArgsConstructor
public class AnswerQuestionController {

    private final AnswerQuestionService answerQuestionService;

    /**
     * SET USER'S ANSWER TO QUESTIONNAIRE
     * @param answerQuestionsDto, AnswerQuestionsDto
     * @return ResponseEntity.ok
     */

    @PostMapping("")
    public ResponseEntity<?> setAnswerToQuestion(@RequestBody AnswerQuestionsDto answerQuestionsDto) {

        ResponseData<?> answerQuestion = answerQuestionService.setAnswerQuestions(answerQuestionsDto);

        return ResponseEntity.ok(answerQuestion);
    }

}
