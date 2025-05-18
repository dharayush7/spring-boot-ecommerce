package com.ayushdhar.ecommerce_perfume.middleware.interceptor;

import com.ayushdhar.ecommerce_perfume.entity.AdminSession;
import com.ayushdhar.ecommerce_perfume.lib.Utils;
import com.ayushdhar.ecommerce_perfume.middleware.context.AdminUserContext;
import com.ayushdhar.ecommerce_perfume.repository.AdminSessionRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Optional;

@Component
public class AdminMiddlewareInterceptor implements HandlerInterceptor {
    private final AdminSessionRepository adminSessionRepository;

    public AdminMiddlewareInterceptor(AdminSessionRepository adminSessionRepository) {
        this.adminSessionRepository = adminSessionRepository;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {

        String authHeader = request.getHeader("Authorization");
        response.setHeader("Content-Type", "application/json");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("{\"msg\": \"Session not found\"}");
            return false;
        }

        String sessionToken = authHeader.split(" ")[1];

        Optional<AdminSession> sessionOpt = adminSessionRepository.findBySessionToken(sessionToken);

        if (sessionOpt.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("{\"msg\": \"Session not found\"}");
            return false;
        }

        if (Utils.isExpired(sessionOpt.get().getExpireAt())) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("{\"msg\": \"Session expired\"}");
            return false;
        }

        AdminUserContext.set(sessionOpt.get().getAdminUser());
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        AdminUserContext.clear(); // Cleanup
    }
}
