package uz.saidoff.crmecosystem.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.*;
import uz.saidoff.crmecosystem.entity.auth.User;
import uz.saidoff.crmecosystem.entity.template.AbsEntity;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class CommentTask extends AbsEntity {

    private String comment;

    @ManyToOne(optional = false)
    private Task task;

    @ManyToOne(optional = false)
    private User user;

    @ManyToOne
    private CommentTask replyComment;
}
