package com.ayushdhar.ecommerce_perfume.dto.admin.auth;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Data
@NoArgsConstructor
public class VerificationRequestDTO {
    @NotBlank(message = "ID is empty")
    private String userId;
    @NotBlank(message = "OTP is required")
    @Length(min = 6, max = 6, message = "OTP should be 6 digits")
    private String otp;
}
