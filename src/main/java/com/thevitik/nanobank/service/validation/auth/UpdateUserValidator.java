package com.thevitik.nanobank.service.validation.auth;

import com.thevitik.nanobank.model.User;
import com.thevitik.nanobank.service.validation.Validator;

import javax.servlet.http.HttpServletRequest;

public class UpdateUserValidator extends Validator<User> {

    @Override
    public User validated(HttpServletRequest request) throws IllegalArgumentException {
        checkRequiredParameters(request, new String[]{"user_id"});
        validate(request.getParameter("user_id"), "user").integer();

        return new User();
    }

    public Validator<User> validateUser(User user, User me) throws IllegalAccessException {
        if (user == null) {
            throw new IllegalArgumentException("Invalid user");
        }
        if (me.getRole() != User.ADMIN) {
            throw new IllegalAccessException("You have no access to control users");
        }
        if (me.getId() == user.getId()) {
            throw new IllegalArgumentException("You can't ban yourself");
        }
        return this;
    }
}
