package uz.saidoff.crmecosystem.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.saidoff.crmecosystem.entity.Answers;
import uz.saidoff.crmecosystem.payload.AnswersDto;
import uz.saidoff.crmecosystem.payload.QuestionCreateDto;
import uz.saidoff.crmecosystem.repository.AnswersRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class AnswerService {

    private final AnswersRepository answersRepository;

    public List<Answers> createAnswer(QuestionCreateDto questionCreateDto) {

        List<Answers> answersList = new ArrayList<>();

        for (AnswersDto answersDto : questionCreateDto.getAnswers()) {

            Answers answers = new Answers();

            answers.setValue(answersDto.getValue());

            if (!questionCreateDto.isQuestionnaire()) {
                answers.setIsRightAnswer(answersDto.getIsRightAnswer());
            }

            answersList.add(answers);
        }

        List<Answers> answers = answersRepository.saveAll(answersList);

        return answers;
    }

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

    public List<Answers> updateAnswers(List<AnswersDto> answersDtoList) {

        List<Answers> answersList = new ArrayList<>();

        for (AnswersDto answersDto : answersDtoList) {

            Integer i = answersRepository.updateAnswersById(answersDto.getValue(),
                                                            answersDto.getIsRightAnswer(),
                                                            answersDto.getId());

            if ( i == 0) {
                throw new RuntimeException("Answer not updated");
            }
        }

        for (AnswersDto answersDto : answersDtoList) {

            Optional<Answers> byId = answersRepository.findById(answersDto.getId());

            if (byId.isEmpty()) {
                throw new RuntimeException("Answer not found");
            }

            Answers answers = byId.get();

            answersList.add(answers);
        }

        return answersList;
    }

}
