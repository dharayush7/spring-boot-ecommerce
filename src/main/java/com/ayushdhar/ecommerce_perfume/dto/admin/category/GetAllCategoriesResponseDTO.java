package com.ayushdhar.ecommerce_perfume.dto.admin.category;

import com.ayushdhar.ecommerce_perfume.entity.Category;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class GetAllCategoriesResponseDTO extends Category {
    private Integer count;
}
