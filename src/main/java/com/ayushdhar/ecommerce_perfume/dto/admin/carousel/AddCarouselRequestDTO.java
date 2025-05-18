package com.ayushdhar.ecommerce_perfume.dto.admin.carousel;


import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddCarouselRequestDTO {

    @NotBlank(message = "Invalid image")
    private String imageId;

    private Integer preference;

    @NotBlank(message = "Link is required")
    private String link;

    private Boolean isBlack;

    @NotBlank(message = "Position is required")
    private String position;
}
