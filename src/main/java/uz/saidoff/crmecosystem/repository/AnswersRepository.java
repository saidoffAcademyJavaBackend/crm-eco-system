package uz.saidoff.crmecosystem.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import uz.saidoff.crmecosystem.entity.Answers;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface AnswersRepository extends JpaRepository<Answers, UUID> {

    @Modifying
    @Transactional
    @Query("update answers set value =:v, isRightAnswer =:r where id =:id")
    Integer updateAnswersById(@Param("v") String value,
                                        @Param("r") Boolean isRightAnswer,
                                        @Param("id") UUID id);
}
