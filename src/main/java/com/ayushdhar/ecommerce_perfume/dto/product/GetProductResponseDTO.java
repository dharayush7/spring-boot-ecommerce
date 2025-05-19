package com.ayushdhar.ecommerce_perfume.dto.product;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class GetProductResponseDTO {
    private String id;
    private String name;
    private String description;
    private Float sellPrice;
    private Float maxPrice;
    private List<String> images;
    private List<CategoryDTO> categories;
    private List<CategoryDTO> tags;
}
