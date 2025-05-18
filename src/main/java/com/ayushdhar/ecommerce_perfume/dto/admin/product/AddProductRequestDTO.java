package com.ayushdhar.ecommerce_perfume.dto.admin.product;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class AddProductRequestDTO {
    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Description is required")
    private String description;

    @NotBlank(message = "Description2 is required")
    private String description2;

    private String description3;

    private List<@NotBlank String> points;

    @NotNull(message = "Max price is required")
    @DecimalMin(value = "0.0", inclusive = true, message = "Max price must be zero or positive")
    private Float maxPrice;

    @NotNull(message = "Selected price is required")
    @DecimalMin(value = "0.0", inclusive = true, message = "Selected price must be zero or positive")
    private Float sellPrice;

    private String fragrance;

    private String strength;

    private String preference;

    private String sustainable;

    private String type;

    private String idealFor;

    @DecimalMin(value = "0", inclusive = true, message = "Quantity must be zero or positive")
    private Integer quantity;

    @NotNull(message = "Category is required")
    @Size(min = 1, message = "At least one category is required")
    private List<@NotBlank String> category;

    private List<@NotBlank String> images;
}
