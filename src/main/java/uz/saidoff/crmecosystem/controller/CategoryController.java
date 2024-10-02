package uz.saidoff.crmecosystem.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.saidoff.crmecosystem.payload.CategoryRequestDto;
import uz.saidoff.crmecosystem.payload.CategoryUpdateDto;
import uz.saidoff.crmecosystem.response.ResponseData;
import uz.saidoff.crmecosystem.service.CategoryService;
import uz.saidoff.crmecosystem.valid.CheckPermission;

import java.util.UUID;

@RestController
@RequestMapping("/category")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;


    @CheckPermission("CREATE_CATEGORY")
    @PostMapping("/saved-category")
    public ResponseEntity<?> createCategory(@RequestBody CategoryRequestDto categoryRequestDto) {
        ResponseData<?> responseData = categoryService.savedCategory(categoryRequestDto);
        return ResponseEntity.status(responseData.isSuccess() ? 200 : 401).body(responseData);
    }

    @CheckPermission("DELETE_CATEGORY")
    @DeleteMapping("/delete-category/{categoryId}")
    public ResponseEntity<ResponseData<?>> deleteCategory(@PathVariable UUID categoryId) {
        ResponseData<?> responseData = categoryService.deletCategory(categoryId);
        return ResponseEntity.status(responseData.isSuccess() ? 200 : 409).body(responseData);
    }

    @CheckPermission("GET_CATEGORY")
    @GetMapping("/get-category/{categoryId}")
    public ResponseEntity<?> getCategoryOne(@PathVariable UUID categoryId) {
        ResponseData<?> responseData = categoryService.getOneCategory(categoryId);
        return ResponseEntity.status(responseData.isSuccess() ? 200 : 403).body(responseData);
    }

    @CheckPermission("GET_CATEGORY")
    @GetMapping("/get-all-category")
    public ResponseEntity<?> getAllCategory(@RequestParam(defaultValue = "0") int page,
                                            @RequestParam(defaultValue = "10") int size) {
        ResponseData<?> responseData = categoryService.getAll(page, size);
        return ResponseEntity.status(responseData.isSuccess() ? 200 : 403).body(responseData);
    }

    @CheckPermission("UPDATE_CATEGORY")
    @PatchMapping("/update-category/{categoryId}")
    public ResponseEntity<?> updateCategory(@PathVariable UUID categoryId, @RequestBody CategoryUpdateDto categoryUpdateDto) {
        ResponseData<?> responseData = categoryService.categoryUpdate(categoryId, categoryUpdateDto);
        return ResponseEntity.status(responseData.isSuccess() ? 200 : 403).body(responseData);
    }


}
