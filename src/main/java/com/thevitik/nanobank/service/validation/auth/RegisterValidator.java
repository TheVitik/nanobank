package com.thevitik.nanobank.service.validation.auth;

import com.thevitik.nanobank.model.User;
import com.thevitik.nanobank.service.validation.Validator;

import javax.servlet.http.HttpServletRequest;

public class RegisterValidator extends Validator<User> {

    @Override
    public User validated(HttpServletRequest request) throws IllegalArgumentException {
        checkRequiredParameters(request, new String[]{"firstname", "lastname", "phone", "password"});
        validate(request.getParameter("firstname"), "first name").lengthBetween(3, 32);
        validate(request.getParameter("lastname"), "last name").lengthBetween(3, 32);
        validate(request.getParameter("phone"), "phone").phone();
        validate(request.getParameter("password"), "password").password();

        return makeObject(request);
    }

    private User makeObject(HttpServletRequest request) {
        return new User().setFirstName(request.getParameter("firstname"))
                .setLastName(request.getParameter("lastname"))
                .setPhone(request.getParameter("phone"))
                .setEncryptedPassword(request.getParameter("password"));
    }
}
