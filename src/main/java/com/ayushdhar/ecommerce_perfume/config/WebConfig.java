package com.ayushdhar.ecommerce_perfume.config;

import com.ayushdhar.ecommerce_perfume.middleware.interceptor.AdminMiddlewareInterceptor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Component
public class WebConfig implements WebMvcConfigurer {

    private final AdminMiddlewareInterceptor adminMiddlewareInterceptor;

    public WebConfig(AdminMiddlewareInterceptor adminMiddlewareInterceptor) {
        this.adminMiddlewareInterceptor = adminMiddlewareInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(adminMiddlewareInterceptor)
                .excludePathPatterns("/admin/auth/**")
                .addPathPatterns("/admin/**");
    }
}
