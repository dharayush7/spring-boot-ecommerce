package com.ayushdhar.ecommerce_perfume.controller;

import com.ayushdhar.ecommerce_perfume.dto.cart.AddProductToCartRequestDTO;
import com.ayushdhar.ecommerce_perfume.dto.cart.GetCartProductResponseDTO;
import com.ayushdhar.ecommerce_perfume.entity.*;
import com.ayushdhar.ecommerce_perfume.lib.Constants;
import com.ayushdhar.ecommerce_perfume.middleware.context.UserContext;
import com.ayushdhar.ecommerce_perfume.response.ApiResponse;
import com.ayushdhar.ecommerce_perfume.services.CartService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/cart")
@Slf4j
public class CartController {

    private final CartService cartService;
    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponse<Void>> addProductToCart(@Valid @RequestBody AddProductToCartRequestDTO addProductToCartRequestDTO) {
        ApiResponse<Void> response = new ApiResponse<>();
        try {
            User user = UserContext.get();
            ProductOnBucketId productOnBucketId = new ProductOnBucketId();

            productOnBucketId.setProductId(addProductToCartRequestDTO.getProductId());
            productOnBucketId.setUserId(user.getId());

            Optional<ProductOnCart> optionalProductOnCart = cartService.findById(productOnBucketId);

            if (optionalProductOnCart.isPresent()) {
                response.setMsg("Product already added to the cart");
                return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
            }

            cartService.addProductOnCart(productOnBucketId, addProductToCartRequestDTO.getQuantity());
            response.setMsg("Product added to the cart");
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            log.error(e.getMessage());
            response.setMsg(Constants.INTERNAL_SERVER_ERROR);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/get")
    public ResponseEntity<ApiResponse<List<GetCartProductResponseDTO>>> getProductsOnCart() {
        ApiResponse<List<GetCartProductResponseDTO>> response = new ApiResponse<>();
        try {
            User user = UserContext.get();

            List<ProductOnCart> productOnCarts = cartService.getProductsOnCart(user.getId());
            List<GetCartProductResponseDTO> getCartProductResponseDTOs = new ArrayList<>();

            for (ProductOnCart productOnCart : productOnCarts) {

                List<String> images = new ArrayList<>();
                Product product = productOnCart.getProduct();
                GetCartProductResponseDTO getCartProductResponseDTO = new GetCartProductResponseDTO();

                getCartProductResponseDTO.setId(product.getId());
                getCartProductResponseDTO.setName(product.getName());
                getCartProductResponseDTO.setDescription(product.getDescription());
                getCartProductResponseDTO.setMaxPrice(product.getMaxPrice());
                getCartProductResponseDTO.setSellPrice(product.getSellPrice());
                getCartProductResponseDTO.setQuantity(productOnCart.getQuantity());
                getCartProductResponseDTO.setIsSaveForLater(productOnCart.getIsSaveForLater());

                for (ProductImage image : product.getImages()) {
                    images.add(image.getUrl());
                }

                getCartProductResponseDTO.setImages(images);
                getCartProductResponseDTOs.add(getCartProductResponseDTO);
            }

            response.setData(getCartProductResponseDTOs);
            response.setMsg("Successfully added products to the cart");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage());
            response.setMsg(Constants.INTERNAL_SERVER_ERROR);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/increase")
    public ResponseEntity<ApiResponse<Void>> increaseProductQuantityOnCart(@RequestParam String productId) {
        ApiResponse<Void> response = new ApiResponse<>();
        try {
            User user = UserContext.get();
            ProductOnBucketId productOnBucketId = new ProductOnBucketId();

            productOnBucketId.setProductId(productId);
            productOnBucketId.setUserId(user.getId());

            Optional<ProductOnCart> optionalProductOnCart = cartService.findById(productOnBucketId);

            if (optionalProductOnCart.isEmpty()) {
                response.setMsg("Product does not added to the cart");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }

            cartService.increaseProductQuantity(productOnBucketId);
            response.setMsg("Product increased to the cart");
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            log.error(e.getMessage());
            response.setMsg(Constants.INTERNAL_SERVER_ERROR);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/decrease")
    public ResponseEntity<ApiResponse<Void>> decreaseProductQuantityOnCart(@RequestParam String productId) {
        ApiResponse<Void> response = new ApiResponse<>();
        try {
            User user = UserContext.get();
            ProductOnBucketId productOnBucketId = new ProductOnBucketId();

            productOnBucketId.setProductId(productId);
            productOnBucketId.setUserId(user.getId());

            Optional<ProductOnCart> optionalProductOnCart = cartService.findById(productOnBucketId);

            if (optionalProductOnCart.isEmpty()) {
                response.setMsg("Product does not added to the cart");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }

            cartService.decreaseProductQuantity(productOnBucketId);
            response.setMsg("Product decreased to the cart");
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            log.error(e.getMessage());
            response.setMsg(Constants.INTERNAL_SERVER_ERROR);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/switch")
    public ResponseEntity<ApiResponse<Void>> switchProductOnCart(@RequestParam String productId) {
        ApiResponse<Void> response = new ApiResponse<>();
        try {
            User user = UserContext.get();
            ProductOnBucketId productOnBucketId = new ProductOnBucketId();

            productOnBucketId.setProductId(productId);
            productOnBucketId.setUserId(user.getId());

            Optional<ProductOnCart> optionalProductOnCart = cartService.findById(productOnBucketId);

            if (optionalProductOnCart.isEmpty()) {
                response.setMsg("Product does not added to the cart");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }

            cartService.switchTypeOnCart(productOnBucketId);

            response.setMsg("Product switched to the cart");
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            log.error(e.getMessage());
            response.setMsg(Constants.INTERNAL_SERVER_ERROR);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<ApiResponse<Void>> deleteProductOnCart(@RequestParam String productId) {
        ApiResponse<Void> response = new ApiResponse<>();
        try {
            User user = UserContext.get();
            ProductOnBucketId productOnBucketId = new ProductOnBucketId();

            productOnBucketId.setProductId(productId);
            productOnBucketId.setUserId(user.getId());

            Optional<ProductOnCart> optionalProductOnCart = cartService.findById(productOnBucketId);

            if (optionalProductOnCart.isEmpty()) {
                response.setMsg("Product does not added to the cart");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }

            cartService.deleteProductOnCart(productOnBucketId);

            response.setMsg("Product deleted to the cart");
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            log.error(e.getMessage());
            response.setMsg(Constants.INTERNAL_SERVER_ERROR);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}
