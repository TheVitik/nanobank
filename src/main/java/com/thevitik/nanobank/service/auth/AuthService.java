package com.thevitik.nanobank.service.auth;

import com.thevitik.nanobank.model.User;

import javax.servlet.http.HttpServletRequest;

public class AuthService {
    protected HttpServletRequest request;

    public AuthService(HttpServletRequest request) {
        this.request = request;
    }

    public boolean isLogged() {
        return request.getSession().getAttribute("user") != null;
    }

    protected void store(User user) {
        request.getSession().setAttribute("user", user);
    }

    public User getUser() {
        return (User) request.getSession().getAttribute("user");
    }

    public void logout() {
        request.getSession().invalidate();
    }
}
