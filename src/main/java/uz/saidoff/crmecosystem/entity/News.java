package uz.saidoff.crmecosystem.entity;

import jakarta.persistence.*;
import lombok.*;
import uz.saidoff.crmecosystem.entity.auth.Role;
import uz.saidoff.crmecosystem.entity.template.AbsEntity;


import java.util.List;
import java.util.UUID;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "news")

public class News extends AbsEntity {
    private String title;
    private String content;
    @ManyToMany(fetch = FetchType.EAGER)
    private List<Role> roles;
    @ManyToOne(fetch = FetchType.EAGER)
    private Attachment attachment;
}
