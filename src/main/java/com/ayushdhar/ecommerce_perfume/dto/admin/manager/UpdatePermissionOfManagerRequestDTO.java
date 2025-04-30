package com.ayushdhar.ecommerce_perfume.dto.admin.manager;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UpdatePermissionOfManagerRequestDTO {

    @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")
    private String email;

    @NotNull(message = "Permission is required")
    private Boolean admin;

    @NotNull(message = "Permission is required")
    private Boolean product;

    @NotNull(message = "Permission is required")
    private Boolean order;

    @NotNull(message = "Permission is required")
    private Boolean payment;

    @NotNull(message = "Permission is required")
    private Boolean customer;

    @NotNull(message = "Permission is required")
    private Boolean site;
}
