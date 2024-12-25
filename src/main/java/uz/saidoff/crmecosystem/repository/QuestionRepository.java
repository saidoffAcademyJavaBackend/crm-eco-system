package uz.saidoff.crmecosystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import uz.saidoff.crmecosystem.entity.Question;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface QuestionRepository extends JpaRepository<Question, UUID> {

    Optional<Question> findById(UUID id);

    @Modifying
    @Query("update question q set q.deleted = true where q.id = :id")
    void updateByIdAndDeleted(@Param("id") UUID id);

    @Query("select q.id, q.startDate, q.usersIDs from question q where q.startDate =:now")
    List<Question> getQuestions(@Param("now") LocalDateTime now);

}
