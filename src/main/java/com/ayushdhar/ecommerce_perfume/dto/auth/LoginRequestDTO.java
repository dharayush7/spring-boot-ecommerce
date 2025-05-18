package com.ayushdhar.ecommerce_perfume.dto.auth;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Data
@NoArgsConstructor
public class LoginRequestDTO {

    @NotBlank(message = "uid is required")
    private String uid;

    @NotBlank(message = "Mobile number is required")
    @Length(min = 10, max = 16, message = "Mobile number should be between 10 to 16 number")
    private String mobileNo;
}
