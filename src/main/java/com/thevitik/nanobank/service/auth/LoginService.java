package com.thevitik.nanobank.service.auth;

import com.thevitik.nanobank.model.User;
import com.thevitik.nanobank.repository.UserRepositoryInterface;
import com.thevitik.nanobank.service.validation.auth.LoginValidator;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;

public class LoginService extends AuthService {

    private LoginValidator validator;
    private UserRepositoryInterface repository;

    public LoginService(HttpServletRequest request, LoginValidator validator, UserRepositoryInterface repository) {
        super(request);
        this.validator = validator;
        this.repository = repository;
    }

    public void login() throws SQLException, IllegalAccessException {
        User validUser = validator.validated(request);
        User user = repository.findByPhone(validUser.getPhone());
        if (user == null || !user.hasValidPassword(validUser.getPassword())) {
            throw new IllegalArgumentException("Phone or password is incorrect");
        }
        if (user.isBanned()) {
            throw new IllegalAccessException("Your account is banned!");
        }

        store(user);
    }
}
