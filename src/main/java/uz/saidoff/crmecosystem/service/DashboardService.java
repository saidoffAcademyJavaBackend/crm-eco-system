package uz.saidoff.crmecosystem.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.saidoff.crmecosystem.exception.NotFoundException;
import uz.saidoff.crmecosystem.payload.DashboardStatsResponseDto;
import uz.saidoff.crmecosystem.payload.TransactionSummaryDto;
import uz.saidoff.crmecosystem.repository.TransactionRepository;
import uz.saidoff.crmecosystem.response.ResponseData;

import java.util.*;

@Service
@RequiredArgsConstructor
public class DashboardService {
    private final TransactionRepository transactionRepository;

    public ResponseData<?> getStats(Date from, Date to, String duration, String currency, Boolean isIncome) {
        DashboardStatsResponseDto dashboardStatsResponseDto = new DashboardStatsResponseDto();
        switch (duration.toLowerCase()) {
            case "day":
                List<Object[]> transactionSummaryByDateAndCurrency = transactionRepository.getTransactionSummaryByDateAndCurrency(from, to, currency, isIncome);
                if (transactionSummaryByDateAndCurrency.isEmpty()) {
                    throw new NotFoundException("transactions not found");
                }
                List<TransactionSummaryDto> list = transactionSummaryByDateAndCurrency.stream().map(v ->
                        new TransactionSummaryDto(v[0].toString(), (String) v[1], (Double) v[2], (Long) v[3])).toList();
                dashboardStatsResponseDto.setCurrency(currency);
                dashboardStatsResponseDto.setDuration(duration);
                dashboardStatsResponseDto.setIsIncome(isIncome);
                dashboardStatsResponseDto.setTransactions(list);
                break;
            case "month":
                List<Object[]> transactionSummaryByMonthAndCurrency = transactionRepository.getTransactionSummaryByMonthAndCurrency(from, to, currency, isIncome);
                if (transactionSummaryByMonthAndCurrency.isEmpty()) {
                    throw new NotFoundException("transactions not found");
                }
                List<TransactionSummaryDto> list1 = transactionSummaryByMonthAndCurrency.stream().map(v ->
                        new TransactionSummaryDto(v[0].toString(), (String) v[1], (Double) v[2], (Long) v[3])).toList();
                dashboardStatsResponseDto.setCurrency(currency);
                dashboardStatsResponseDto.setDuration(duration);
                dashboardStatsResponseDto.setIsIncome(isIncome);
                dashboardStatsResponseDto.setTransactions(list1);
                break;
            case "year":
                List<Object[]> transactionSummaryByYearAndCurrency = transactionRepository.getTransactionSummaryByYearAndCurrency(from, to, currency, isIncome);
                if (transactionSummaryByYearAndCurrency.isEmpty()) {
                    throw new NotFoundException("transactions not found");
                }
                List<TransactionSummaryDto> list2 = transactionSummaryByYearAndCurrency.stream().map(v ->
                        new TransactionSummaryDto(v[0].toString(), (String) v[1], (Double) v[2], (Long) v[3])).toList();
                dashboardStatsResponseDto.setCurrency(currency);
                dashboardStatsResponseDto.setDuration(duration);
                dashboardStatsResponseDto.setIsIncome(isIncome);
                dashboardStatsResponseDto.setTransactions(list2);
                break;
            default:
                return ResponseData.errorResponse("Bad request", "/api/dashboard/get-income-outcome-stats", 400);
        }
        return ResponseData.successResponse(dashboardStatsResponseDto);
    }
}
