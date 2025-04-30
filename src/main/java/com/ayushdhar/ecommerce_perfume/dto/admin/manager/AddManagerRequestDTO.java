package com.ayushdhar.ecommerce_perfume.dto.admin.manager;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Data
@NoArgsConstructor
public class AddManagerRequestDTO {
    @NotNull(message = "Name is required")
    @Length(min = 3,  message = "Name should be at least 3 characters")
    private String name;
    @NotNull(message = "Email is required")
    @Email(message = "Email should be valid")
    private String email;
    @NotNull(message = "Password is required")
    @Length(min = 6,  message = "Password should be at least 6 characters")
    private String password;
    @NotNull(message = "Mobile No is required")
    @Length(min = 10,  message = "Mobile No should be at least 10 characters")
    private String mobileNo;
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
