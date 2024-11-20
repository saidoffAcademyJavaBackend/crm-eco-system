package uz.saidoff.crmecosystem.repository.FromOutcomeAndIncome;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.saidoff.crmecosystem.enums.RoleType;

@Service
@RequiredArgsConstructor
public class UserTransactionRepository {

    private final EntityManager entityManager;

    public Double getTotalSalaryByRoleType(RoleType roleType) {
        String jpql = "SELECT SUM(u.salary) " +
                "FROM users u " +
                "JOIN u.role r " +
                "WHERE r.roleType = :roleType";

        Query query = entityManager.createQuery(jpql);
        query.setParameter("roleType", roleType);

        return (Double) query.getSingleResult();
    }
}
