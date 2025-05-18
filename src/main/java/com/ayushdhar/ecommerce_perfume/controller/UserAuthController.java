package com.ayushdhar.ecommerce_perfume.controller;


import com.ayushdhar.ecommerce_perfume.dto.auth.LoginRequestDTO;
import com.ayushdhar.ecommerce_perfume.lib.Constants;
import com.ayushdhar.ecommerce_perfume.response.ApiResponse;
import com.ayushdhar.ecommerce_perfume.services.UserAuthService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/auth")
public class UserAuthController {

    private final UserAuthService userAuthService;

    public UserAuthController(UserAuthService userAuthService) {
        this.userAuthService = userAuthService;
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<Void>> loginHandler(@Valid @RequestBody  LoginRequestDTO loginRequestDTO) {
        ApiResponse<Void> response = new ApiResponse<>();
        try {
            userAuthService.saveUserByDTO(loginRequestDTO);
            response.setMsg("Login successful");
            return  new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage());
            response.setMsg(Constants.INTERNAL_SERVER_ERROR);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }
}
