package uz.saidoff.crmecosystem.repository.FromOutcomeAndIncome;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.saidoff.crmecosystem.enums.Currency;
import uz.saidoff.crmecosystem.payload.OutcomeAndIncome.IncomeHistory;
import uz.saidoff.crmecosystem.payload.OutcomeAndIncome.IncomeHistoryList;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class IncomeTransactionRepository {

    private final EntityManager entityManager;

    public IncomeHistoryList getIncomeBreakdownBetweenDates(Date startDate, Date endDate, Currency currency) {
        String sql = "SELECT c.name, SUM(t.amount) " +
                "FROM Transaction t " +
                "JOIN t.category c " +
                "WHERE t.isIncome = true AND t.currency = :currency " +
                "AND t.createdAt BETWEEN :startDate AND :endDate " +
                "GROUP BY c.name";

        Query query = entityManager.createQuery(sql);
        query.setParameter("startDate", startDate);
        query.setParameter("endDate", endDate);
        query.setParameter("currency", currency);

        List<Object[]> results = query.getResultList();

        double totalIncome = results.stream().mapToDouble(row -> (Double) row[1]).sum();

        return getIncomeHistoryList(totalIncome, results);
    }

    private static IncomeHistoryList getIncomeHistoryList(double totalIncome, List<Object[]> results) {
        IncomeHistoryList incomeHistoryList = new IncomeHistoryList();
        incomeHistoryList.setAllAmount(totalIncome);

        List<IncomeHistory> list = new ArrayList<>();

        for (Object[] row : results) {
            String categoryName = (String) row[0];
            double amount = (Double) row[1];
            double percentage = (amount / totalIncome) * 100;

            IncomeHistory incomeHistory = new IncomeHistory();
            incomeHistory.setAmount(amount);
            incomeHistory.setNameCategory(categoryName);
            incomeHistory.setCategoryInterest(percentage);
            list.add(incomeHistory);
        }

        incomeHistoryList.setIncomeHistory(list);
        return incomeHistoryList;
    }

}
