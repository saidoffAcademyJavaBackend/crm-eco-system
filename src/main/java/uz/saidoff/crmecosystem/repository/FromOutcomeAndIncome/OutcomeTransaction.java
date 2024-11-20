package uz.saidoff.crmecosystem.repository.FromOutcomeAndIncome;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.saidoff.crmecosystem.enums.Currency;
import uz.saidoff.crmecosystem.payload.OutcomeAndIncome.OutcomeHistory;

import java.sql.Date;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class OutcomeTransaction {

    @Autowired
    private EntityManager entityManager;

    public OutcomeHistory getOutcomeBreakdownBetweenDates(Date startDate, Date endDate, Currency currency,
                                                          String employee, String advertisement, String another) {
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

        OutcomeHistory outcomeHistory = new OutcomeHistory();
        outcomeHistory.setAllOutcomesSum(totalOutcome);

        for (Object[] row : results) {
            String categoryName = (String) row[0];
            double amount = (Double) row[1];
            double percentage = (amount / totalOutcome) * 100;

            if (Objects.equals(categoryName, employee)) {
                outcomeHistory.setFromEmployee(amount);
                outcomeHistory.setInterestOnEmployee(percentage);
            }
            else if (Objects.equals(categoryName, advertisement)) {
                outcomeHistory.setFromAdvertisement(amount);
                outcomeHistory.setInterestOnAdvertisement(percentage);
            }
            else if (Objects.equals(categoryName, another)) {
                outcomeHistory.setFromAnother(amount);
                outcomeHistory.setInterestOnAnother(percentage);
            }
        }

        return outcomeHistory;
    }
}
