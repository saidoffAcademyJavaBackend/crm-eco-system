package uz.saidoff.crmecosystem.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.saidoff.crmecosystem.entity.AnsweredQuestions;
import uz.saidoff.crmecosystem.entity.Answers;
import uz.saidoff.crmecosystem.entity.Question;
import uz.saidoff.crmecosystem.entity.auth.User;
import uz.saidoff.crmecosystem.exception.NotFoundException;
import uz.saidoff.crmecosystem.payload.AnswerQuestionsDto;
import uz.saidoff.crmecosystem.repository.AnsweredQuestionsRepository;
import uz.saidoff.crmecosystem.repository.QuestionRepository;
import uz.saidoff.crmecosystem.repository.UserRepository;
import uz.saidoff.crmecosystem.response.ResponseData;
import uz.saidoff.crmecosystem.util.UserSession;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class AnswerQuestionService {

    private final QuestionRepository questionRepository;
    private final UserSession userSession;
    private final UserRepository userRepository;
    private final AnsweredQuestionsRepository answeredQuestionsRepository;

    /**
     * SET USER'S ANSWER TO QUESTIONNAIRE
     * @param answerQuestionsDto, AnswerQuestionsDto
     * @return ResponseData<answerQuestionsDto>
     */

    public ResponseData<?> setAnswerQuestions(AnswerQuestionsDto answerQuestionsDto) {

        UUID id = userSession.getUser().getId();

        AnsweredQuestions answeredQuestions = new AnsweredQuestions();

        Optional<Question> byId = questionRepository.findById(answerQuestionsDto.getQuestionId());
        User user = userRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Question not found")
        );

        if (byId.isEmpty()) {
            throw new NotFoundException("Question not found");
        }

        Question question = byId.get();

        List<Answers> answers = question.getAnswers();

        for (Answers answer : answers) {
            if (answer.getId().equals(answerQuestionsDto.getAnswerId())) {

                answeredQuestions.setSelectedAnswer(answer);

                break;
            }
        }

        answeredQuestions.setQuestions(question);
        answeredQuestions.setParticipant(user);

        AnsweredQuestions save = answeredQuestionsRepository.save(answeredQuestions);

        AnswerQuestionsDto answerQuestionsDto1 = new AnswerQuestionsDto();
        answerQuestionsDto1.setQuestionId(save.getQuestions().getId());
        answerQuestionsDto1.setAnswerId(save.getSelectedAnswer().getId());
        answerQuestionsDto1.setUserId(save.getParticipant().getId());

        return new ResponseData<>(answerQuestionsDto1, true);
    }
}
