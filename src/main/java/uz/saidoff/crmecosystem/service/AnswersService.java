package uz.saidoff.crmecosystem.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.saidoff.crmecosystem.entity.Answers;
import uz.saidoff.crmecosystem.mapper.AnswerMapper;
import uz.saidoff.crmecosystem.payload.QuestionCreateDto;
import uz.saidoff.crmecosystem.repository.AnswersRepository;

import java.util.List;

@RequiredArgsConstructor
@Service
public class AnswersService {

    private final AnswerMapper answerMapper;
    private final AnswersRepository answersRepository;

    public List<Answers> createAnswers(QuestionCreateDto questionDto) {

        List<Answers> answersList = answerMapper.dtoToEntity(questionDto.getAnswers());

        List<Answers> answers = answersRepository.saveAll(answersList);

        return answers;
    }

}
