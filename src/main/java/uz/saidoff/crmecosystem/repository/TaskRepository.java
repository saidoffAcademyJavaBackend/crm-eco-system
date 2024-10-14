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
    @Query(value = "update task set task_order=task_order+1 where task_order>=? and task_order<? " +
            "and stage_id=?",
            nativeQuery = true)
    int movingUp(Integer newPositionOrder, Integer previousPositionOrder, UUID stageId);


    @Modifying
    @Query(value = "update task set task_order=task_order-1 where task_order<=? and task_order>? " +
            "and stage_id=?",
            nativeQuery = true)
    int movingDown(Integer newPositionOrder, Integer previousPositionOrder, UUID stageId);


    @Modifying
    @Query(value = "update task set task_order=task_order+1 where task_order>=? and stage_id=?", nativeQuery = true)
    int addingToOtherStage(Integer newPositionOrder, UUID newStageId);


    @Modifying
    @Query(value = "update task set task_order=task_order-1 where task_order>? and stage_id=?", nativeQuery = true)
    int removingToOtherStage(Integer oldPositionOrder, UUID oldStageId);

}
