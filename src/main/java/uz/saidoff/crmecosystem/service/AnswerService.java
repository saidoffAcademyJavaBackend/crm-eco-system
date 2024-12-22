package uz.saidoff.crmecosystem.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.saidoff.crmecosystem.entity.Answers;
import uz.saidoff.crmecosystem.entity.Question;
import uz.saidoff.crmecosystem.exception.NotFoundException;
import uz.saidoff.crmecosystem.payload.AnswersDto;
import uz.saidoff.crmecosystem.payload.QuestionCreateDto;
import uz.saidoff.crmecosystem.repository.AnswersRepository;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class AnswerService {

    private final AnswersRepository answersRepository;
    private final QuestionService questionService;

    /**
     * CREATE ANSWERS
     * @param questionCreateDto, QuestionCreateDto
     * @return List<Answers>
     */

    public List<Answers> createAnswer(QuestionCreateDto questionCreateDto) {

        List<Answers> answersList = new ArrayList<>();

        for (AnswersDto answersDto : questionCreateDto.getAnswers()) {

            Answers answers = new Answers();

            answers.setValue(answersDto.getValue());

            if (!questionCreateDto.isQuestionnaire()) {
                answers.setIsRightAnswer(answersDto.getIsRightAnswer());
            }

            answersList.add(answers);
        }

        return answersRepository.saveAll(answersList);
    }

    /**
     * GET ANSWERS
     * @param answers, List<Answers>
     * @return List<AnswersDto>
     */

    public List<AnswersDto> getAnswersDto(List<Answers> answers) {

        List<AnswersDto> answersDtoList = new ArrayList<>();

        for (Answers answer : answers) {

            AnswersDto answersDto = new AnswersDto();

            answersDto.setId(answer.getId());
            answersDto.setValue(answer.getValue());
            answersDto.setIsRightAnswer(answer.getIsRightAnswer());

            answersDtoList.add(answersDto);
        }

        return answersDtoList;
    }

    /**
     * UPDATE ANSWERS
     * @param questionCreateDto, QuestionCreateDto
     * @return List<Answers>
     */

    public List<Answers> updateAnswers(QuestionCreateDto questionCreateDto) {

        List<AnswersDto> answers = questionCreateDto.getAnswers();

        Question question = questionService.getQuestion(questionCreateDto.getQuestionId());

        List<Answers> byQuestion = answersRepository.findByQuestion(question);

        if (byQuestion.isEmpty()) {
            throw new NotFoundException("Answers not found");
        }

        for (AnswersDto answer : answers) {
            for (Answers value : byQuestion) {
                value.setValue(answer.getValue());
                value.setIsRightAnswer(answer.getIsRightAnswer());
            }
        }

        return answersRepository.saveAll(byQuestion);
    }

}
