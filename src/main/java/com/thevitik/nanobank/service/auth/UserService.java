package com.thevitik.nanobank.service.auth;

import com.thevitik.nanobank.model.User;
import com.thevitik.nanobank.repository.UserRepositoryInterface;
import com.thevitik.nanobank.service.validation.auth.UpdateUserValidator;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.util.List;

public class UserService {
    private UserRepositoryInterface repository;
    private AuthService auth;

    public UserService(UserRepositoryInterface repository, AuthService auth) {
        this.repository = repository;
        this.auth = auth;
    }

    public List<User> getUsers() throws SQLException {
        return repository.getUsers();
    }

    public void update(HttpServletRequest request, UpdateUserValidator validator) throws SQLException, IllegalAccessException {
        validator.validated(request);
        User user = repository.findById(Integer.parseInt(request.getParameter("user_id")));
        validator.validateUser(user, auth.getUser());
        user.setRole(getAction(request));
        repository.update(user);

    }

    private int getAction(HttpServletRequest request) {
        if (request.getParameter("ban") != null) {
            return User.BANNED;
        } else {
            return User.USER;
        }
    }
}
