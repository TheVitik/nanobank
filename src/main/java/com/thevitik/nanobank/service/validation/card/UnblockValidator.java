package com.thevitik.nanobank.service.validation.card;

import com.thevitik.nanobank.model.Card;
import com.thevitik.nanobank.model.UnblockRequest;
import com.thevitik.nanobank.model.User;
import com.thevitik.nanobank.service.validation.Validator;

import javax.servlet.http.HttpServletRequest;

public class UnblockValidator extends Validator<UnblockRequest> {

    @Override
    public UnblockRequest validated(HttpServletRequest request) throws IllegalArgumentException {
        checkRequiredParameters(request, new String[]{"request_id"});
        validate(request.getParameter("request_id"), "unblock request").integer();
        return new UnblockRequest();
    }

    public Validator<UnblockRequest> validateRequest(UnblockRequest request, User user) throws IllegalAccessException {
        if (request == null) {
            throw new IllegalArgumentException("Invalid unblock request");
        }
        if (user.getRole() != User.ADMIN) {
            throw new IllegalAccessException("You have no access to unblock the card");
        }
        return this;
    }
}
