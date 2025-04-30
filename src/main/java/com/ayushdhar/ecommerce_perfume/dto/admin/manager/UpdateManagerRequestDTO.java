package com.ayushdhar.ecommerce_perfume.dto.admin.manager;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Data
@NoArgsConstructor
public class UpdateManagerRequestDTO {
    @NotEmpty(message = "Name is required")
    @Length(min = 3,  message = "Name should be at least 3 characters")
    private String name;
    @NotEmpty(message = "Email is required")
    @Email(message = "Email should be valid")
    private String email;
    @NotEmpty(message = "Email is required")
    @Email(message = "Email should be valid")
    private String oldEmail;
}
