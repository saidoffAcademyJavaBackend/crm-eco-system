package uz.saidoff.crmecosystem.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import uz.saidoff.crmecosystem.enums.Currency;
import uz.saidoff.crmecosystem.response.ResponseData;
import uz.saidoff.crmecosystem.service.IncomeService;

import java.sql.Date;

@RestController
@RequestMapping("api/income")
@RequiredArgsConstructor
public class IncomeController {

    private final IncomeService incomeService;

    @GetMapping("get-income-history/{start}/{end}/{currency}")
    public ResponseData<?> incomeHistory(@PathVariable Date start, @PathVariable Currency currency,
                                         @PathVariable Date end) {
        return this.incomeService.income(start, end, currency);
    }
}
