package uz.saidoff.crmecosystem.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.saidoff.crmecosystem.exception.NotFoundException;
import uz.saidoff.crmecosystem.payload.OutcomeAndIncome.ForEmployees;
import uz.saidoff.crmecosystem.payload.OutcomeAndIncome.SalaryHistory;
import uz.saidoff.crmecosystem.repository.FromOutcomeAndIncome.EmployeeTransactionRepository;
import uz.saidoff.crmecosystem.repository.FromOutcomeAndIncome.UserTransactionRepository;
import uz.saidoff.crmecosystem.response.ResponseData;

import java.sql.Date;

@Service
@RequiredArgsConstructor
public class BetweenHistoryService {

    private final EmployeeTransactionRepository employeeTransaction;
    private final UserTransactionRepository userTransaction;

    public ResponseData<?> salaryHistory(Date start, Date end, SalaryHistory salaryHistory) {
        Double allSalary = this.userTransaction.getTotalSalaryByRoleType(salaryHistory.getRoleType());
        ForEmployees forEmployees = this.employeeTransaction.getEmployeeBreakdownBetweenDates(start, end,
                salaryHistory.getCurrency(), salaryHistory.getCategoryName(), salaryHistory.isIncome());
        if (forEmployees == null) {
            throw new NotFoundException("Employee not found");
        }
        forEmployees.setAllSum(allSalary);
        forEmployees.setMustBePaid(allSalary - forEmployees.getPaid());
        return ResponseData.successResponse(forEmployees);
    }

}
