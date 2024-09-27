package uz.saidoff.crmecosystem.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.saidoff.crmecosystem.entity.auth.User;

import java.util.UUID;

public interface InternsRepository extends JpaRepository<User, UUID> {
    @Query(value = "select u.account_non_expired, u.account_non_expired_or_credentials_non_expired,\n" +
            "       u.account_non_locked, u.credentials_non_expired, u.deleted,\n" +
            "       u.enabled, u.monthly_payment, u.salary, u.birth_date, u.created_at,\n" +
            "       u.start_work, u.updated_at, u.created_by, u.group_id, u.id,\n" +
            "       u.role_id, u.updated_by, u.added_by, u.attached_mentor, u.birth_place,\n" +
            "       u.current_residence, u.father_name, u.first_name, u.last_name,\n" +
            "       u.passport_series, u.password, u.phone_number, u.second_phone_number, u.specialty\n" +
            "    from users as u\n" +
            "    left join role as r on u.role_id = r.id where r.role_type=?1", nativeQuery = true)
    Page<User> findAllInternsPageable(String type,Pageable pageable);
}
