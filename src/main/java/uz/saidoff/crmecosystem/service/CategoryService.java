package uz.saidoff.crmecosystem.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import uz.saidoff.crmecosystem.entity.Category;
import uz.saidoff.crmecosystem.exception.NotFoundException;
import uz.saidoff.crmecosystem.payload.CategoryRequestDto;
import uz.saidoff.crmecosystem.payload.CategoryUpdateDto;
import uz.saidoff.crmecosystem.repository.CategoryRepository;
import uz.saidoff.crmecosystem.response.ResponseData;

import java.util.*;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;


    public ResponseData<?> savedCategory(CategoryRequestDto categoryRequestDto) {

        Category category = new Category();
        category.setName(categoryRequestDto.getName());
        category.setDescription(categoryRequestDto.getDescription());
        category.setIncome(categoryRequestDto.isIncome());
        category.setCreatedBy(categoryRequestDto.getUserId());
        category.setUpdatedBy(category.getCreatedBy());
        categoryRepository.save(category);
        return ResponseData.successResponse("category saved");
    }

    public ResponseData<?> deletCategory(UUID categoryId) {

        Optional<Category> optionalCategory = categoryRepository.findById(categoryId);
        if (optionalCategory.isEmpty()) {
            throw new NotFoundException("category not found");
        }
        Category category = optionalCategory.get();
        category.setDeleted(true);
        categoryRepository.save(category);
        return ResponseData.successResponse("category succesfuly deleted ");
    }

    public ResponseData<?> getOneCategory(UUID categoryId) {
        Optional<Category> optionalCategory = categoryRepository.findById(categoryId);
        if (optionalCategory.isEmpty()) {
            return ResponseData.errorResponse("category not found", "/get-category", 403);
        }
        Category category = optionalCategory.get();
        return ResponseData.successResponse(category);
    }

    public ResponseData<?> getAll(int page, int size) {
        Pageable pageable = PageRequest.of(size, page);
        Page<Category> categoryPage = categoryRepository.findAllByDeletedFalse(pageable);
        if (categoryPage.isEmpty()) {
            return ResponseData.errorResponse("problema : -> not found category failed ", "/get-all-category", 403);
        }
        Map<String, Object> response = new HashMap<>();
        response.put("data", categoryPage.stream().toList());
        response.put("total", categoryPage.getTotalElements());
        response.put("totalPages", categoryPage.getTotalPages());
        return new ResponseData<>(response, true);
    }

    public ResponseData<?> categoryUpdate(UUID categoryId, CategoryUpdateDto categoryUpdateDto) {
        Optional<Category> optionalCategory = categoryRepository.findById(categoryId);
        if (optionalCategory.isEmpty()) {
            return ResponseData.errorResponse("category not found", "/update-category", 403);
        }
        Category category = optionalCategory.get();
        if (categoryUpdateDto.name() != null) {
            category.setName(categoryUpdateDto.name());
        }
        if (categoryUpdateDto.description() != null) {
            category.setDescription(categoryUpdateDto.description());
        }
        categoryRepository.save(category);

        return ResponseData.successResponse("category succesfuly update ");
    }
}
