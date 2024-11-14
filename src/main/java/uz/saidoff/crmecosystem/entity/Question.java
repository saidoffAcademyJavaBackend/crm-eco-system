package uz.saidoff.crmecosystem.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
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
    private List<Answer> answers;

    private Boolean isInProcess;
}
