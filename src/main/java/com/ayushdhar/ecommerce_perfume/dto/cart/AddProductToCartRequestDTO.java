package com.ayushdhar.ecommerce_perfume.dto.cart;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AddProductToCartRequestDTO {

    @NotBlank(message = "Product Id is required")
    private String productId;
    private int quantity;
}
