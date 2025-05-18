package com.ayushdhar.ecommerce_perfume.dto.admin.upload;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UploadRequestDTO {
    @NotBlank(message = "URL is required")
    private  String url;
}
