package com.ayushdhar.ecommerce_perfume.dto.admin.auth;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ResendOtpRequestDTO {
    @NotBlank(message = "ID is empty")
    private String userId;
}
