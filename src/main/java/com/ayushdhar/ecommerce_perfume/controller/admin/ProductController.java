package com.ayushdhar.ecommerce_perfume.controller.admin;

import com.ayushdhar.ecommerce_perfume.dto.admin.product.AddProductRequestDTO;
import com.ayushdhar.ecommerce_perfume.dto.admin.product.GetProductByIdResponseDTO;
import com.ayushdhar.ecommerce_perfume.dto.admin.product.UpdateProductRequestDTO;
import com.ayushdhar.ecommerce_perfume.entity.AdminUser;
import com.ayushdhar.ecommerce_perfume.entity.Product;
import com.ayushdhar.ecommerce_perfume.entity.ProductImage;
import com.ayushdhar.ecommerce_perfume.entity.ProductOnCategories;
import com.ayushdhar.ecommerce_perfume.lib.Constants;
import com.ayushdhar.ecommerce_perfume.middleware.context.AdminUserContext;
import com.ayushdhar.ecommerce_perfume.response.ApiResponse;
import com.ayushdhar.ecommerce_perfume.services.admin.ProductService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/admin/product")
@Slf4j
public class ProductController {

    private final ProductService productService;

    ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponse<Void>> addProduct(@Valid @RequestBody AddProductRequestDTO addProductRequestDTO){
        ApiResponse<Void> response = new ApiResponse<>();
        try {
            AdminUser admin = productService.findAdminUserById(AdminUserContext.get().getId()).get();

            if (admin.getPermission().stream().noneMatch(permission -> permission.equals(Constants.ADMIN) || permission.equals(Constants.PRODUCT))) {
                response.setMsg(Constants.ACCESS_DENIED);
                return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
            }

            productService.addProductByDto(addProductRequestDTO);
            response.setMsg("Product added successfully");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage());
            response.setMsg(Constants.INTERNAL_SERVER_ERROR);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @GetMapping("/get")
    public ResponseEntity<ApiResponse<List<Product>>> getAllProducts(){
        ApiResponse<List<Product>> response = new ApiResponse<>();
        try {
            AdminUser admin = productService.findAdminUserById(AdminUserContext.get().getId()).get();
            if (admin.getPermission().stream().noneMatch(permission -> permission.equals(Constants.ADMIN) || permission.equals(Constants.PRODUCT))) {
                response.setMsg(Constants.ACCESS_DENIED);
                return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
            }

            List<Product> products = productService.getAllProducts();

            for (Product product : products) {
                product.setProductOnCategories(null);
                product.setImages(null);
            }

            response.setMsg("Product fetch successfully");
            response.setData(products);

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage());
            response.setMsg(Constants.INTERNAL_SERVER_ERROR);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<ApiResponse<GetProductByIdResponseDTO>> getProductById(@PathVariable("id") String id){
        ApiResponse<GetProductByIdResponseDTO> response = new ApiResponse<>();
        try {
            AdminUser admin = productService.findAdminUserById(AdminUserContext.get().getId()).get();
            if (admin.getPermission().stream().noneMatch(permission -> permission.equals(Constants.ADMIN) || permission.equals(Constants.PRODUCT))) {
                response.setMsg(Constants.ACCESS_DENIED);
                return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
            }

            Optional<Product> optionalProduct = productService.findProductById(id);
            if (optionalProduct.isEmpty()) {
                response.setMsg("Product not found");
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }
            Product product = optionalProduct.get();

            GetProductByIdResponseDTO getProductByIdResponseDTO = new GetProductByIdResponseDTO();
            getProductByIdResponseDTO.setId(product.getId());
            getProductByIdResponseDTO.setName(product.getName());
            getProductByIdResponseDTO.setDescription(product.getDescription());
            getProductByIdResponseDTO.setDescription2(product.getDescription2());
            getProductByIdResponseDTO.setDescription3(product.getDescription3());
            getProductByIdResponseDTO.setFragrance(product.getFragrance());
            getProductByIdResponseDTO.setCreatedAt(product.getCreatedAt());
            getProductByIdResponseDTO.setUpdatedAt(product.getUpdatedAt());
            getProductByIdResponseDTO.setIdealFor(product.getIdealFor());
            getProductByIdResponseDTO.setPoints(product.getPoints());
            getProductByIdResponseDTO.setPreference(product.getPreference());
            getProductByIdResponseDTO.setSustainable(product.getSustainable());
            getProductByIdResponseDTO.setIsLive(product.getIsLive());
            getProductByIdResponseDTO.setQuantity(product.getQuantity());
            getProductByIdResponseDTO.setType(product.getType());
            getProductByIdResponseDTO.setStrength(product.getStrength());
            getProductByIdResponseDTO.setMaxPrice(product.getMaxPrice());
            getProductByIdResponseDTO.setSellPrice(product.getSellPrice());

            List<ProductImage> productImages = new ArrayList<>();
            List<String> categories = new ArrayList<>();

            for (ProductImage productImage : product.getImages()) {
                productImage.setProduct(null);
                productImages.add(productImage);
            }

            for (ProductOnCategories productOnCategories : product.getProductOnCategories()) {
                categories.add(productOnCategories.getCategory().getName());
            }

            getProductByIdResponseDTO.setImages(productImages);
            getProductByIdResponseDTO.setCategories(categories);

            response.setData(getProductByIdResponseDTO);
            response.setMsg("Product fetch successfully");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage());
            response.setMsg(Constants.INTERNAL_SERVER_ERROR);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/change-status/{id}")
    public  ResponseEntity<ApiResponse<Void>> changeStatus (@PathVariable String id, @RequestParam("status") Boolean status) {
        ApiResponse<Void> response = new ApiResponse<>();
        try {
            AdminUser admin = productService.findAdminUserById(AdminUserContext.get().getId()).get();
            if (admin.getPermission().stream().noneMatch(permission -> permission.equals(Constants.ADMIN) || permission.equals(Constants.PRODUCT))) {
                response.setMsg(Constants.ACCESS_DENIED);
                return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
            }
            Optional<Product> optionalProduct = productService.findProductById(id);

            if (optionalProduct.isEmpty()) {
                response.setMsg("Product not found");
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }

            Product product = optionalProduct.get();
            product.setIsLive(status);
            productService.updateProduct(product);

            response.setMsg("Product status updated successfully");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage());
            response.setMsg(Constants.INTERNAL_SERVER_ERROR);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/update")
    public  ResponseEntity<ApiResponse<Void>> updateProduct (@Valid @RequestBody UpdateProductRequestDTO updateProductRequestDTO) {
        ApiResponse<Void> response = new ApiResponse<>();
        try {
            AdminUser admin = productService.findAdminUserById(AdminUserContext.get().getId()).get();
            if (admin.getPermission().stream().noneMatch(permission -> permission.equals(Constants.ADMIN) || permission.equals(Constants.PRODUCT))) {
                response.setMsg(Constants.ACCESS_DENIED);
                return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
            }
            Optional<Product> optionalProduct = productService.findProductById(updateProductRequestDTO.getId());

            if (optionalProduct.isEmpty()) {
                response.setMsg("Product not found");
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }
            productService.updateProductByDto(updateProductRequestDTO);
            response.setMsg("Product updated successfully");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage());
            response.setMsg(Constants.INTERNAL_SERVER_ERROR);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
