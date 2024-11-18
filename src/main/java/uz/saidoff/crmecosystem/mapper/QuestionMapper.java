package uz.saidoff.crmecosystem.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import uz.saidoff.crmecosystem.entity.Answers;
import uz.saidoff.crmecosystem.entity.Question;
import uz.saidoff.crmecosystem.payload.AnswersDto;
import uz.saidoff.crmecosystem.payload.QuestionCreateDto;

import java.util.List;

@RequiredArgsConstructor
@Component
public class QuestionMapper {

    public Question toQuestionEntity(QuestionCreateDto dto, List<Answers> answersList) {

        Question question = new Question();
        question.setQuestion(dto.getQuestion());
        question.setDescription(dto.getDescription());
        question.setAnswers(answersList);
        question.setAttachments(dto.getAttachmentIds());
        question.setInProcess(false);
        question.setQuestionnaire(true);

        return question;
    }

    public QuestionCreateDto toQuestionCreateDto(Question question, List<AnswersDto> answersDtoList) {

        QuestionCreateDto dto = new QuestionCreateDto();
        dto.setQuestion(question.getQuestion());
        dto.setDescription(question.getDescription());
        dto.setAttachmentIds(question.getAttachments());
        dto.setAnswers(answersDtoList);

        return null;
    }

}
