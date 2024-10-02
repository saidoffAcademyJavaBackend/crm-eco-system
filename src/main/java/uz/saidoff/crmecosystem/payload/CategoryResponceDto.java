package uz.saidoff.crmecosystem.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CategoryResponceDto {

    private UUID categoryId;
    private String name;
    private String description;
    private boolean isIncome;
    private Timestamp createdAt;
    private boolean deleted;

}
