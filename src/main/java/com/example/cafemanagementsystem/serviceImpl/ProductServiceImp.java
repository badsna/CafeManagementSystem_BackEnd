package com.example.cafemanagementsystem.serviceImpl;

import com.example.cafemanagementsystem.constants.CafeConstants;
import com.example.cafemanagementsystem.jwt.JwtAuthenticationFilter;
import com.example.cafemanagementsystem.model.Category;
import com.example.cafemanagementsystem.model.Product;
import com.example.cafemanagementsystem.repo.ProductRepo;
import com.example.cafemanagementsystem.service.ProductService;
import com.example.cafemanagementsystem.utils.CafeUtils;
import com.example.cafemanagementsystem.wrapper.ProductWrapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
public class ProductServiceImp implements ProductService {
    private final ProductRepo productRepo;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    Logger log = LoggerFactory.getLogger(BillServiceImpl.class);

    @Override
    public ResponseEntity<String> addNewProduct(Map<String, String> productRequestDto) {
        log.info("Inside addNewProduct");
        try {
            if (jwtAuthenticationFilter.isAdmin()) {

                if (validateProductMap(productRequestDto, false)) {

                    productRepo.save(getProductFromMap(productRequestDto, false));

                    return CafeUtils.getResponseEntity("Product Added Successfully", HttpStatus.OK);
                }
                return CafeUtils.getResponseEntity(CafeConstants.INVALID_DATA, HttpStatus.BAD_REQUEST);
            } else {
                return CafeUtils.getResponseEntity(CafeConstants.UNAUTHORIZED_ACCESS, HttpStatus.UNAUTHORIZED);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return CafeUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private boolean validateProductMap(Map<String, String> productRequestDto, boolean validateId) {
       log.info("Inside validateProductMap()");

        if (productRequestDto.containsKey("name")) {
            if (productRequestDto.containsKey("id") && validateId) {
                return true;
            } else if (!validateId) {
                return true;
            }
        }
        return false;
    }

    private Product getProductFromMap(Map<String, String> productRequestDto, Boolean isAdd) {
       log.info("Inside getProductFromMap()");

        Category category = new Category();
        category.setId(Integer.parseInt(productRequestDto.get("categoryId")));

        Product product = new Product();
        if (isAdd) {
            product.setId(Integer.parseInt(productRequestDto.get("id")));
        } else {
            product.setStatus("true");
        }

        product.setCategory(category);
        product.setName(productRequestDto.get("name"));
        product.setDescription(productRequestDto.get("description"));
        product.setPrice(Integer.parseInt(productRequestDto.get("price")));

        return product;
    }

    @Override
    public ResponseEntity<List<ProductWrapper>> getAllProduct() {
        log.info("Inside getAllProduct()");
        try {
            List<Product> product = productRepo.findAll();
            return new ResponseEntity<>(mapToProductWrapper(product), HttpStatus.OK);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return new ResponseEntity<List<ProductWrapper>>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);

    }

    private List<ProductWrapper> mapToProductWrapper(List<Product> productList) {
        log.info("Inside mapToProductWrapper()");

        List<ProductWrapper> productWrapperList = new ArrayList<>();
        for (Product product : productList) {
            ProductWrapper productWrapper = new ProductWrapper();
            productWrapper.setName(product.getName());
            productWrapper.setStatus(product.getStatus());
            productWrapper.setPrice(product.getPrice());
            productWrapper.setDescription(product.getDescription());
            productWrapper.setId(product.getId());
            productWrapper.setCategoryId(product.getCategory().getId());
            productWrapper.setCategoryName(product.getCategory().getName());

            productWrapperList.add(productWrapper);
        }
        return productWrapperList;
    }

    @Override
    public ResponseEntity<String> updateProduct(Map<String, String> productRequestDto) {
       log.info("Inside updateProduct()");

       try {
            if (jwtAuthenticationFilter.isAdmin()) {
                //we need id also so true

                if (validateProductMap(productRequestDto, true)) {
                    Optional<Product> product = productRepo.findById(Integer.parseInt(productRequestDto.get("id")));

                    if (product.isPresent()) {

                        Product product1 = getProductFromMap(productRequestDto, true);
                        product1.setStatus(product.get().getStatus());
                        productRepo.save(product1);

                        return CafeUtils.getResponseEntity("Product Updated Successfully", HttpStatus.OK);
                    } else {
                        return CafeUtils.getResponseEntity("Product id doesn't exists", HttpStatus.OK);
                    }
                }
                return CafeUtils.getResponseEntity(CafeConstants.INVALID_DATA, HttpStatus.BAD_REQUEST);
            } else {
                return CafeUtils.getResponseEntity(CafeConstants.UNAUTHORIZED_ACCESS, HttpStatus.UNAUTHORIZED);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return CafeUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> deleteProduct(Integer id) {
        log.info("Inside deleteProduct()");
        try {
            if (jwtAuthenticationFilter.isAdmin()) {

                Optional<Product> product = productRepo.findById(id);

                if (product.isPresent()) {

                    product.get().getStatus();
                    productRepo.deleteById(id);
                    return CafeUtils.getResponseEntity("Product Deleted Successfully", HttpStatus.OK);
                }

                return CafeUtils.getResponseEntity("Product id doesnt exists.", HttpStatus.OK);
            } else {
                return CafeUtils.getResponseEntity(CafeConstants.UNAUTHORIZED_ACCESS, HttpStatus.UNAUTHORIZED);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return CafeUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> updateStatus(Map<String, String> productRequestDto) {
        log.info("Inside updateStatus()");
        try {
            if (jwtAuthenticationFilter.isAdmin()) {
                Optional<Product> product = productRepo.findById(Integer.parseInt(productRequestDto.get("id")));
                if (product.isPresent()) {
                    productRepo.updateProductStatus(productRequestDto.get("status"), Integer.parseInt(productRequestDto.get("id")));

                    return CafeUtils.getResponseEntity("Product Status Updated Successfully", HttpStatus.OK);
                }
                return CafeUtils.getResponseEntity("Product id doesn't exists", HttpStatus.OK);
            } else {
                return CafeUtils.getResponseEntity(CafeConstants.UNAUTHORIZED_ACCESS, HttpStatus.UNAUTHORIZED);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return CafeUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<List<ProductWrapper>> getByCategoryId(Integer id) {
        log.info("Inside getByCategoryId");

        try {
            List<Product> product = productRepo.findByCategory_Id(id);
            return new ResponseEntity<>(mapToProductWrapper(product), HttpStatus.OK);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return new ResponseEntity<List<ProductWrapper>>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<ProductWrapper> getByProductId(Integer id) {
       log.info("Inside getByProductId()");
        try {
            Optional<Product> product = productRepo.findById(id);
            if (product.isPresent()) {
                ProductWrapper productWrapper = new ProductWrapper();
                productWrapper.setId(product.get().getId());
                productWrapper.setName(product.get().getName());
                productWrapper.setDescription(product.get().getDescription());
                productWrapper.setPrice(product.get().getPrice());
                return new ResponseEntity<>(productWrapper, HttpStatus.OK);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return new ResponseEntity<>(new ProductWrapper(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
