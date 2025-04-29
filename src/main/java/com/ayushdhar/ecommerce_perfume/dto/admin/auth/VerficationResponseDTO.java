package com.ayushdhar.ecommerce_perfume.dto.admin.auth;


import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class VerficationResponseDTO {
    private String sessionId;
    private List<String> permission;

}
