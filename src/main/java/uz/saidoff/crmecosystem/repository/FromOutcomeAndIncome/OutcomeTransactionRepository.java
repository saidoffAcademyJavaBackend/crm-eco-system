package uz.saidoff.crmecosystem.repository.FromOutcomeAndIncome;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.saidoff.crmecosystem.enums.Currency;
import uz.saidoff.crmecosystem.payload.OutcomeAndIncome.OutcomeHistory;
import uz.saidoff.crmecosystem.payload.OutcomeAndIncome.OutcomeHistoryList;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OutcomeTransactionRepository {

    private final EntityManager entityManager;

    public OutcomeHistoryList getOutcomeBreakdownBetweenDates(Date startDate, Date endDate, Currency currency) {
        String sql = "SELECT c.name, SUM(t.amount) " +
                "FROM Transaction t " +
                "JOIN t.category c " +
                "WHERE t.isIncome = false AND t.currency = :currency " +
                "AND t.createdAt BETWEEN :startDate AND :endDate " +
                "GROUP BY c.name";

        Query query = entityManager.createQuery(sql);
        query.setParameter("startDate", startDate);
        query.setParameter("endDate", endDate);
        query.setParameter("currency", currency);

        List<Object[]> results = query.getResultList();

        double totalOutcome = results.stream().mapToDouble(row -> (Double) row[1]).sum();

        return getOutcomeHistoryList(totalOutcome, results);
    }

    private static OutcomeHistoryList getOutcomeHistoryList(double totalOutcome, List<Object[]> results) {
        OutcomeHistoryList outcomeHistoryList = new OutcomeHistoryList();
        outcomeHistoryList.setAllAmount(totalOutcome);

        List<OutcomeHistory> list = new ArrayList<>();

        for (Object[] row : results) {
            String categoryName = (String) row[0];
            double amount = (Double) row[1];
            double percentage = (amount / totalOutcome) * 100;

            OutcomeHistory outcomeHistory = new OutcomeHistory();
            outcomeHistory.setNameCategory(categoryName);
            outcomeHistory.setAmount(amount);
            outcomeHistory.setCategoryInterest(percentage);
            list.add(outcomeHistory);
        }
        outcomeHistoryList.setOutcomeHistory(list);
        return outcomeHistoryList;
    }
}
