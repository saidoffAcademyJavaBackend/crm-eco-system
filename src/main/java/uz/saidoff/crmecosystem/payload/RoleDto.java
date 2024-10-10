package uz.saidoff.crmecosystem.payload;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import uz.saidoff.crmecosystem.enums.RoleType;

import java.sql.Timestamp;
import java.util.UUID;

@Setter
@Getter
public class RoleDto {

    private UUID roleId;
    private String name;
    private RoleType roleType;


}
