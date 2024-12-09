package uz.saidoff.crmecosystem.repository.groupPay;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.saidoff.crmecosystem.enums.Currency;
import uz.saidoff.crmecosystem.payload.PaymentForMonthDto.GroupPayDto;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GroupPayRepository {


    private final EntityManager entityManager;

    public GroupPayDto groupPayCurrentMonthHistory(UUID groupId, int groupStage){
        String sql = "SELECT SUM(t.amount) " +
                "FROM Transaction t " +
                "WHERE t.userPayments.group.id =: groupId " +
                "AND t.isIncome = true AND t.currency = :currency " +
                "AND t.userPayments.groupStage = :groupStage ";

        Query query = entityManager.createQuery(sql);
        query.setParameter("groupStage", groupStage);
        query.setParameter("groupId", groupId);
        query.setParameter("currency", Currency.SUM);


        Double totalIncome = (Double) query.getSingleResult();

        GroupPayDto groupPayDto = new GroupPayDto();
        groupPayDto.setAllIncomeReceivedAmount(totalIncome);

        return groupPayDto;
    }

}
