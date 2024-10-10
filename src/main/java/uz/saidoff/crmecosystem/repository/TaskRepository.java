package uz.saidoff.crmecosystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import uz.saidoff.crmecosystem.entity.Stage;
import uz.saidoff.crmecosystem.entity.Task;

import java.util.List;
import java.util.UUID;

public interface TaskRepository extends JpaRepository<Task, UUID> {
    List<Task> findAllByStageIn(List<Stage> stages);

    @Modifying
    @Query(value = "update task set position_order=position_order+1 where position_order>=? and position_order<? " +
            "and stage_id=?",
            nativeQuery = true)

    Boolean movingUp(Integer newPositionOrder, Integer previousPositionOrder, UUID stageId);


    @Modifying
    @Query(value = "update task set position_order=position_order-1 where position_order<=? and position_order>? " +
            "and stage_id=?",
            nativeQuery = true)

    Boolean movingDown(Integer newPositionOrder, Integer previousPositionOrder, UUID stageId);

}
