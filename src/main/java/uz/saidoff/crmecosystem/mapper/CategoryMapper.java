package uz.saidoff.crmecosystem.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import uz.saidoff.crmecosystem.entity.Category;
import uz.saidoff.crmecosystem.payload.CategoryResponceDto;

@Component
@RequiredArgsConstructor
public class CategoryMapper {


    public CategoryResponceDto toResponseCategory(Category category) {

        CategoryResponceDto responceDto = new CategoryResponceDto();
        responceDto.setCategoryId(category.getId());
        responceDto.setName(category.getName());
        responceDto.setDescription(category.getDescription());
        responceDto.setIncome(category.isIncome());
        responceDto.setDeleted(category.isDeleted());
        responceDto.setCreatedAt(category.getCreatedAt());

        return responceDto;
    }
}
