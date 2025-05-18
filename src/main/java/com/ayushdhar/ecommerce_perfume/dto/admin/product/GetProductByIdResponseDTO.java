package com.ayushdhar.ecommerce_perfume.dto.admin.product;

import com.ayushdhar.ecommerce_perfume.entity.ProductImage;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
public class GetProductByIdResponseDTO {

    private String id;
    private String name;
    private String description;
    private String description2;
    private String description3;
    private List<String> points;
    private Float maxPrice;
    private Float sellPrice;
    private String fragrance;
    private String strength;
    private String preference;
    private String sustainable;
    private String type;
    private String idealFor;
    private Integer quantity;
    private Boolean isLive = false;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<ProductImage> images;
    private List<String> categories;
}
