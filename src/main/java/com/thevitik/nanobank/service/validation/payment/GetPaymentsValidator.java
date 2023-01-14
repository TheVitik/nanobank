package com.thevitik.nanobank.service.validation.payment;

import com.thevitik.nanobank.model.Card;
import com.thevitik.nanobank.model.Payment;
import com.thevitik.nanobank.model.User;
import com.thevitik.nanobank.service.validation.Validator;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Objects;

public class GetPaymentsValidator extends Validator<Payment> {
    private final String[] sortings = new String[]{"id", "date"};

    @Override
    public Payment validated(HttpServletRequest request) throws IllegalArgumentException {
        return new Payment();
    }

    public boolean shouldSort(HttpServletRequest request) {
        String param = request.getParameter("sort");
        if (param == null) {
            return false;
        }
        for (String sort : sortings) {
            if (param.contains(sort) && (param.contains("asc") || param.contains("desc"))) {
                return true;
            }
        }
        return false;
    }
}
