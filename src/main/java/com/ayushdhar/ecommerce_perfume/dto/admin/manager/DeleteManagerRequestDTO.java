package com.ayushdhar.ecommerce_perfume.dto.admin.manager;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DeleteManagerRequestDTO {
    @Email(message = "Email should be valid")
    @NotBlank(message = "Email is required")
    private String email;
}
