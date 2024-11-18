package uz.saidoff.crmecosystem.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.saidoff.crmecosystem.entity.Answers;
import uz.saidoff.crmecosystem.entity.Question;
import uz.saidoff.crmecosystem.mapper.AnswerMapper;
import uz.saidoff.crmecosystem.mapper.QuestionMapper;
import uz.saidoff.crmecosystem.payload.AnswersDto;
import uz.saidoff.crmecosystem.payload.QuestionCreateDto;
import uz.saidoff.crmecosystem.payload.QuestionSetDatesDto;
import uz.saidoff.crmecosystem.repository.QuestionRepository;
import uz.saidoff.crmecosystem.response.ResponseData;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class QuestionService {

    private final QuestionRepository questionRepository;
    private final AnswersService answersService;
    private final QuestionMapper questionMapper;
    private final AnswerMapper answerMapper;

    public ResponseData<?> createQuestion(QuestionCreateDto questionDto) {

        List<Answers> answers = answersService.createAnswers(questionDto);

        return service(questionDto);
    }

    public ResponseData<?> updateQuestion(QuestionCreateDto questionDto) {

        Optional<Question> byId = questionRepository.findById(questionDto.getQuestionId());

        if (byId.isEmpty()) {
            throw new EntityNotFoundException("Question not found");
        }

        return service(questionDto);
    }

    public ResponseData<?> setDates(QuestionSetDatesDto questionSetDatesDto) {

        Optional<Question> byId = questionRepository.findById(questionSetDatesDto.getQuestionId());

        if (byId.isEmpty()) {
            throw new EntityNotFoundException("Question not found");
        }

        Question question = byId.get();
        question.setStartDate(questionSetDatesDto.getStartDate());
        question.setEndDate(questionSetDatesDto.getEndDate());
//        question.setInProcess(true);

        Question save = questionRepository.save(question);

        return new ResponseData<>(questionSetDatesDto, true);
    }

    public ResponseData<?> service(QuestionCreateDto questionDto) {

        List<Answers> answers = answersService.updateAnswers(questionDto);

        Question question = questionMapper.toQuestionEntity(questionDto, answers);

        Question save = questionRepository.save(question);

        List<AnswersDto> answersDtos = answerMapper.entityToDto(answers);

        QuestionCreateDto questionCreateDto = questionMapper.toQuestionCreateDto(save, answersDtos);

        return new ResponseData<>(questionCreateDto, true);
    }

    public ResponseData<?> getQuestion(UUID id) {

        Optional<Question> byId = questionRepository.findById(id);

        if (byId.isEmpty()) {
            throw new EntityNotFoundException("Question not found");
        }

        Question question = byId.get();

        QuestionCreateDto questionCreateDto = questionMapper.toQuestionDto(question);

        return new ResponseData<>(questionCreateDto, true);
    }

    public ResponseData<?> getAllQuestions() {

        List<Question> all = questionRepository.findAll();

        return null;
    }
}
