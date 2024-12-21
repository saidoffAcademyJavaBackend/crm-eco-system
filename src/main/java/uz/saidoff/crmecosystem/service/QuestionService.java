package uz.saidoff.crmecosystem.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.saidoff.crmecosystem.entity.Answers;
import uz.saidoff.crmecosystem.entity.Group;
import uz.saidoff.crmecosystem.entity.GroupStudent;
import uz.saidoff.crmecosystem.entity.Question;
import uz.saidoff.crmecosystem.exception.ForbiddenException;
import uz.saidoff.crmecosystem.mapper.QuestionMapper;
import uz.saidoff.crmecosystem.payload.QuestionCreateDto;
import uz.saidoff.crmecosystem.repository.GroupStudentRepository;
import uz.saidoff.crmecosystem.repository.QuestionRepository;
import uz.saidoff.crmecosystem.repository.UserRepository;
import uz.saidoff.crmecosystem.response.ResponseData;
import uz.saidoff.crmecosystem.util.UserSession;

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
    private final UserSession userSession;
    private final UserRepository userRepository;
    private final GroupStudentRepository groupStudentRepository;
    private final GroupService groupService;

    public ResponseData<?> createQuestion(QuestionCreateDto questionDto) {

        if (questionDto.getStartDate().isAfter(questionDto.getEndDate())) {
            throw new DateTimeException("Start date cannot be after end date");
        }

        List<Answers> answer = answerService.createAnswer(questionDto);

        Question question = questionMapper.dtoToEntity(questionDto, answer);

        Question save = questionRepository.save(question);

        QuestionCreateDto questionCreateDto = questionMapper.entityToDto(save);

        return new ResponseData<>(questionCreateDto, true);
    }

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

    public ResponseData<?> getQuestionById(UUID id) {

        Optional<Question> byId = questionRepository.findById(id);

        if (byId.isEmpty()) {
            throw new EntityNotFoundException("Question not found");
        }

        Question question = byId.get();

        QuestionCreateDto questionCreateDto = questionMapper.entityToDto(question);

        return new ResponseData<>(questionCreateDto, true);
    }

    public ResponseData<?> getQuestionForStudents(UUID id) {

        UUID userId = userSession.getUser().getId();

        Optional<Question> questionById = questionRepository.findById(id);

        if (questionById.isEmpty()) {
            throw new EntityNotFoundException("Question not found");
        }

        Question question = questionById.get();

        List<UUID> groupIDs = question.getGroupIDs();
        List<Group> groupsByIDs = groupService.getGroupsById(groupIDs);

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

    public ResponseData<?> deleteQuestion(UUID id) {

        questionRepository.deleteById(id);

        return new ResponseData<>(true);
    }

    public Question getQuestion(UUID id) {

        return questionRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Question not found")
        );
    }
}
