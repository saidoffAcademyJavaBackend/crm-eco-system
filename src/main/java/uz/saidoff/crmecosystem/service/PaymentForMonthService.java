package uz.saidoff.crmecosystem.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import uz.saidoff.crmecosystem.entity.GroupStudent;
import uz.saidoff.crmecosystem.entity.PaymentForMonth;
import uz.saidoff.crmecosystem.entity.auth.User;
import uz.saidoff.crmecosystem.enums.PaymentMonthStatus;
import uz.saidoff.crmecosystem.exception.NotFoundException;
import uz.saidoff.crmecosystem.mapper.PaymentForMonthMapper;
import uz.saidoff.crmecosystem.payload.PaymentForMonthDto.PaymentForMonthCreatDto;
import uz.saidoff.crmecosystem.payload.PaymentForMonthDto.PaymentForMonthUpdateDto;
import uz.saidoff.crmecosystem.repository.GroupStudentRepository;
import uz.saidoff.crmecosystem.repository.PaymentForServiceRepository;
import uz.saidoff.crmecosystem.repository.UserRepository;
import uz.saidoff.crmecosystem.response.ResponseData;

import java.sql.Date;
import java.util.*;

@Component
@RequiredArgsConstructor
public class PaymentForMonthService {

    private final PaymentForServiceRepository paymentForServiceRepository;
    private final GroupStudentRepository groupStudentRepository;
    private final UserRepository userRepository;
    private final PaymentForMonthMapper paymentForMonthMapper;

    public ResponseData<?> creat(PaymentForMonthCreatDto paymentForMonthCreatDto) {
        Optional<GroupStudent> groupStudentOptional = this.groupStudentRepository
                .findById(paymentForMonthCreatDto.getGroupStudentId());
        if (groupStudentOptional.isEmpty()) {
            throw new NotFoundException("group student not found");
        }
        Optional<User> userOptional = userRepository.findById(groupStudentOptional.get().getStudentId().getId());
        if (userOptional.isEmpty()) {
            throw new NotFoundException("user not found");
        }
        Double allAmount=paymentForMonthCreatDto.getAllPaymentAmount();
        Date month=paymentForMonthCreatDto.getMonth();
        for (int i = 1; i <= paymentForMonthCreatDto.getAllMonths(); i++) {
            PaymentForMonth paymentForMonth = new PaymentForMonth();
            month.setMonth(month.getMonth()+(i-1));
            paymentForMonth.setMonth(month);
            paymentForMonth.setGroupStudentId(paymentForMonthCreatDto.getGroupStudentId());
            if (allAmount>0 && allAmount>=paymentForMonthCreatDto.getPaymentAmount()) {
                paymentForMonth.setPaymentAmount(paymentForMonthCreatDto.getPaymentAmount());

            } else if (allAmount>0) {
                paymentForMonth.setPaymentAmount(paymentForMonthCreatDto.getPaymentAmount()-allAmount);
                paymentForMonthCreatDto.setPaymentAmount(0.0);
            }
            else {
                paymentForMonth.setPaymentAmount(paymentForMonthCreatDto.getPaymentAmount());
                paymentForMonthCreatDto.setPaymentAmount(0.0);
            }
            if (Objects.equals(paymentForMonth.getPaymentAmount(), userOptional.get().getSalary())) {
                paymentForMonth.setStatus(PaymentMonthStatus.PAID);
            } else if (paymentForMonthCreatDto.getPaymentAmount()==0) {
                paymentForMonth.setStatus(PaymentMonthStatus.UNPAID);
            } else {
                paymentForMonth.setStatus(PaymentMonthStatus.HALF_PAID);
            }
            paymentForMonth.setCurrentMonth(i == paymentForMonthCreatDto.getStartMonth());
            paymentForMonth.setActive(paymentForMonth.getStatus() == PaymentMonthStatus.PAID);
            this.paymentForServiceRepository.save(paymentForMonth);
            allAmount-=paymentForMonth.getPaymentAmount();
        }
        return ResponseData.successResponse("success");
    }


    public ResponseData<?> update(PaymentForMonthUpdateDto paymentForMonthUpdateDto) {
        Optional<PaymentForMonth> paymentForMonthOptional = this.paymentForServiceRepository
                .findById(paymentForMonthUpdateDto.getPaymentForMonthId());
        if (paymentForMonthOptional.isEmpty()) {
            throw new NotFoundException("payment for month not found");
        }
        PaymentForMonth paymentForMonth = paymentForMonthOptional.get();
        this.paymentForMonthMapper.toEntity(paymentForMonth, paymentForMonthUpdateDto);
        this.paymentForServiceRepository.save(paymentForMonth);
        return ResponseData.successResponse(this.paymentForMonthMapper.toDto(paymentForMonth));
    }

    public ResponseData<?> getAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<PaymentForMonth> paymentForMonthPage = this.paymentForServiceRepository.findAllByAndDeletedFalse(pageable);
        if (paymentForMonthPage.isEmpty()) {
            throw new NotFoundException("payment for month not found");
        }

        Map<String, Object> response = new HashMap<>();
        response.put("data", paymentForMonthMapper.toDto(paymentForMonthPage.toList()));
        response.put("total", paymentForMonthPage.getTotalElements());
        response.put("totalPages", paymentForMonthPage.getTotalPages());

        return ResponseData.successResponse(response);
    }

}
