package uz.saidoff.crmecosystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.saidoff.crmecosystem.entity.News;
import uz.saidoff.crmecosystem.entity.auth.Role;

import java.util.List;
import java.util.UUID;

public interface NewsRepository extends JpaRepository<News, UUID> {
    List<News> findByRoles(Role role);

    @Query(value = "select n.id ,n.created_at,n.created_by,n.updated_at,n.updated_by,n.deleted,n.title,n.content,roles_id,n.attachment_id from news as n left join public.news_roles nr on n.id = nr.news_id\n" +
            "    left join role as r on nr.roles_id = r.id where role_type=?1 and n.deleted=false order by n.created_at desc limit  ?2 offset ?3 ",nativeQuery = true )
    List<News> findByRolesAndNewsId(String roleType, int size, int offset);
}
