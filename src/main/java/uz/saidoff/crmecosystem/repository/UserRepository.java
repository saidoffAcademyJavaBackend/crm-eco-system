package uz.saidoff.crmecosystem.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import uz.saidoff.crmecosystem.entity.Warning;
import uz.saidoff.crmecosystem.entity.auth.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    boolean existsByPhoneNumber(String phoneNumber);
    Optional<User> findByPhoneNumber(String phoneNumber);

    List<User> findByRole_Name(String roleName);

    Optional<User> findByIdAndDeletedFalse(UUID userId);

    @Query("select count(u.warnings) from users as u where u.id =:userId")
    List<User> findAllWarningsByUserUI(@Param("userId") UUID userId);

    List<User> findAllByRoleIdInAndDeletedFalse(List<UUID> roleIds);


}
