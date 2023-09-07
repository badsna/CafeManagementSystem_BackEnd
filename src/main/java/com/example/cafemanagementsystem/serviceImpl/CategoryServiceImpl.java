package com.example.cafemanagementsystem.serviceImpl;

import com.example.cafemanagementsystem.constants.CafeConstants;
import com.example.cafemanagementsystem.jwt.JwtAuthenticationFilter;
import com.example.cafemanagementsystem.model.Category;
import com.example.cafemanagementsystem.repo.CategoryRepo;
import com.example.cafemanagementsystem.service.CategoryService;
import com.example.cafemanagementsystem.utils.CafeUtils;
import com.google.common.base.Strings;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepo categoryRepo;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    @Override
    public ResponseEntity<String> addNewCategory(Map<String, String> categoryRequestDto) {
        try {
            if(jwtAuthenticationFilter.isAdmin()){
                if(validateCategoryMap(categoryRequestDto, false)){

                    categoryRepo.save(getCategoryFromMap(categoryRequestDto,false));
                    return CafeUtils.getResponseEntity("Category Added Successfully",HttpStatus.OK);
                }
            }else{
                return CafeUtils.getResponseEntity(CafeConstants.UNAUTHORIZED_ACCESS,HttpStatus.UNAUTHORIZED);
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return CafeUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private boolean validateCategoryMap(Map<String, String> categoryRequestDto, boolean validateId) {
        if(categoryRequestDto.containsKey("name")){
            if(categoryRequestDto.containsKey("id") && validateId){
                return true;
            }else if (!validateId){
                return true;
            }
        }
        return false;
    }

    private Category getCategoryFromMap(Map<String,String> categoryRequestDto,Boolean isAdd){
        Category category=new Category();
        if(isAdd){
            category.setId(Integer.parseInt(categoryRequestDto.get("id")));
        }
        category.setName(categoryRequestDto.get("name"));
        return category;
    }

    @Override
    public ResponseEntity<List<Category>> getAllCategory(String filterValue) {
        try {
            //for category having products
            if(!Strings.isNullOrEmpty(filterValue) && filterValue.equalsIgnoreCase("true")){
                log.info("inside if");
                return new ResponseEntity<List<Category>>(categoryRepo.getAllCategory(),HttpStatus.OK);
            }
            return new ResponseEntity<>(categoryRepo.findAll(),HttpStatus.OK);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return new ResponseEntity<List<Category>>(new ArrayList<>(),HttpStatus.INTERNAL_SERVER_ERROR);

    }

    @Override
    public ResponseEntity<String> updateCategory(Map<String, String> categoryRequestDto) {
        try {
            if(jwtAuthenticationFilter.isAdmin()){
                //we need id also so true
                if(validateCategoryMap(categoryRequestDto, true)){
                   Optional<Category> category= categoryRepo.findById(Integer.parseInt(categoryRequestDto.get("id")));
                   if(category.isPresent()){
                       categoryRepo.save(getCategoryFromMap(categoryRequestDto,true));
                       return CafeUtils.getResponseEntity("Category Updated Successfully",HttpStatus.OK);
                   }else{
                       return CafeUtils.getResponseEntity("Category id doesn't exists",HttpStatus.OK);
                   }
                }
                return CafeUtils.getResponseEntity(CafeConstants.INVALID_DATA,HttpStatus.BAD_REQUEST);
            }else{
                return CafeUtils.getResponseEntity(CafeConstants.UNAUTHORIZED_ACCESS,HttpStatus.UNAUTHORIZED);
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return CafeUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
