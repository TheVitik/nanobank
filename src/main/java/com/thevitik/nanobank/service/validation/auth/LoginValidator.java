package com.thevitik.nanobank.service.validation.auth;

import com.thevitik.nanobank.model.User;
import com.thevitik.nanobank.service.validation.Validator;

import javax.servlet.http.HttpServletRequest;

public class LoginValidator extends Validator<User> {

    @Override
    public User validated(HttpServletRequest request) throws IllegalArgumentException {
        checkRequiredParameters(request, new String[]{"phone", "password"});
        validate(request.getParameter("phone"), "phone").phone();
        validate(request.getParameter("password"), "password").lengthGreater(8);
        return makeObject(request);
    }

    private User makeObject(HttpServletRequest request) {
        return new User()
                .setPhone(request.getParameter("phone"))
                .setPassword(request.getParameter("password"));
    }
}
