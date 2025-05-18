package com.ayushdhar.ecommerce_perfume.middleware.interceptor;

import com.ayushdhar.ecommerce_perfume.entity.User;
import com.ayushdhar.ecommerce_perfume.middleware.context.UserContext;
import com.ayushdhar.ecommerce_perfume.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Optional;

@Component
public class UserMiddlewareInterceptor implements HandlerInterceptor {
    private final UserRepository userRepository;

    public UserMiddlewareInterceptor(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        String authHeader = request.getHeader("Authorization");
        response.setHeader("Content-Type", "application/json");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("{\"msg\": \"User not found\"}");
            return false;
        }

        String uid = authHeader.split(" ")[1];
        Optional<User> user = userRepository.findByUid(uid);

        if(user.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("{\"msg\": \"User not found\"}");
            return false;
        }

        UserContext.set(user.get());
        return true;
    }

    @Override
    public  void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        UserContext.clear();
    }
}
