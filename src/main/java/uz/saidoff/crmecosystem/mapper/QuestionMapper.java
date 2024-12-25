package uz.saidoff.crmecosystem.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import uz.saidoff.crmecosystem.entity.Answers;
import uz.saidoff.crmecosystem.entity.Question;
import uz.saidoff.crmecosystem.payload.questionnaire.AnswersResponseDto;
import uz.saidoff.crmecosystem.payload.questionnaire.QuestionCreateDto;
import uz.saidoff.crmecosystem.payload.questionnaire.QuestionResponseDto;
import uz.saidoff.crmecosystem.service.AnswerService;

import java.util.List;

@RequiredArgsConstructor
@Component
public class QuestionMapper {

  private final AnswerService answerService;

  /**
   * TURN QuestionCreateDto INTO Question WITH List<Answers> WHEN CREATING QUESTIONNAIRE
   *
   * @param questionDto, QuestionCreateDto
   * @param answer,      List<Answers>
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
   *
   * @param questionDto, QuestionCreateDto
   * @return Question
   */

  public Question dtoToEntity(QuestionCreateDto questionDto) {

    Question question = new Question();

    question.setQuestion(questionDto.getQuestion());
    question.setDescription(questionDto.getDescription());
    question.setAttachmentIDs(questionDto.getAttachmentIds());
    question.setStartDate(questionDto.getStartDate());
    question.setEndDate(questionDto.getEndDate());
    question.setQuestionnaire(questionDto.isQuestionnaire());
    question.setGroupIDs(questionDto.getGroupIDs());
    question.setUsersIDs(questionDto.getUserIDs());

    return question;
  }

  /**
   * TURN Question INTO QuestionCreateDto
   *
   * @param save, Question
   * @return QuestionCreateDto
   */

  public QuestionResponseDto entityToDto(Question question) {

    List<AnswersResponseDto> answersDto = question.getAnswers()
      .stream().map(q -> new AnswersResponseDto(q.getId(), q.getValue())).toList();

    QuestionResponseDto questionDto =
      QuestionResponseDto.builder().
        id(question.getId()).
        question(question.getQuestion()).
        description(question.getDescription()).
        attachmentIds(question.getAttachmentIDs()).
        answers(answersDto).
        questionnaire(question.isQuestionnaire()).
        build();

    return questionDto;
  }
}
