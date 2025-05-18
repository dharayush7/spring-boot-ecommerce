package com.ayushdhar.ecommerce_perfume.controller;

import com.ayushdhar.ecommerce_perfume.dto.profile.UpdateProfileRequestDTO;
import com.ayushdhar.ecommerce_perfume.entity.User;
import com.ayushdhar.ecommerce_perfume.lib.Constants;
import com.ayushdhar.ecommerce_perfume.middleware.context.UserContext;
import com.ayushdhar.ecommerce_perfume.response.ApiResponse;
import com.ayushdhar.ecommerce_perfume.services.ProfileService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/profile")
@Slf4j
public class ProfileController {

    private final ProfileService profileService;

    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    @GetMapping("/get")
    public ResponseEntity<ApiResponse<User>> getProfile() {
        User user = UserContext.get();

        ApiResponse<User> response = new ApiResponse<>();
        response.setData(user);
        response.setMsg("Profile successfully retrieved");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/update")
    public ResponseEntity<ApiResponse<Void>> updateProfile(@Valid @RequestBody UpdateProfileRequestDTO updateProfileRequestDTO) {
        ApiResponse<Void>  response = new ApiResponse<>();
        try {
            User user = UserContext.get();

            user.setName(updateProfileRequestDTO.getName());
            user.setEmail(updateProfileRequestDTO.getEmail());

            profileService.updateProfile(user);
            response.setMsg("Profile successfully updated");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage());
            response.setMsg(Constants.INTERNAL_SERVER_ERROR);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
