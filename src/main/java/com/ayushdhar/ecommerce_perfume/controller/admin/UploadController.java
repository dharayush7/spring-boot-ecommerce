package com.ayushdhar.ecommerce_perfume.controller.admin;

import com.ayushdhar.ecommerce_perfume.dto.admin.upload.UploadRequestDTO;
import com.ayushdhar.ecommerce_perfume.dto.admin.upload.UploadResponseDTO;
import com.ayushdhar.ecommerce_perfume.entity.AdminUser;
import com.ayushdhar.ecommerce_perfume.entity.CarouselImage;
import com.ayushdhar.ecommerce_perfume.entity.ProductImage;
import com.ayushdhar.ecommerce_perfume.lib.Constants;
import com.ayushdhar.ecommerce_perfume.middleware.context.AdminUserContext;
import com.ayushdhar.ecommerce_perfume.response.ApiResponse;
import com.ayushdhar.ecommerce_perfume.services.admin.UploadService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/upload")
@Slf4j
public class UploadController {

    private final UploadService uploadService;

    public UploadController(UploadService uploadService) {
        this.uploadService = uploadService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<UploadResponseDTO>> uploadHandler (@Valid @RequestBody UploadRequestDTO uploadRequestDTO) {
        ApiResponse<UploadResponseDTO> response = new ApiResponse<>();
        try {
            AdminUser admin = uploadService.findAdminUserById(AdminUserContext.get().getId()).get();

            if (admin.getPermission().stream().noneMatch(permission -> permission.equals(Constants.ADMIN) || permission.equals("PRODUCT"))) {
                response.setMsg(Constants.ACCESS_DENIED);
                return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
            }

            ProductImage productImage = uploadService.savaProductImageByDTO(uploadRequestDTO);

            UploadResponseDTO uploadResponseDTO = new UploadResponseDTO();
            uploadResponseDTO.setMediaId(productImage.getId());

            response.setData(uploadResponseDTO);
            response.setMsg("Image uploaded successfully");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage());
            response.setMsg(Constants.INTERNAL_SERVER_ERROR);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/carousel")
    public ResponseEntity<ApiResponse<UploadResponseDTO>> uploadCarouselImageHandler (@Valid @RequestBody UploadRequestDTO uploadRequestDTO) {
        ApiResponse<UploadResponseDTO> response = new ApiResponse<>();
        try {
            AdminUser admin = uploadService.findAdminUserById(AdminUserContext.get().getId()).get();

            if (admin.getPermission().stream().noneMatch(permission -> permission.equals(Constants.ADMIN) || permission.equals("SITE"))) {
                response.setMsg(Constants.ACCESS_DENIED);
                return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
            }

            CarouselImage carouselImage = uploadService.savaCarouselImageByDTO(uploadRequestDTO);

            UploadResponseDTO uploadResponseDTO = new UploadResponseDTO();
            uploadResponseDTO.setMediaId(carouselImage.getId());

            response.setData(uploadResponseDTO);
            response.setMsg("Image uploaded successfully");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage());
            response.setMsg(Constants.INTERNAL_SERVER_ERROR);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
