package uz.saidoff.crmecosystem.payload.PaymentForMonthDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PaymentForMonthCreatDto {
    private UUID groupStudentId;
    private Date month;// qaysi oyga to'lov qilinmoqda
    private Integer startMonth;//nechinchi oydan bohslayabti
    private Integer allMonths;// kursni umumiy davomiyligi
    private Double paymentAmount;// hozirgi to'lgangan summa
    private Double allPaymentAmount;// Umimiy summa butun kurs uchun
    private boolean active;// qaysi oy berilgan bo'lsa o'sha oyga to'langani
    private boolean currentMonth; //
}
