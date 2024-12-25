package uz.saidoff.crmecosystem.service;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import uz.saidoff.crmecosystem.entity.Answers;
import uz.saidoff.crmecosystem.exception.NotFoundException;
import uz.saidoff.crmecosystem.payload.AnswersDto;
import uz.saidoff.crmecosystem.payload.questionnaire.AnswersCreateDto;
import uz.saidoff.crmecosystem.payload.questionnaire.QuestionCreateDto;
import uz.saidoff.crmecosystem.repository.AnswersRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class AnswerService {

  private final AnswersRepository answersRepository;
  private final QuestionService questionService;

  public AnswerService(AnswersRepository answersRepository,@Lazy QuestionService questionService) {
    this.answersRepository = answersRepository;
    this.questionService = questionService;
  }

  /**
   * CREATE ANSWERS
   *
   * @param questionCreateDto, QuestionCreateDto
   * @return List<Answers>
   */

  public List<Answers> createAnswer(QuestionCreateDto questionCreateDto) {

    List<Answers> answersList = new ArrayList<>();

    for (AnswersCreateDto answersDto : questionCreateDto.getAnswers()) {

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
   *
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
   *
   * @param questionCreateDto, QuestionCreateDto
   * @return List<Answers>
   */

  public List<Answers> updateAnswers(QuestionCreateDto questionCreateDto) {

    List<AnswersDto> answers = questionCreateDto.getAnswers();


    List<Answers> byQuestion = questionService.getQuestionsAnswers(questionCreateDto.getQuestionId());

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
