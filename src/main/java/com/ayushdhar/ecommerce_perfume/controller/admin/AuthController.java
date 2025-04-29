package com.ayushdhar.ecommerce_perfume.controller.admin;

import com.ayushdhar.ecommerce_perfume.dto.admin.auth.*;
import com.ayushdhar.ecommerce_perfume.entity.AdminSession;
import com.ayushdhar.ecommerce_perfume.entity.AdminUser;
import com.ayushdhar.ecommerce_perfume.entity.Otp;
import com.ayushdhar.ecommerce_perfume.lib.ComplexOtpGenerator;
import com.ayushdhar.ecommerce_perfume.lib.Constants;
import com.ayushdhar.ecommerce_perfume.lib.Utils;
import com.ayushdhar.ecommerce_perfume.response.ApiResponse;
import com.ayushdhar.ecommerce_perfume.services.AuthService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/admin/auth")
public class AuthController {

    private final AuthService authService;
    public  AuthController(AuthService authService) {
        this.authService = authService;
    }

    private final Utils utils = new Utils();

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponseDTO>> login(@Valid @RequestBody LoginRequestDTO loginRequestDTO) {
        ApiResponse<LoginResponseDTO> apiResponse = new ApiResponse<>();
        try {
            Optional<AdminUser> optionalAdminUser = authService.findByEmail(loginRequestDTO.getEmail());
            if (optionalAdminUser.isEmpty()) {
                apiResponse.setMsg("Wrong email or password");
                return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
            }
            AdminUser adminUser = optionalAdminUser.get();

            if (!utils.matchesPassword(loginRequestDTO.getPassword(), adminUser.getPassword())) {
                apiResponse.setMsg("Wrong email or password");
                return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
            }

            Integer otp = ComplexOtpGenerator.generateOtp();

            authService.deleteAllOtpsForUser(adminUser.getId());
            authService.saveNewOtp(adminUser.getId(), otp);

            log.info("Otp: {}", otp);
            apiResponse.setMsg("Otp sent successfully");

            LoginResponseDTO loginResponseDTO = new LoginResponseDTO();
            loginResponseDTO.setUserId(adminUser.getId());

            apiResponse.setData(loginResponseDTO);

            return new ResponseEntity<>(apiResponse, HttpStatus.OK);

        } catch (Exception e) {
            apiResponse.setMsg(Constants.INTERNAL_SERVER_ERROR);
        log.error(e.getMessage());
        return new ResponseEntity<>(apiResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/resend")
    public ResponseEntity<ApiResponse<Void>> resendOtp(@Valid @RequestBody ResendOtpRequestDTO resendOtpRequestDTO){
        ApiResponse<Void> response = new ApiResponse<>();
        try {
            List<Otp> allOtpsForUser = authService.findAllOtpsForUser(resendOtpRequestDTO.getUserId());
            log.info("OTP: {}", allOtpsForUser.getFirst().getCode());
            response.setMsg("Otp resent successfully");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.setMsg(Constants.INTERNAL_SERVER_ERROR);
            log.error(e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/verification")
    public ResponseEntity<ApiResponse<VerficationResponseDTO>> verification(@Valid @RequestBody VerificationRequestDTO verificationRequestDTO){

        Integer otpCode = Integer.parseInt(verificationRequestDTO.getOtp());
        ApiResponse<VerficationResponseDTO> response = new ApiResponse<>();

        try {
            Optional<AdminUser> optionalAdminUser = authService.findUserById(verificationRequestDTO.getUserId());

            if (optionalAdminUser.isEmpty() || optionalAdminUser.get().getOtps().isEmpty()) {
                response.setMsg("User not found");
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }

            AdminUser adminUser = optionalAdminUser.get();

            List<Otp> otps = adminUser.getOtps();
            otps.sort(Comparator.comparing(Otp::getCreatedAt).reversed());
            Otp otp = otps.getFirst();

            if(Utils.isExpired(otp.getExpireAt())) {
                response.setMsg("Otp expired");
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }

            if (!Objects.equals(otpCode, otp.getCode())) {
                response.setMsg("Wrong otp");
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }

            authService.deleteAllAdminSessionsByAdminUserId(adminUser.getId());
            authService.deleteAllOtpsForUser(adminUser.getId());
            AdminSession adminSession = authService.saveNewAdminSession(adminUser.getId());

            VerficationResponseDTO responseDTO = new VerficationResponseDTO();
            responseDTO.setSessionId(adminSession.getId());
            responseDTO.setPermission(adminUser.getPermission());

            response.setMsg("Login sucesssful");
            response.setData(responseDTO);

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage());
            response.setMsg(Constants.INTERNAL_SERVER_ERROR);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



}
