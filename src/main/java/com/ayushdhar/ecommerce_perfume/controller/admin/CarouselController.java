package com.ayushdhar.ecommerce_perfume.controller.admin;


import com.ayushdhar.ecommerce_perfume.dto.admin.carousel.AddCarouselRequestDTO;
import com.ayushdhar.ecommerce_perfume.dto.admin.carousel.CarouselImageDTO;
import com.ayushdhar.ecommerce_perfume.dto.admin.carousel.GetCarouselResponseDTO;
import com.ayushdhar.ecommerce_perfume.entity.AdminUser;
import com.ayushdhar.ecommerce_perfume.entity.Carousel;
import com.ayushdhar.ecommerce_perfume.lib.Constants;
import com.ayushdhar.ecommerce_perfume.middleware.context.AdminUserContext;
import com.ayushdhar.ecommerce_perfume.response.ApiResponse;
import com.ayushdhar.ecommerce_perfume.services.admin.CarouselService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/admin/carousel")
@Slf4j
public class CarouselController {

    private final CarouselService carouselService;

    public CarouselController(CarouselService carouselService) {
        this.carouselService = carouselService;
    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponse<Void>> addCarousel(@Valid @RequestBody AddCarouselRequestDTO addCarouselRequestDTO) {
        ApiResponse<Void> response = new ApiResponse<>();
        try {
            AdminUser admin = carouselService.findAdminUserById(AdminUserContext.get().getId()).get();

            if (admin.getPermission().stream().noneMatch(permission -> permission.equals(Constants.ADMIN) || permission.equals("SITE"))) {
                response.setMsg(Constants.ACCESS_DENIED);
                return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
            }

            carouselService.saveNewCarouselByDTO(addCarouselRequestDTO);

            response.setMsg("Carousel added successfully");
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            log.error(e.getMessage());
            response.setMsg(Constants.INTERNAL_SERVER_ERROR);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/get")
    public ResponseEntity<ApiResponse<List<GetCarouselResponseDTO>>> getCarousels() {
        ApiResponse<List<GetCarouselResponseDTO>> response = new ApiResponse<>();
        try {
            AdminUser admin = carouselService.findAdminUserById(AdminUserContext.get().getId()).get();

            if (admin.getPermission().stream().noneMatch(permission -> permission.equals(Constants.ADMIN) || permission.equals("SITE"))) {
                response.setMsg(Constants.ACCESS_DENIED);
                return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
            }

            List<Carousel>  carousels = carouselService.findAllCarousels();
            List<GetCarouselResponseDTO> carouselResponseDTOs = new ArrayList<>();

            for (Carousel carousel : carousels) {
                GetCarouselResponseDTO responseDTO = new GetCarouselResponseDTO();
                CarouselImageDTO carouselImageDTO = new CarouselImageDTO();

                carouselImageDTO.setUrl(carousel.getImage().getUrl());

                responseDTO.setId(carousel.getId());
                responseDTO.setLink(carousel.getLink());
                responseDTO.setPosition(carousel.getPosition());
                responseDTO.setIsBlack(carousel.getIsBlack());
                responseDTO.setPreference(carousel.getPreference());
                responseDTO.setImage(carouselImageDTO);

                carouselResponseDTOs.add(responseDTO);
            }

            response.setData(carouselResponseDTOs);
            response.setMsg("Carousels retrieved successfully");

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage());
            response.setMsg(Constants.INTERNAL_SERVER_ERROR);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<ApiResponse<Void>> deleteCarousel(@RequestParam("carouselId") String carouselId) {
        ApiResponse<Void> response = new ApiResponse<>();
        try {
            AdminUser admin = carouselService.findAdminUserById(AdminUserContext.get().getId()).get();

            if (admin.getPermission().stream().noneMatch(permission -> permission.equals(Constants.ADMIN) || permission.equals("SITE"))) {
                response.setMsg(Constants.ACCESS_DENIED);
                return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
            }

            Optional<Carousel> optionalCarousel = carouselService.findCarouselById(carouselId);

            if (optionalCarousel.isEmpty()) {
                response.setMsg("Carousel not found");
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }

            Carousel carousel = optionalCarousel.get();
            carouselService.deleteCarouse(carousel);

            response.setMsg("Carousel deleted successfully");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage());
            response.setMsg(Constants.INTERNAL_SERVER_ERROR);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
