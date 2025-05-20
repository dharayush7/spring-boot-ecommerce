package com.ayushdhar.ecommerce_perfume.config;

import com.ayushdhar.ecommerce_perfume.middleware.interceptor.AdminMiddlewareInterceptor;
import com.ayushdhar.ecommerce_perfume.middleware.interceptor.UserMiddlewareInterceptor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Component
public class WebConfig implements WebMvcConfigurer {

    private final AdminMiddlewareInterceptor adminMiddlewareInterceptor;
    private final UserMiddlewareInterceptor userMiddlewareInterceptor;

    public WebConfig(
            AdminMiddlewareInterceptor adminMiddlewareInterceptor,
            UserMiddlewareInterceptor userMiddlewareInterceptor
    ) {
        this.adminMiddlewareInterceptor = adminMiddlewareInterceptor;
        this.userMiddlewareInterceptor = userMiddlewareInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(adminMiddlewareInterceptor)
                .excludePathPatterns("/admin/auth/**")
                .addPathPatterns("/admin/**");

        registry.addInterceptor(userMiddlewareInterceptor)
                .addPathPatterns("/profile/**", "/cart/**");
    }
}
