package uz.saidoff.crmecosystem.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.saidoff.crmecosystem.entity.auth.User;
import uz.saidoff.crmecosystem.entity.template.AbsEntity;
import uz.saidoff.crmecosystem.enums.WeekDays;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Entity(name = "groups")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Group extends AbsEntity {

  private String name;

  @ManyToOne
  private User teacher;

  @Schema(type = "String", pattern = "HH:mm")
  private LocalTime startTime;

  @Schema(type = "String", pattern = "HH:mm")
  private LocalTime endTime;

  private boolean active = true;

  private String linkForTelegram;

  @ManyToOne
  private GroupType groupType;

  @JsonFormat(pattern = "yyyy-MM-dd")
  private LocalDate startedDate;

  private Double paymentAmount;

  private int groupStage = 1;

  @ElementCollection(fetch = FetchType.EAGER)
  @Enumerated(EnumType.STRING)
  private List<WeekDays> weekDays;

  private boolean student;

  private String room;

  @OneToMany
  private List<GroupStudent> groupStudents;
}
