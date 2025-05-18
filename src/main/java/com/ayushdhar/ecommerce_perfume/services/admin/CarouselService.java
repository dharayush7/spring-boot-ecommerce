package com.ayushdhar.ecommerce_perfume.services.admin;

import com.ayushdhar.ecommerce_perfume.dto.admin.carousel.AddCarouselRequestDTO;
import com.ayushdhar.ecommerce_perfume.entity.*;
import com.ayushdhar.ecommerce_perfume.repository.AdminUserRepository;
import com.ayushdhar.ecommerce_perfume.repository.CarouselImageRepository;
import com.ayushdhar.ecommerce_perfume.repository.CarouselRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class CarouselService {

    private final AdminUserRepository adminUserRepository;
    private final CarouselImageRepository carouselImageRepository;
    private final CarouselRepository carouselRepository;


    public CarouselService(
            AdminUserRepository adminUserRepository,
            CarouselRepository carouselRepository,
            CarouselImageRepository carouselImageRepository
    ) {
        this.adminUserRepository = adminUserRepository;
        this.carouselRepository = carouselRepository;
        this.carouselImageRepository = carouselImageRepository;
    }


    public Optional<AdminUser> findAdminUserById(String id) {
        return adminUserRepository.findById(id);
    }

    @Transactional
    public void saveNewCarouselByDTO(AddCarouselRequestDTO addCarouselRequestDTO) {

        Carousel carousel = new Carousel();
        carousel.setLink(addCarouselRequestDTO.getLink());
        carousel.setIsBlack(addCarouselRequestDTO.getIsBlack());
        carousel.setPreference(addCarouselRequestDTO.getPreference());

        if (addCarouselRequestDTO.getPosition().equals("LEFT")) {
            carousel.setPosition(CarouselButtonPosition.LEFT);
        } else if (addCarouselRequestDTO.getPosition().equals("RIGHT")) {
            carousel.setPosition(CarouselButtonPosition.RIGHT);
        }
        carouselRepository.save(carousel);

        Optional<CarouselImage> optionalCarouselImage = carouselImageRepository.findById(addCarouselRequestDTO.getImageId());

        if (optionalCarouselImage.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Image Not Found");
        }

        CarouselImage carouselImage = optionalCarouselImage.get();
        carouselImage.setCarouselId(carousel.getId());
        carouselImageRepository.save(carouselImage);
    }

    public List<Carousel> findAllCarousels() {
        return  carouselRepository.findAll();
    }

    public Optional<Carousel> findCarouselById(String id) {
        return carouselRepository.findById(id);
    }

    @Transactional
    public void deleteCarouse(Carousel carousel) {
        carouselRepository.delete(carousel);
    }
}
