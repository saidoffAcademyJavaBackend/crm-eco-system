package uz.saidoff.crmecosystem.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.saidoff.crmecosystem.entity.Group;
import uz.saidoff.crmecosystem.entity.GroupStudent;
import uz.saidoff.crmecosystem.exception.NotFoundException;
import uz.saidoff.crmecosystem.payload.PaymentForMonthDto.GroupPayDto;
import uz.saidoff.crmecosystem.repository.GroupRepository;
import uz.saidoff.crmecosystem.repository.groupPay.GroupPayRepository;
import uz.saidoff.crmecosystem.response.ResponseData;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GroupPayHistoryService {
    private final GroupPayRepository groupPayRepository;
    private final GroupRepository groupRepository;

    public ResponseData<?> getGroupPayHistory(UUID groupId) {
        Optional<Group> group = groupRepository.findByIdAndDeletedIsFalse(groupId);
        if (group.isEmpty()){
            throw new NotFoundException("Group not found");
        }
        double allIncomeAmount = 0.0;
        for (GroupStudent groupStudent : group.get().getGroupStudents()) {
            allIncomeAmount+=groupStudent.getPaymentAmount();
        }
        GroupPayDto groupPayDto = this.groupPayRepository.groupPayCurrentMonthHistory(groupId, group.get().getGroupStage());
        groupPayDto.setAllIncomeAmount(allIncomeAmount);
        groupPayDto.setAllIncomeUnearnedAmount(allIncomeAmount-groupPayDto.getAllIncomeAmount());
        return ResponseData.successResponse(groupPayDto);
    }
}
