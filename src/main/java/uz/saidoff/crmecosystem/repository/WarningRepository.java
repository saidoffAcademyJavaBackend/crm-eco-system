package uz.saidoff.crmecosystem.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.saidoff.crmecosystem.entity.Warning;

import java.util.List;
import java.util.UUID;

@Repository
public interface WarningRepository extends JpaRepository<Warning, UUID> {

    List<Warning> findAllByPunishmentIsTrue();

}
