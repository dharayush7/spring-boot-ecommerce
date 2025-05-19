package com.ayushdhar.ecommerce_perfume.controller;

import com.ayushdhar.ecommerce_perfume.dto.product.GetProductResponseDTO;
import com.ayushdhar.ecommerce_perfume.entity.Category;
import com.ayushdhar.ecommerce_perfume.entity.ProductImage;
import com.ayushdhar.ecommerce_perfume.entity.ProductOnCategories;
import com.ayushdhar.ecommerce_perfume.lib.Constants;
import com.ayushdhar.ecommerce_perfume.response.ApiResponse;
import com.ayushdhar.ecommerce_perfume.services.UserCategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/category")
@Slf4j
public class UserCategoryController {

    private final UserCategoryService userCategoryService;

    public UserCategoryController(UserCategoryService userCategoryService) {
        this.userCategoryService = userCategoryService;
    }

    @GetMapping("/get")
    public ResponseEntity<ApiResponse<List<Category>>> getAllCategory() {
        ApiResponse<List<Category>> response = new ApiResponse<>();

        try {
            List<Category> categories = userCategoryService.findAll();

            for (Category category : categories) {
                category.setProductOnCategories(null);
            }

            response.setData(categories);
            response.setMsg("Categories fetched successfully");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage());
            response.setMsg(Constants.INTERNAL_SERVER_ERROR);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<ApiResponse<List<GetProductResponseDTO>>> getProductById(@PathVariable String id) {
        ApiResponse<List<GetProductResponseDTO>> response = new ApiResponse<>();
        try {
            Optional<Category> optionalCategory = userCategoryService.findById(id);

            if (optionalCategory.isEmpty()) {
                response.setMsg("Category not found");
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }

            Category category = optionalCategory.get();
            List<GetProductResponseDTO> getProductResponseDTOS = new ArrayList<>();

            for (ProductOnCategories productOnCategories : category.getProductOnCategories()) {
                GetProductResponseDTO getProductResponseDTO = new GetProductResponseDTO();
                List<String> images = new ArrayList<>();

                getProductResponseDTO.setName(productOnCategories.getCategory().getName());
                getProductResponseDTO.setId(productOnCategories.getCategory().getId());
                getProductResponseDTO.setDescription(productOnCategories.getCategory().getDescription());

                for (ProductImage image : productOnCategories.getProduct().getImages()) {
                    images.add(image.getUrl());
                }

                getProductResponseDTO.setImages(images);
                getProductResponseDTOS.add(getProductResponseDTO);
            }

            response.setData(getProductResponseDTOS);
            response.setMsg("Products fetched successfully");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage());
            response.setMsg(Constants.INTERNAL_SERVER_ERROR);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
