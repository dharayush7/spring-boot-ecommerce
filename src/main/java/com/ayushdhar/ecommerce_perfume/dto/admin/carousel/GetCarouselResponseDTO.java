package com.ayushdhar.ecommerce_perfume.dto.admin.carousel;

import com.ayushdhar.ecommerce_perfume.entity.CarouselButtonPosition;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class GetCarouselResponseDTO {
    private String id;
    private String link;
    private Integer preference;
    private Boolean isBlack;
    private CarouselButtonPosition position;
    private CarouselImageDTO image;
}
