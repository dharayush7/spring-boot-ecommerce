package com.ayushdhar.ecommerce_perfume.controller;

import com.ayushdhar.ecommerce_perfume.dto.product.GetProductResponseDTO;
import com.ayushdhar.ecommerce_perfume.entity.*;
import com.ayushdhar.ecommerce_perfume.lib.Constants;
import com.ayushdhar.ecommerce_perfume.middleware.context.UserContext;
import com.ayushdhar.ecommerce_perfume.response.ApiResponse;
import com.ayushdhar.ecommerce_perfume.services.WishlistService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/wishlist")
@Slf4j
public class WishlistController {

    private final WishlistService wishlistService;

    public WishlistController(WishlistService wishlistService) {
        this.wishlistService = wishlistService;
    }

    @GetMapping("/curd")
    public ResponseEntity<ApiResponse<Void>> curdWishlist(@RequestParam String productId) {
        ApiResponse<Void> response = new ApiResponse<>();

        try {
            User user = UserContext.get();
            ProductOnBucketId productOnBucketId = new ProductOnBucketId();

            productOnBucketId.setProductId(productId);
            productOnBucketId.setUserId(user.getId());

            Optional<ProductOnWishList> optionalProductOnWishList = wishlistService.findProductOnWishlistById(productOnBucketId);

            if (optionalProductOnWishList.isPresent()) {
                wishlistService.deleteProductOnWishListById(optionalProductOnWishList.get().getId());
            } else {
                wishlistService.saveNewProductOnWishList(productOnBucketId);
            }

            response.setMsg("Updated product on wishlist");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage());
            response.setMsg(Constants.INTERNAL_SERVER_ERROR);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/get")
    public ResponseEntity<ApiResponse<List<GetProductResponseDTO>>> getProductsOnWishlist() {
        ApiResponse<List<GetProductResponseDTO>> response = new ApiResponse<>();
        try {
            User user = UserContext.get();

            List<ProductOnWishList> productOnWishLists = wishlistService.findAllProductOnWishListByUserId(user.getId());

            List<GetProductResponseDTO> getProductResponseDTOS = new ArrayList<>();

            for (ProductOnWishList productOnWishList : productOnWishLists) {

                GetProductResponseDTO getProductResponseDTO = new GetProductResponseDTO();
                Product product = productOnWishList.getProduct();
                List<String> images = new ArrayList<>();

                getProductResponseDTO.setId(product.getId());
                getProductResponseDTO.setName(product.getName());
                getProductResponseDTO.setSellPrice(product.getSellPrice());
                getProductResponseDTO.setDescription(product.getDescription());
                getProductResponseDTO.setMaxPrice(product.getMaxPrice());

                for (ProductImage productImage : product.getImages()) {
                    images.add(productImage.getUrl());
                }

                getProductResponseDTO.setImages(images);
                getProductResponseDTOS.add(getProductResponseDTO);
            }

            response.setData(getProductResponseDTOS);
            response.setMsg("Get product on wishlist");

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage());
            response.setMsg(Constants.INTERNAL_SERVER_ERROR);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
