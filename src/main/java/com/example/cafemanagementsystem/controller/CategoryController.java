package com.example.cafemanagementsystem.controller;

import com.example.cafemanagementsystem.constants.CafeConstants;
import com.example.cafemanagementsystem.model.Category;
import com.example.cafemanagementsystem.service.CategoryService;
import com.example.cafemanagementsystem.utils.CafeUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/category")
@RequiredArgsConstructor

public class CategoryController {
    private final CategoryService categoryService;
    @PostMapping("/add")
    public ResponseEntity<String> addNewCategory(@RequestBody Map<String,String> categoryRequestDto){
        try {
            return categoryService.addNewCategory(categoryRequestDto);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return CafeUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /*
    While admin is logged in all category is to be shown
    But while user is logged in only so category with product
     */
    @GetMapping("/getAll")
    public ResponseEntity<List<Category>> getAllCategory(@RequestParam(required = false)
                                                         String filterValue){
        try {
            return categoryService.getAllCategory(filterValue);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PostMapping("/update")
    public ResponseEntity<String> updateCategory(@RequestBody(required = true) Map<String,String> categoryRequestDto){
        try {
            return categoryService.updateCategory(categoryRequestDto);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return CafeUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
