package uz.saidoff.crmecosystem.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.saidoff.crmecosystem.entity.auth.User;
import uz.saidoff.crmecosystem.entity.template.AbsEntity;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GroupStudent extends AbsEntity {

    private boolean completed;

    private double paymentAmount;

    @ManyToOne
    private User student;



    //TODO groupId va studentId
    // ni olib tashlash keyin  studentni
    // studentga saqlash kerak group getGroupStudentsni
    // olib chiqib qo'shish kerak

}
