package com.ayushdhar.ecommerce_perfume.controller.admin;

import com.ayushdhar.ecommerce_perfume.dto.admin.auth.*;
import com.ayushdhar.ecommerce_perfume.entity.AdminRestPasswordSession;
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
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static com.ayushdhar.ecommerce_perfume.lib.Utils.generateCuid;

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

            Optional<AdminSession> optionalAdminSession = authService.findAdminSessionByAdminUserId(adminUser.getId());

            AdminSession adminSession;

            VerficationResponseDTO responseDTO = new VerficationResponseDTO();
            if (optionalAdminSession.isPresent()) {
                adminSession = optionalAdminSession.get();
                adminSession.setSessionToken(generateCuid());

                LocalDateTime now = LocalDateTime.now();
                LocalDateTime oneHourLater = now.plusHours(1);

                adminSession.setExpireAt(oneHourLater);
                authService.updateAdminSession(adminSession);
            }
                else {
                adminSession = authService.saveNewAdminSession(adminUser.getId());
            }
            responseDTO.setSessionId(adminSession.getSessionToken());
            responseDTO.setPermission(adminUser.getPermission());
            authService.deleteAllOtpsForUser(adminUser.getId());


            response.setMsg("Login successful");
            response.setData(responseDTO);

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage());
            response.setMsg(Constants.INTERNAL_SERVER_ERROR);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/forget-password")
    public ResponseEntity<ApiResponse<ForgetOtpResponseDTO>> forgetPassword(@Valid @RequestBody ForgetOtpRequestDTO forgetOtpRequestDTO) {
        ApiResponse<ForgetOtpResponseDTO> response = new ApiResponse<>();
        try {
            Optional<AdminUser> optionalAdminUser = authService.findByEmail(forgetOtpRequestDTO.getEmail());

            if (optionalAdminUser.isEmpty()) {
                response.setMsg("User not found");
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }

            AdminUser adminUser = optionalAdminUser.get();

            authService.deleteAllOtpsForUser(adminUser.getId());
            authService.deleteAllAdminRestPasswordSessionsByAdminUserId(adminUser.getId());

            AdminRestPasswordSession adminRestPasswordSession = authService.saveNewAdminRestPasswordSession(adminUser.getId());

            Integer otp = ComplexOtpGenerator.generateOtp();
            authService.saveNewOtpForRestPasswordSession(adminUser.getId(), otp, adminRestPasswordSession.getId());

            ForgetOtpResponseDTO forgetOtpResponseDTO = new ForgetOtpResponseDTO();
            forgetOtpResponseDTO.setRestSessionId(adminRestPasswordSession.getId());

            response.setData(forgetOtpResponseDTO);
            response.setMsg("Otp sent successfully");
            log.info("Otp: {}", otp);
            return new ResponseEntity<>(response, HttpStatus.OK);

        } catch (Exception e) {
            log.error(e.getMessage());
            response.setMsg(Constants.INTERNAL_SERVER_ERROR);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/verify-opts")
    public ResponseEntity<ApiResponse<Void>> verifyOtps (@RequestHeader("Authorization") String authHeader, @RequestBody VerifyOtpsRequestDTO verifyOtpsRequestDTO) {
        ApiResponse<Void> response = new ApiResponse<>();
        try {
            Optional<AdminRestPasswordSession> optionalAdminRestPasswordSession = authService.findAdminRestPasswordSessionById(authHeader.split(" ")[1]);

            if (optionalAdminRestPasswordSession.isEmpty()) {
                response.setMsg(Constants.SESSION_NOT_FOUND);
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }

            AdminRestPasswordSession adminRestPasswordSession = optionalAdminRestPasswordSession.get();

            if (Utils.isExpired(adminRestPasswordSession.getExpireAt())) {
                response.setMsg("Session expired");
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }

            List<Otp> otps = adminRestPasswordSession.getAdminUser().getOtps();
            otps.sort(Comparator.comparing(Otp::getCreatedAt).reversed());
            Otp otp = otps.getFirst();

            if (!Objects.equals(verifyOtpsRequestDTO.getOtp(), otp.getCode().toString())) {
                response.setMsg("Wrong otp");
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }

            authService.deleteAllOtpsForUser(adminRestPasswordSession.getAdminUser().getId());

            adminRestPasswordSession.setIsVarfied(true);
            authService.updateAdminRestPasswordSession(adminRestPasswordSession);

            response.setMsg("Otp verified successfully");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage());
            response.setMsg(Constants.INTERNAL_SERVER_ERROR);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/change-password")
    public  ResponseEntity<ApiResponse<Void>> changePassword(@RequestHeader("Authorization") String authHeader, @RequestBody ChangePasswordRequestDTO changePasswordRequestDTO){
        ApiResponse<Void> response = new ApiResponse<>();
        try {
            Optional<AdminRestPasswordSession> optionalAdminRestPasswordSession = authService.findAdminRestPasswordSessionById(authHeader.split(" ")[1]);

            if (optionalAdminRestPasswordSession.isEmpty()) {
                response.setMsg(Constants.SESSION_NOT_FOUND);
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }

            AdminRestPasswordSession adminRestPasswordSession = optionalAdminRestPasswordSession.get();

            if (Utils.isExpired(adminRestPasswordSession.getExpireAt())) {
                response.setMsg("Session expired");
            }

            if (Boolean.FALSE.equals(adminRestPasswordSession.getIsVarfied()) || Boolean.TRUE.equals(adminRestPasswordSession.getIsChanged())) {
                response.setMsg(Constants.SESSION_NOT_FOUND);
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }

            AdminUser adminUser = adminRestPasswordSession.getAdminUser();
            adminUser.setPassword(utils.encodePassword(changePasswordRequestDTO.getPassword()));
            authService.updateAdminUser(adminUser);

            adminRestPasswordSession.setIsChanged(true);
            authService.updateAdminRestPasswordSession(adminRestPasswordSession);

            response.setMsg("Password changed successfully");
            return new ResponseEntity<>(response, HttpStatus.OK);

        } catch (Exception e) {
            log.error(e.getMessage());
            response.setMsg(Constants.INTERNAL_SERVER_ERROR);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
