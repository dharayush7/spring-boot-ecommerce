package com.ayushdhar.ecommerce_perfume.middleware.context;

import com.ayushdhar.ecommerce_perfume.entity.User;

public class UserContext {

    private UserContext() {
        throw new IllegalStateException("Context class");
    }

    private static final ThreadLocal<User> currentUser = new ThreadLocal<>();

    public static void set(User user) {
        currentUser.set(user);
    }

    public static User get() {
        return currentUser.get();
    }

    public static void clear() {
        currentUser.remove();
    }
}

