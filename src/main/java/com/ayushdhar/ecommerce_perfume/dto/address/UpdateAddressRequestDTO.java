package com.ayushdhar.ecommerce_perfume.dto.address;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;


@Data
@NoArgsConstructor
public class UpdateAddressRequestDTO {
    @NotBlank(message = "Id is required")
    private String id;

    @NotBlank(message = "first name is required")
    private String firstName;

    @NotBlank(message = "Last name is required")
    private String lastName;

    @NotBlank(message = "Phone number is required")
    @Length(min = 10, max = 16, message = "Phone number should be valid")
    private String phoneNumber;

    private String alternatePhoneNumber;

    @NotBlank(message = "Address Line 1 is required")
    private String address1;

    private String address2;

    private String landmark;

    @NotBlank(message = "City is required")
    private String city;

    @NotBlank(message = "State is required")
    private String state;

    @NotBlank(message = "Country is required")
    private String country;

    @NotNull(message = "Post code is required")
    private Integer postCode;

}
