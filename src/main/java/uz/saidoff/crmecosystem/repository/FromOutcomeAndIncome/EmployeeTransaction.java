package uz.saidoff.crmecosystem.repository.FromOutcomeAndIncome;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.saidoff.crmecosystem.enums.Currency;
import uz.saidoff.crmecosystem.payload.OutcomeAndIncome.ForEmployees;

import java.sql.Date;

@Service
@RequiredArgsConstructor
public class EmployeeTransaction {

    @Autowired
    private EntityManager entityManager;

    public ForEmployees getEmployeeBreakdownBetweenDates(Date startDate, Date endDate, Currency currency,
                                                        String name, boolean isType) {
        String sql = "SELECT SUM(t.amount) " +
                "FROM Transaction t " +
                "JOIN t.category c " +
                "WHERE t.isIncome = :isType AND t.currency = :currency AND c.name=:name " +
                "AND t.createdAt BETWEEN :startDate AND :endDate ";

        Query query = entityManager.createQuery(sql);
        query.setParameter("startDate", startDate);
        query.setParameter("endDate", endDate);
        query.setParameter("currency", currency);
        query.setParameter("name", name);
        query.setParameter("isType", isType);

        Double totalOutcome = (Double) query.getSingleResult();

        ForEmployees forEmployees = new ForEmployees();
        forEmployees.setPaid(totalOutcome);

        return forEmployees;
    }

}
