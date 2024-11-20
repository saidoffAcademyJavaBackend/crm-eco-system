package uz.saidoff.crmecosystem.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import uz.saidoff.crmecosystem.entity.Answers;
import uz.saidoff.crmecosystem.entity.Question;
import uz.saidoff.crmecosystem.payload.AnswersDto;
import uz.saidoff.crmecosystem.payload.QuestionCreateDto;
import uz.saidoff.crmecosystem.service.AnswerService;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Component
public class QuestionMapper {


    private final AnswerService answerService;

    public Question dtoToEntity(QuestionCreateDto questionDto, List<Answers> answer) {

        Question question = new Question();

        question.setQuestion(questionDto.getQuestion());
        question.setDescription(questionDto.getDescription());
        question.setAnswers(answer);
        question.setAttachmentIDs(questionDto.getAttachmentIds());
        question.setAnsweredQuestions(questionDto.getAnsweredQuestionIds());
        question.setStartDate(questionDto.getStartDate());
        question.setEndDate(questionDto.getEndDate());
        question.setQuestionnaire(questionDto.isQuestionnaire());
        question.setGroupIDs(questionDto.getGroupIDs());
        question.setUsersIDs(questionDto.getUserIDs());

        LocalDateTime now = LocalDateTime.now();

        if (now.isAfter(question.getStartDate())) {
            question.setInProcess(true);
        }

        return question;
    }

    public QuestionCreateDto entityToDto(Question save) {

        List<AnswersDto> answersDto = answerService.getAnswersDto(save.getAnswers());

        QuestionCreateDto questionDto = new QuestionCreateDto();

        questionDto.setQuestion(save.getQuestion());
        questionDto.setDescription(save.getDescription());
        questionDto.setAnswers(answersDto);
        questionDto.setAttachmentIds(save.getAttachmentIDs());
        questionDto.setAnsweredQuestionIds(save.getAnsweredQuestions());
        questionDto.setStartDate(save.getStartDate());
        questionDto.setEndDate(save.getEndDate());
        questionDto.setQuestionnaire(save.isQuestionnaire());
        questionDto.setGroupIDs(save.getGroupIDs());
        questionDto.setUserIDs(save.getUsersIDs());
        questionDto.setInProcess(save.isInProcess());

        return questionDto;
    }
}
