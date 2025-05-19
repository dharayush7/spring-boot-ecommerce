package com.ayushdhar.ecommerce_perfume.dto.product;


import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class GetProductByIdResponseDTO {
    private String id;
    private String name;
    private String description;
    private Float sellPrice;
    private Float maxPrice;
    private String fragrance;
    private String strength;
    private String preference;
    private String sustainable;
    private String type;
    private String idealFor;
    private Integer quantity;
    private String description2;
    private String description3;
    private List<String> images;
    private List<CategoryDTO> categories;
    private List<CategoryDTO> tags;
}
