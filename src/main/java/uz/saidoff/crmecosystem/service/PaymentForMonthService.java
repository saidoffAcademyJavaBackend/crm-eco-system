package uz.saidoff.crmecosystem.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import uz.saidoff.crmecosystem.payload.PaymentForMonthDto.PaymentForMonthCreatDto;
import uz.saidoff.crmecosystem.payload.PaymentForMonthDto.PaymentForMonthUpdateDto;
import uz.saidoff.crmecosystem.repository.PaymentForServiceRepository;
import uz.saidoff.crmecosystem.response.ResponseData;

@Component
@RequiredArgsConstructor
public class PaymentForMonthService {

    private final PaymentForServiceRepository paymentForServiceRepository;

    public ResponseData<?> creat(PaymentForMonthCreatDto paymentForMonthCreatDto) {


        return null;
    }

    public ResponseData<?> update(PaymentForMonthUpdateDto paymentForMonthUpdateDto) {


        return null;
    }
}
