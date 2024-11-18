package uz.saidoff.crmecosystem.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.saidoff.crmecosystem.entity.Answers;
import uz.saidoff.crmecosystem.entity.Question;
import uz.saidoff.crmecosystem.mapper.AnswerMapper;
import uz.saidoff.crmecosystem.mapper.QuestionMapper;
import uz.saidoff.crmecosystem.payload.AnswersDto;
import uz.saidoff.crmecosystem.payload.QuestionCreateDto;
import uz.saidoff.crmecosystem.repository.QuestionRepository;
import uz.saidoff.crmecosystem.response.ResponseData;

import java.util.List;

@RequiredArgsConstructor
@Service
public class QuestionService {

    private final QuestionRepository questionRepository;
    private final AnswersService answersService;
    private final QuestionMapper questionMapper;
    private final AnswerMapper answerMapper;

    public ResponseData<?> createQuestion(QuestionCreateDto questionDto) {

        List<Answers> answers = answersService.createAnswers(questionDto);

        Question question = questionMapper.toQuestionEntity(questionDto, answers);

        Question save = questionRepository.save(question);

        List<AnswersDto> answersDtos = answerMapper.entityToDto(answers);

        QuestionCreateDto questionCreateDto = questionMapper.toQuestionCreateDto(save, answersDtos);

        return new ResponseData<>(questionCreateDto, true);
    }

}
