package uz.saidoff.crmecosystem.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.saidoff.crmecosystem.entity.*;
import uz.saidoff.crmecosystem.enums.NotificationType;
import uz.saidoff.crmecosystem.exception.ForbiddenException;
import uz.saidoff.crmecosystem.mapper.QuestionMapper;
import uz.saidoff.crmecosystem.payload.NotificationDto;
import uz.saidoff.crmecosystem.payload.questionnaire.QuestionCreateDto;
import uz.saidoff.crmecosystem.payload.questionnaire.QuestionResponseDto;
import uz.saidoff.crmecosystem.repository.QuestionRepository;
import uz.saidoff.crmecosystem.response.ResponseData;
import uz.saidoff.crmecosystem.util.UserSession;

import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class QuestionService {

  private final QuestionRepository questionRepository;
  private final QuestionMapper questionMapper;
  private final AnswerService answerService;
  private final GroupService groupService;
  private final NotificationService notificationService;
  private final UserSession userSession;

  /**
   * CREATE NEW QUESTIONNAIRE
   *
   * @param questionDto, QuestionCreateDto
   * @return ResponseData<questionDto>
   */

  public ResponseData<?> createQuestion(QuestionCreateDto questionDto) {

    if (questionDto.getStartDate().isAfter(questionDto.getEndDate())) {
      throw new DateTimeException("Start date cannot be after end date");
    }

    List<Answers> answer = answerService.createAnswer(questionDto);

    Question question = questionMapper.dtoToEntity(questionDto);
    question.setAnswers(answer);

    Question save = questionRepository.save(question);


    //CREATING NOTIFICATION FOR THIS QUESTIONNAIRE
    NotificationDto notificationDto = new NotificationDto();
    notificationDto.setTitle(save.getQuestion());
    notificationDto.setDescription(save.getDescription().length() > 100 ? save.getDescription().substring(0, 100) : save.getDescription());
    notificationDto.setObject(save.getId());
    notificationDto.setType(NotificationType.QUESTIONNAIRE);

    notificationService.saveNotification(notificationDto);

    QuestionResponseDto questionCreateDto = questionMapper.entityToDto(save);

    return new ResponseData<>(questionCreateDto, true);
  }

  /**
   * UPDATE QUESTIONNAIRE VIA INFO IN QuestionCreateDto
   *
   * @param questionDto, QuestionCreteDto
   * @param id
   * @return ResponseData<questionDto>
   */

  public ResponseData<?> updateQuestion(UUID id, QuestionCreateDto questionDto) {

    Optional<Question> byId = questionRepository.findById(id);
    questionDto.setQuestionId(id);
    if (byId.isEmpty()) {
      throw new EntityNotFoundException("Question not found");
    }

    Question question = questionMapper.dtoToEntity(questionDto);

    Question save = questionRepository.save(question);

    QuestionCreateDto questionCreateDto = questionMapper.entityToDto(save);

    return new ResponseData<>(questionCreateDto, true);
  }

  /**
   * GET ALL QUESTIONNAIRES
   *
   * @return ResponseData<questionDtoList>
   */

  public ResponseData<?> getAllQuestions() {

    List<QuestionCreateDto> questionDtoList = new ArrayList<>();
    List<Question> all = questionRepository.findAll();

    if (all.isEmpty()) {
      throw new EntityNotFoundException("Question not found");
    }

    for (Question question : all) {

      QuestionCreateDto questionDto = questionMapper.entityToDto(question);

      questionDtoList.add(questionDto);
    }

    return new ResponseData<>(questionDtoList, true);
  }

  /**
   * GET ONE QUESTIONNAIRE BY ITS ID
   *
   * @param id, UUID
   * @return ResponseData<questionCreateDto>
   */

  public ResponseData<?> getQuestionById(UUID id) {

    Optional<Question> byId = questionRepository.findById(id);

    if (byId.isEmpty()) {
      throw new EntityNotFoundException("Question not found");
    }

    Question question = byId.get();

    QuestionCreateDto questionCreateDto = questionMapper.entityToDto(question);

    return new ResponseData<>(questionCreateDto, true);
  }

  /**
   * GET QUESTION BY ID FOR ONE EXACT USER
   *
   * @param id, UUID
   * @return ResponseData<questionCreateDto>
   */

  public ResponseData<?> getQuestionForStudents(UUID id) {

    UUID userId = userSession.getUser().getId();

    Optional<Question> questionById = questionRepository.findById(id);

    if (questionById.isEmpty()) {
      throw new EntityNotFoundException("Question not found");
    }

    Question question = questionById.get();

    List<UUID> groupIDs = question.getGroupIDs();
    List<Group> groupsByIDs = groupService.getGroupsById(groupIDs);

    checkIfUserCanReadQuestion(userId, groupsByIDs, question);

    QuestionCreateDto questionCreateDto = questionMapper.entityToDto(question);

    return new ResponseData<>(questionCreateDto, true);
  }

  /**
   * DELETE ONE QUESTIONNAIRE BY ITS ID (CHANGE deleted STATUS)
   *
   * @param id, UUID
   * @return ResponseData<>
   */

  public ResponseData<?> deleteQuestion(UUID id) {

    questionRepository.updateByIdAndDeleted(id);

    return new ResponseData<>(true);
  }

  /**
   * GET ONE QUESTIONNAIRE (FOR ADMINS)
   *
   * @param id, UUID
   * @return Question
   */

  public Question getQuestion(UUID id) {

    return questionRepository.findById(id).orElseThrow(
      () -> new EntityNotFoundException("Question not found")
    );
  }

  public List<NotificationResponse> getQuestionsForScheduledJob() {

    UUID id = userSession.getUser().getId();

    LocalDateTime now = LocalDateTime.now();

    List<Question> questions = questionRepository.getQuestions(now);

    for (Question question : questions) {
      if (question.getUsersIDs().contains(id)) {
        throw new ForbiddenException("This user", "Forbidden");
      }
    }

    return notificationService.getNotificationForScheduledJob(questions);
  }

  /**
   * CHECK IF USER IN THIS SESSION IS NOT FORBIDDEN
   *
   * @param userId,      UUID
   * @param groupsByIDs, List<Group>
   * @param question,    Question
   */

  public void checkIfUserCanReadQuestion(UUID userId, List<Group> groupsByIDs, Question question) {

    for (Group groupsByID : groupsByIDs) {

      for (int i = 0; i < question.getGroupIDs().size(); i++) {
        if (!groupsByID.getId().equals(question.getGroupIDs().get(i))) {
          throw new ForbiddenException("This group", "forbidden");
        }
      }

      List<GroupStudent> groupStudents = groupsByID.getGroupStudents();

      for (GroupStudent groupStudent : groupStudents) {
        for (int i = 0; i < question.getUsersIDs().size(); i++) {
          if (groupStudent.getStudent().getId().equals(question.getUsersIDs().get(i))
            &&
            groupStudent.getStudent().getId().equals(userId)) {
            throw new ForbiddenException("This user", "forbidden");
          }
        }
      }

    }
  }


  //  GET SINGLE QUESTION'S ASSIGNED ANSWERS
  public List<Answers> getQuestionsAnswers(UUID questionId) {
    Optional<Question> optionalQuestion = questionRepository.findById(questionId);
    if (optionalQuestion.isEmpty()) {
      throw new EntityNotFoundException("Question not found");
    }
    return optionalQuestion.get().getAnswers();
  }

}
