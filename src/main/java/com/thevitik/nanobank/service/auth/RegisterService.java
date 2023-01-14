package com.thevitik.nanobank.service.auth;

import com.thevitik.nanobank.model.User;
import com.thevitik.nanobank.repository.UserRepositoryInterface;
import com.thevitik.nanobank.repository.impl.UserRepository;
import com.thevitik.nanobank.service.validation.auth.RegisterValidator;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;

public class RegisterService extends AuthService {

    private RegisterValidator validator;
    private UserRepositoryInterface repository;

    public RegisterService(HttpServletRequest request, RegisterValidator validator, UserRepositoryInterface repository) {
        super(request);
        this.validator = validator;
        this.repository = repository;
    }

    public void register() throws SQLException {
        User user = validator.validated(request);
        if (repository.findByPhone(user.getPhone()) != null) {
            throw new IllegalArgumentException("Phone number is already taken");
        }
        repository.create(user);
    }
}
