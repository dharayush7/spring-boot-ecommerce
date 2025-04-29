package com.ayushdhar.ecommerce_perfume.dto.admin.auth;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Data
@NoArgsConstructor
public class ChangePasswordRequestDTO {
    @NotBlank(message = "Password is required")
    @Length(min = 6, max = 6, message = "Password should be 6 digits")
    private String password;
}
