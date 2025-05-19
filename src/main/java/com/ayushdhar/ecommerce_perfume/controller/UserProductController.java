package com.ayushdhar.ecommerce_perfume.controller;

import com.ayushdhar.ecommerce_perfume.dto.product.CategoryDTO;
import com.ayushdhar.ecommerce_perfume.dto.product.GetProductByIdResponseDTO;
import com.ayushdhar.ecommerce_perfume.dto.product.GetProductResponseDTO;
import com.ayushdhar.ecommerce_perfume.entity.Product;
import com.ayushdhar.ecommerce_perfume.entity.ProductImage;
import com.ayushdhar.ecommerce_perfume.entity.ProductOnCategories;
import com.ayushdhar.ecommerce_perfume.lib.Constants;
import com.ayushdhar.ecommerce_perfume.response.ApiResponse;
import com.ayushdhar.ecommerce_perfume.services.UserProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/product")
@Slf4j
public class UserProductController {

    private final UserProductService userProductService;

    public UserProductController(UserProductService userProductService) {
        this.userProductService = userProductService;
    }

    @GetMapping("/get")
    public ResponseEntity<ApiResponse<List<GetProductResponseDTO>>> getAllProducts() {
        ApiResponse<List<GetProductResponseDTO>> response = new ApiResponse<>();
        try {
            List<Product> products = userProductService.findAll();
            List<GetProductResponseDTO> getProductResponseDTOS = new ArrayList<>();

            for (Product product : products) {
                GetProductResponseDTO getProductResponseDTO = new GetProductResponseDTO();
                List<String> images = new ArrayList<>();
                List<CategoryDTO> categories = new ArrayList<>();
                List<CategoryDTO> tags = new ArrayList<>();

                getProductResponseDTO.setId(product.getId());
                getProductResponseDTO.setName(product.getName());
                getProductResponseDTO.setDescription(product.getDescription());

                for (ProductImage productImage : product.getImages()) {
                    images.add(productImage.getUrl());
                }

                for (ProductOnCategories productOnCategories: product.getProductOnCategories()) {
                    CategoryDTO categoryDTO = new CategoryDTO();

                    categoryDTO.setId(productOnCategories.getCategory().getId());
                    categoryDTO.setName(productOnCategories.getCategory().getName());
                    if (Boolean.TRUE.equals(productOnCategories.getCategory().getIsTag())) {
                        categories.add(categoryDTO);
                    } else {
                        tags.add(categoryDTO);
                    }
                }

                getProductResponseDTO.setCategories(categories);
                getProductResponseDTO.setTags(tags);
                getProductResponseDTO.setImages(images);

                getProductResponseDTOS.add(getProductResponseDTO);
            }
            response.setData(getProductResponseDTOS);
            response.setMsg("Products found");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage());
            response.setMsg(Constants.INTERNAL_SERVER_ERROR);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<ApiResponse<GetProductByIdResponseDTO>> getProductById(@PathVariable String id) {
        ApiResponse<GetProductByIdResponseDTO> response = new ApiResponse<>();
        try {
            Optional<Product> product = userProductService.findById(id);
            if (product.isEmpty()) {
                response.setMsg("Product not found");
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }
            GetProductByIdResponseDTO getProductByIdRequestDTO = new GetProductByIdResponseDTO();
            List<String> images = new ArrayList<>();
            List<CategoryDTO> categories = new ArrayList<>();
            List<CategoryDTO> tags = new ArrayList<>();

            getProductByIdRequestDTO.setId(product.get().getId());
            getProductByIdRequestDTO.setName(product.get().getName());
            getProductByIdRequestDTO.setDescription(product.get().getDescription());
            getProductByIdRequestDTO.setDescription2(product.get().getDescription2());
            getProductByIdRequestDTO.setDescription3(product.get().getDescription3());
            getProductByIdRequestDTO.setFragrance(product.get().getFragrance());
            getProductByIdRequestDTO.setPreference(product.get().getPreference());
            getProductByIdRequestDTO.setQuantity(product.get().getQuantity());
            getProductByIdRequestDTO.setStrength(product.get().getStrength());
            getProductByIdRequestDTO.setType(product.get().getType());
            getProductByIdRequestDTO.setIdealFor(product.get().getIdealFor());
            getProductByIdRequestDTO.setSustainable(product.get().getSustainable());

            for (ProductImage productImage : product.get().getImages()) {
                images.add(productImage.getUrl());
            }

            for (ProductOnCategories productOnCategories : product.get().getProductOnCategories()) {
                CategoryDTO categoryDTO = new CategoryDTO();
                categoryDTO.setId(productOnCategories.getCategory().getId());
                categoryDTO.setName(productOnCategories.getCategory().getName());
                if (Boolean.TRUE.equals(productOnCategories.getCategory().getIsTag())) {
                    categories.add(categoryDTO);
                } else {
                    tags.add(categoryDTO);
                }
            }

            getProductByIdRequestDTO.setCategories(categories);
            getProductByIdRequestDTO.setTags(tags);
            getProductByIdRequestDTO.setImages(images);

            response.setData(getProductByIdRequestDTO);
            response.setMsg("Product found");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage());
            response.setMsg(Constants.INTERNAL_SERVER_ERROR);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
