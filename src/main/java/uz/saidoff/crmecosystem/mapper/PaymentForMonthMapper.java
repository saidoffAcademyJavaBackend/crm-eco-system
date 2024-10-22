package uz.saidoff.crmecosystem.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import uz.saidoff.crmecosystem.entity.PaymentForMonth;
import uz.saidoff.crmecosystem.entity.auth.User;
import uz.saidoff.crmecosystem.enums.PaymentMonthStatus;
import uz.saidoff.crmecosystem.exception.NotFoundException;
import uz.saidoff.crmecosystem.payload.PaymentForMonthDto.PaymentForMonthDto;
import uz.saidoff.crmecosystem.payload.PaymentForMonthDto.PaymentForMonthUpdateDto;
import uz.saidoff.crmecosystem.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class PaymentForMonthMapper {
    private final UserRepository userRepository;

    public PaymentForMonth toEntity(PaymentForMonth paymentForMonth, PaymentForMonthUpdateDto paymentForMonthDto) {
        Optional<User> userOptional = userRepository.findById(paymentForMonthDto.getUserId());
        if (userOptional.isEmpty()) {
            throw new NotFoundException("user not found");
        }
        if (paymentForMonthDto.getMonth()!=null) paymentForMonth.setMonth(paymentForMonthDto.getMonth());
        if (paymentForMonthDto.getPaymentAmount()!=null) {
            paymentForMonth.setPaymentAmount(paymentForMonthDto.getPaymentAmount());
            if (Objects.equals(paymentForMonth.getPaymentAmount(), userOptional.get().getSalary())) {
            paymentForMonth.setStatus(PaymentMonthStatus.PAID);
            } else if (paymentForMonthDto.getPaymentAmount()==0) {
            paymentForMonth.setStatus(PaymentMonthStatus.UNPAID);
            } else {
            paymentForMonth.setStatus(PaymentMonthStatus.HALF_PAID);
            }
        }
        if (paymentForMonthDto.isActive()) paymentForMonth.setActive(paymentForMonth.getStatus() == PaymentMonthStatus.PAID);
        if (paymentForMonthDto.isCurrentMonth())paymentForMonth.setCurrentMonth(true);
        if (paymentForMonthDto.getGroupStudentId() != null) paymentForMonth.setGroupStudentId(paymentForMonthDto.getGroupStudentId());
        return paymentForMonth;
    }

    public PaymentForMonthDto toDto(PaymentForMonth paymentForMonth) {
        PaymentForMonthDto paymentForMonthDto = new PaymentForMonthDto();
        paymentForMonthDto.setPaymentForMonthId(paymentForMonth.getId());
        paymentForMonthDto.setMonth(paymentForMonth.getMonth());
        paymentForMonthDto.setGroupStudentId(paymentForMonth.getGroupStudentId());
        paymentForMonthDto.setPaymentAmount(paymentForMonth.getPaymentAmount());
        paymentForMonthDto.setCurrentMonth(paymentForMonth.isCurrentMonth());
        paymentForMonthDto.setActive(paymentForMonth.isActive());
        paymentForMonthDto.setCurrentMonth(paymentForMonth.isCurrentMonth());
        return paymentForMonthDto;
    }

    public List<PaymentForMonthDto> toDto(List<PaymentForMonth> paymentForMonths) {
        List<PaymentForMonthDto> paymentForMonthDto = new ArrayList<>();
        for (PaymentForMonth paymentForMonth : paymentForMonths) {
            paymentForMonthDto.add(toDto(paymentForMonth));
        }
        return paymentForMonthDto;
    }
}
