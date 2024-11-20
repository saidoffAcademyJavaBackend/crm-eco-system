package uz.saidoff.crmecosystem.entity;

import jakarta.persistence.*;
import lombok.*;
import uz.saidoff.crmecosystem.entity.auth.User;
import uz.saidoff.crmecosystem.entity.template.AbsEntity;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity(name = "answered_questions")
public class AnsweredQuestions extends AbsEntity {

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User participant;

    @ManyToOne(optional = false)
    @JoinColumn(name = "answer_id", nullable = false)
    private Answers selectedAnswer;
}
