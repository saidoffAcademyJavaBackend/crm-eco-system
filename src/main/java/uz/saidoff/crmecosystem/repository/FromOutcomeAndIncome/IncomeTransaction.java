package uz.saidoff.crmecosystem.repository.FromOutcomeAndIncome;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.saidoff.crmecosystem.enums.Currency;
import uz.saidoff.crmecosystem.payload.OutcomeAndIncome.IncomeHistory;

import java.sql.Date;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class IncomeTransaction {

    @Autowired
    private EntityManager entityManager;

    public IncomeHistory getIncomeBreakdownBetweenDates(Date startDate, Date endDate, Currency currency, String project, String student,
                                                        String intern, String advertisement, String another) {
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

        IncomeHistory incomeHistory = new IncomeHistory();
        incomeHistory.setAllOutcomesSum(totalIncome);

        for (Object[] row : results) {
            String categoryName = (String) row[0];
            double amount = (Double) row[1];
            double percentage = (amount / totalIncome) * 100;

            if (Objects.equals(categoryName, project)) {
                incomeHistory.setFromProject(amount);
                incomeHistory.setInterestOnProject(percentage);
            }
            else if (Objects.equals(categoryName, student)) {
                incomeHistory.setFromStudent(amount);
                incomeHistory.setInterestOnStudent(percentage);
            }
            else if (Objects.equals(categoryName, intern)) {
                incomeHistory.setFromIntern(amount);
                incomeHistory.setInterestOnIntern(percentage);
            }
            else if (Objects.equals(categoryName, advertisement)) {
                incomeHistory.setFromAdvertisement(amount);
                incomeHistory.setInterestOnAdvertisement(percentage);
            }
            else if (Objects.equals(categoryName, another)) {
                incomeHistory.setFromAnother(amount);
                incomeHistory.setInterestOnAnother(percentage);
            }

//            switch (categoryName) {
//                case "Proektlardan":
//                    incomeHistory.setFromProject(amount);
//                    incomeHistory.setInterestOnProject(percentage);
//                    break;
//                case "O'quvchilardan":
//                    incomeHistory.setFromStudent(amount);
//                    incomeHistory.setInterestOnStudent(percentage);
//                    break;
//                case "Amaliyotchilardan":
//                    incomeHistory.setFromIntern(amount);
//                    incomeHistory.setInterestOnIntern(percentage);
//                    break;
//                default:
//                    // Boshqa kategoriyalar uchun qo'shimcha ishlov berishingiz mumkin
//                    break;
//            }
        }

        return incomeHistory;
    }

}
