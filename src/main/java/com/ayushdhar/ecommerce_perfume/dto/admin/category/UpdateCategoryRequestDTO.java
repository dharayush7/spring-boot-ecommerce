package com.ayushdhar.ecommerce_perfume.dto.admin.category;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class UpdateCategoryRequestDTO  extends CreateCategoryRequestDTO {
    @NotNull(message = "ID is required")
    @NotBlank(message = "ID is required")
    private String id;
}
