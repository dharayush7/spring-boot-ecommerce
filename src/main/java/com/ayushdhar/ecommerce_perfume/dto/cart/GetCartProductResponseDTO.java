package com.ayushdhar.ecommerce_perfume.dto.cart;


import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class GetCartProductResponseDTO {
    private String id;
    private String name;
    private String description;
    private Float sellPrice;
    private Float maxPrice;
    private int quantity;
    private Boolean isSaveForLater;
    private List<String> images;
}
