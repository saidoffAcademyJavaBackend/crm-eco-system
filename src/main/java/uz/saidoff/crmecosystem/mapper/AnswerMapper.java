package uz.saidoff.crmecosystem.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import uz.saidoff.crmecosystem.entity.Answers;
import uz.saidoff.crmecosystem.payload.AnswersDto;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Component
public class AnswerMapper {



    public List<Answers> dtoToEntity(List<AnswersDto> answersDtos) {

        List<Answers> answersList = new ArrayList<>();

        for (AnswersDto answersDto : answersDtos) {

            Answers answers = new Answers();
            answers.setValue(answersDto.getValue());
            answers.setIsRightAnswer(answersDto.getIsRightAnswer());

            answersList.add(answers);
        }

        return answersList;
    }

    public List<AnswersDto> entityToDto(List<Answers> answersList) {

        List<AnswersDto> answersListDto = new ArrayList<>();

        for (Answers answers : answersList) {

            AnswersDto answersDto = new AnswersDto();
            answersDto.setValue(answers.getValue());
            answersDto.setIsRightAnswer(answers.getIsRightAnswer());

            answersListDto.add(answersDto);
        }

        return answersListDto;
    }
}
