package uz.saidoff.crmecosystem.entity;

import jakarta.persistence.*;
import lombok.*;
import uz.saidoff.crmecosystem.entity.auth.User;
import uz.saidoff.crmecosystem.entity.template.AbsEntity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity(name = "question")
public class Question extends AbsEntity {

    private String question;

    private String description;

    @ElementCollection
    private List<UUID> attachmentIDs;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    @OneToMany
    private List<Answers> answers;

    @ManyToMany
    private List<AnsweredQuestions> answeredQuestions;

    @ElementCollection
    private List<UUID> groupIDs;

    @ElementCollection
    private List<UUID> usersIDs;

    private boolean isInProcess = false;

    //if true it is questionnaire, else it's test
    private boolean questionnaire = true;
}
