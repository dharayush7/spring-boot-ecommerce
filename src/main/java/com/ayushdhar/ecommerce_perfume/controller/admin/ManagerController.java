package com.ayushdhar.ecommerce_perfume.controller.admin;

import com.ayushdhar.ecommerce_perfume.dto.admin.manager.AddManagerRequestDTO;
import com.ayushdhar.ecommerce_perfume.dto.admin.manager.UpdateManagerRequestDTO;
import com.ayushdhar.ecommerce_perfume.dto.admin.manager.UpdatePermissionOfManagerRequestDTO;
import com.ayushdhar.ecommerce_perfume.dto.admin.manager.DeleteManagerRequestDTO;
import com.ayushdhar.ecommerce_perfume.entity.AdminUser;
import com.ayushdhar.ecommerce_perfume.lib.Constants;
import com.ayushdhar.ecommerce_perfume.middleware.context.AdminUserContext;
import com.ayushdhar.ecommerce_perfume.response.ApiResponse;
import com.ayushdhar.ecommerce_perfume.services.ManagerService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/admin/manager")
@Slf4j
public class ManagerController {
    private final ManagerService managerService;

    public ManagerController(ManagerService managerService) {
        this.managerService = managerService;
    }

    @GetMapping("/get")
    public ResponseEntity<ApiResponse<List<AdminUser>>> getAllManagers() {
        ApiResponse<List<AdminUser>> response = new ApiResponse<>();
        try {

            AdminUser admin = managerService.findAdminUserById( AdminUserContext.get().getId()).get();

            if (admin.getPermission().stream().noneMatch(permission -> permission.equals(Constants.ADMIN) )) {
                response.setMsg(Constants.ACCESS_DENIED);
                return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
            }

            List<AdminUser> allManagers = managerService.getAllManagers();
            allManagers.forEach(manager -> {
                manager.setOtps(null);
                manager.setPassword(null);
                manager.setAdminRestPasswordSessions(null);
                manager.setAdminSessions(null);
            });
            response.setMsg("Manager fetch");
            response.setData(allManagers);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            response.setMsg(Constants.INTERNAL_SERVER_ERROR);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponse<Void>> addManager(@Valid @RequestBody AddManagerRequestDTO addManagerRequestDTO) {
        ApiResponse<Void> response = new ApiResponse<>();
        try {
            AdminUser admin = managerService.findAdminUserById( AdminUserContext.get().getId()).get();

            if (admin.getPermission().stream().noneMatch(permission -> permission.equals(Constants.ADMIN) )) {
                response.setMsg(Constants.ACCESS_DENIED);
                return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
            }

            managerService.addNewManager(addManagerRequestDTO);

            response.setMsg("Manager added successfully");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage());
            response.setMsg(Constants.INTERNAL_SERVER_ERROR);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/update")
    public ResponseEntity<ApiResponse<Void>> updateManager(@Valid @RequestBody UpdateManagerRequestDTO updateManagerRequestDTO) {
        ApiResponse<Void> response = new ApiResponse<>();
        try {
            AdminUser admin = managerService.findAdminUserById( AdminUserContext.get().getId()).get();

            if (admin.getPermission().stream().noneMatch(permission -> permission.equals(Constants.ADMIN) )) {
                response.setMsg(Constants.ACCESS_DENIED);
                return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
            }

            Optional<AdminUser> optionalAdminUser = managerService.findManagerByEmail(updateManagerRequestDTO.getOldEmail());

            if (optionalAdminUser.isEmpty()) {
                response.setMsg(Constants.MANAGER_NOT_FOUND);
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }

            AdminUser adminUser = optionalAdminUser.get();
            adminUser.setName(updateManagerRequestDTO.getName());
            adminUser.setEmail(updateManagerRequestDTO.getEmail());

            managerService.updateManager(adminUser);

            response.setMsg("Manager updated successfully");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage());
            response.setMsg(Constants.INTERNAL_SERVER_ERROR);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/permission/update")
    public ResponseEntity<ApiResponse<Void>> updateManagerPermission(@Valid @RequestBody UpdatePermissionOfManagerRequestDTO updatePermissionOfManagerRequestDTO) {
        ApiResponse<Void> response = new ApiResponse<>();
        try {
            AdminUser admin = managerService.findAdminUserById( AdminUserContext.get().getId()).get();

            if (admin.getPermission().stream().noneMatch(permission -> permission.equals(Constants.ADMIN) )) {
                response.setMsg(Constants.ACCESS_DENIED);
                return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
            }

            Optional<AdminUser> optionalAdminUser = managerService.findManagerByEmail(updatePermissionOfManagerRequestDTO.getEmail());

            if (optionalAdminUser.isEmpty()) {
                response.setMsg(Constants.MANAGER_NOT_FOUND);
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }

            AdminUser adminUser = optionalAdminUser.get();
            List<String> permission = new ArrayList<>();

            if (updatePermissionOfManagerRequestDTO.getAdmin()) permission.add("ADMIN");
            if (updatePermissionOfManagerRequestDTO.getCustomer()) permission.add("CUSTOMER");
            if (updatePermissionOfManagerRequestDTO.getProduct()) permission.add("PRODUCT");
            if (updatePermissionOfManagerRequestDTO.getSite()) permission.add("SITE");
            if (updatePermissionOfManagerRequestDTO.getPayment()) permission.add("PAYMENT");
            if (updatePermissionOfManagerRequestDTO.getOrder()) permission.add("ORDER");

            adminUser.setPermission(permission);
            managerService.updateManager(adminUser);

            response.setMsg("Permission updated successfully");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage());
            response.setMsg(Constants.INTERNAL_SERVER_ERROR);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/delete")
    public ResponseEntity<ApiResponse<Void>> deleteManager(@Valid @RequestBody DeleteManagerRequestDTO deleteManagerRequestDTO) {
        ApiResponse<Void> response = new ApiResponse<>();
        try {
            AdminUser admin = managerService.findAdminUserById( AdminUserContext.get().getId()).get();

            if (admin.getPermission().stream().noneMatch(permission -> permission.equals(Constants.ADMIN) )) {
                response.setMsg(Constants.ACCESS_DENIED);
                return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
            }

            Optional<AdminUser> optionalAdminUser = managerService.findManagerByEmail(deleteManagerRequestDTO.getEmail());

            if (optionalAdminUser.isEmpty()) {
                response.setMsg(Constants.MANAGER_NOT_FOUND);
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }

            managerService.deleteManagerByEmail(deleteManagerRequestDTO.getEmail());

            response.setMsg("Manager deleted successfully");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage());
            response.setMsg(Constants.INTERNAL_SERVER_ERROR);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
