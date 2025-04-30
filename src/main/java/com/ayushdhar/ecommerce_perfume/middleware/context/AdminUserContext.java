package com.ayushdhar.ecommerce_perfume.middleware.context;

import com.ayushdhar.ecommerce_perfume.entity.AdminUser;

public class AdminUserContext {

    private AdminUserContext() {
        throw new IllegalStateException("Context class");
    }
    private static final ThreadLocal<AdminUser> currentAdmin = new ThreadLocal<>();

    public static void set(AdminUser user) {
        currentAdmin.set(user);
    }

    public static AdminUser get() {
        return currentAdmin.get();
    }

    public static void clear() {
        currentAdmin.remove();
    }
}

