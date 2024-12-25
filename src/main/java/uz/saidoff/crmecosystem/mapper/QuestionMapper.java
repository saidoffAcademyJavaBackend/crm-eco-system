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

    /**
     * TURN QuestionCreateDto INTO Question WITH List<Answers> WHEN CREATING QUESTIONNAIRE
     * @param questionDto, QuestionCreateDto
     * @param answer, List<Answers>
     * @return Question
     */

    public Question dtoToEntity(QuestionCreateDto questionDto, List<Answers> answer) {

        Question question = new Question();

        question.setQuestion(questionDto.getQuestion());
        question.setDescription(questionDto.getDescription());
        question.setAnswers(answer);
        question.setAttachmentIDs(questionDto.getAttachmentIds());
        question.setStartDate(questionDto.getStartDate());
        question.setEndDate(questionDto.getEndDate());
        question.setQuestionnaire(questionDto.isQuestionnaire());
        question.setGroupIDs(questionDto.getGroupIDs());
        question.setUsersIDs(questionDto.getUserIDs());

        return question;
    }

    /**
     * TURN QuestionCreateDto INTO Question WHEN UPDATING QUESTIONNAIRE
     * @param questionDto, QuestionCreateDto
     * @return Question
     */

    public Question dtoToEntity(QuestionCreateDto questionDto) {

        Question question = new Question();
        List<Answers> answers = answerService.updateAnswers(questionDto);

        question.setQuestion(questionDto.getQuestion());
        question.setDescription(questionDto.getDescription());
        question.setAttachmentIDs(questionDto.getAttachmentIds());
        question.setAnswers(answers);
        question.setStartDate(questionDto.getStartDate());
        question.setEndDate(questionDto.getEndDate());
        question.setQuestionnaire(questionDto.isQuestionnaire());
        question.setGroupIDs(questionDto.getGroupIDs());
        question.setUsersIDs(questionDto.getUserIDs());

        return question;
    }

    /**
     * TURN Question INTO QuestionCreateDto
     * @param save, Question
     * @return QuestionCreateDto
     */

    public QuestionCreateDto entityToDto(Question save) {

        List<AnswersDto> answersDto = answerService.getAnswersDto(save.getAnswers());

        QuestionCreateDto questionDto = new QuestionCreateDto();

        questionDto.setQuestionId(save.getId());
        questionDto.setQuestion(save.getQuestion());
        questionDto.setDescription(save.getDescription());
        questionDto.setAnswers(answersDto);
        questionDto.setAttachmentIds(save.getAttachmentIDs());
        questionDto.setStartDate(save.getStartDate());
        questionDto.setEndDate(save.getEndDate());
        questionDto.setQuestionnaire(save.isQuestionnaire());
        questionDto.setGroupIDs(save.getGroupIDs());
        questionDto.setUserIDs(save.getUsersIDs());

        return questionDto;
    }
}
