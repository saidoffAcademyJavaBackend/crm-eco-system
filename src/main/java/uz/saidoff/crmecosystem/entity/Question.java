package uz.saidoff.crmecosystem.entity;

import jakarta.persistence.*;
import lombok.*;
import uz.saidoff.crmecosystem.entity.template.AbsEntity;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity(name = "question")
public class Question extends AbsEntity {

    private String question;

    private String description;

    @OneToMany
    private List<Attachment> attachments;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    @OneToMany
    private List<Answers> answers;

    @ManyToMany
    private List<AnsweredQuestions> answeredQuestions;

    private boolean isInProcess;

    //if true it is questionnaire, else it's test
    private boolean questionnaire = true;
}
