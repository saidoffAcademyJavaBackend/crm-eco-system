package uz.saidoff.crmecosystem.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.saidoff.crmecosystem.entity.Answers;
import uz.saidoff.crmecosystem.entity.Group;
import uz.saidoff.crmecosystem.entity.GroupStudent;
import uz.saidoff.crmecosystem.entity.Question;
import uz.saidoff.crmecosystem.enums.NotificationType;
import uz.saidoff.crmecosystem.exception.ForbiddenException;
import uz.saidoff.crmecosystem.mapper.QuestionMapper;
import uz.saidoff.crmecosystem.payload.NotificationDto;
import uz.saidoff.crmecosystem.payload.QuestionCreateDto;
import uz.saidoff.crmecosystem.repository.QuestionRepository;
import uz.saidoff.crmecosystem.response.ResponseData;

import java.time.DateTimeException;
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

    /**
     * CREATE NEW QUESTIONNAIRE
     * @param questionDto, QuestionCreateDto
     * @return ResponseData<questionDto>
     */

    public ResponseData<?> createQuestion(QuestionCreateDto questionDto) {

        if (questionDto.getStartDate().isAfter(questionDto.getEndDate())) {
            throw new DateTimeException("Start date cannot be after end date");
        }

        List<Answers> answer = answerService.createAnswer(questionDto);

        Question question = questionMapper.dtoToEntity(questionDto, answer);

        Question save = questionRepository.save(question);


        //CREATING NOTIFICATION WITH THIS QUESTIONNAIRE


        NotificationDto notificationDto = new NotificationDto();
        notificationDto.setTitle(save.getQuestion());
        notificationDto.setDescription(save.getDescription().length() > 100 ? save.getDescription().substring(0, 100) : save.getDescription());
        notificationDto.setObject(save.getId());
        notificationDto.setType(NotificationType.QUESTIONNAIRE);

        notificationService.saveNotification(notificationDto);

        QuestionCreateDto questionCreateDto = questionMapper.entityToDto(save);

        return new ResponseData<>(questionCreateDto, true);
    }

    /**
     * UPDATE QUESTIONNAIRE VIA INFO IN QuestionCreateDto
     * @param questionDto, QuestionCreteDto
     * @return ResponseData<questionDto>
     */

    public ResponseData<?> updateQuestion(QuestionCreateDto questionDto) {

        Optional<Question> byId = questionRepository.findById(questionDto.getQuestionId());

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
     * @return ResponseData<questionDtoList>
     */

    public ResponseData<?> getAllQuestions() {

        List<QuestionCreateDto> questionDtoList = new ArrayList<>();
        List<Question> all = questionRepository.findAll();

        if(all.isEmpty()) {
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
     * @param id, UUID
     * @return ResponseData<questionCreateDto>
     */

    public ResponseData<?> getQuestionForStudents(UUID id) {

        Optional<Question> questionById = questionRepository.findById(id);

        if (questionById.isEmpty()) {
            throw new EntityNotFoundException("Question not found");
        }

        Question question = questionById.get();

        List<UUID> groupIDs = question.getGroupIDs();
        List<Group> groupsByIDs = groupService.getGroupsById(groupIDs);


        //CHECKING IF THE USER CAN READ THIS QUESTIONNAIRE


        for (Group groupsByID : groupsByIDs) {

            for (int i = 0; i < question.getGroupIDs().size(); i++) {
                if (!groupsByID.getId().equals(question.getGroupIDs().get(i))) {
                    throw new ForbiddenException("This group", "forbidden");
                }
            }

            List<GroupStudent> groupStudents = groupsByID.getGroupStudents();

            for (GroupStudent groupStudent : groupStudents) {
                for (int i = 0; i < question.getUsersIDs().size(); i++) {
                    if (groupStudent.getStudent().getId().equals(question.getUsersIDs().get(i))) {
                        throw new ForbiddenException("This user", "forbidden");
                    }
                }
            }

        }

        QuestionCreateDto questionCreateDto = questionMapper.entityToDto(question);

        return new ResponseData<>(questionCreateDto, true);
    }

    /**
     * DELETE ONE QUESTIONNAIRE BY ITS ID (CHANGE deleted STATUS)
     * @param id, UUID
     * @return ResponseData<>
     */

    public ResponseData<?> deleteQuestion(UUID id) {

        questionRepository.updateByIdAndDeleted(id,true);

        return new ResponseData<>(true);
    }

    /**
     * GET ONE QUESTIONNAIRE (FOR ADMINS)
     * @param id, UUID
     * @return Question
     */

    public Question getQuestion(UUID id) {

        return questionRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Question not found")
        );
    }
}
