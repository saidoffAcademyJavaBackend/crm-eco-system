package uz.saidoff.crmecosystem.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import uz.saidoff.crmecosystem.payload.OutcomeAndIncome.SalaryHistory;
import uz.saidoff.crmecosystem.response.ResponseData;
import uz.saidoff.crmecosystem.service.BetweenHistoryService;

import java.sql.Date;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/between-history-for-user")
public class BetweenHistoryForUserController {

    private final BetweenHistoryService betweenHistory;

    @GetMapping("get/{start}/{end}")
    public ResponseData<?> getBetweenHistoryForUser(@PathVariable Date start, @PathVariable Date end,
                                                    @RequestBody SalaryHistory salaryHistory) {
        return this.betweenHistory.salaryHistory(start, end, salaryHistory);
    }

}
