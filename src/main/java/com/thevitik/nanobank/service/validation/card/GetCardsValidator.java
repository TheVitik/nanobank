package com.thevitik.nanobank.service.validation.card;

import com.thevitik.nanobank.model.Card;
import com.thevitik.nanobank.model.Payment;
import com.thevitik.nanobank.service.validation.Validator;

import javax.servlet.http.HttpServletRequest;

public class GetCardsValidator extends Validator<Card> {
    private final String[] sortings = new String[]{"id", "number", "balance"};

    @Override
    public Card validated(HttpServletRequest request) throws IllegalArgumentException {
        return new Card();
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
