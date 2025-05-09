package com.ayushdhar.ecommerce_perfume.dto.admin.category;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Data
@NoArgsConstructor
public class CreateCategoryRequestDTO {
    @NotEmpty(message = "Name is required")
    @Length(min = 3,  message = "Name should be at least 3 characters")
    private String name;
    private String desc;
    @NotNull(message = "Is Tag is required")
    private Boolean isTag;
}
