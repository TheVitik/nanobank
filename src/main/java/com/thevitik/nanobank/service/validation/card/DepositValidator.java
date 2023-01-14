package com.thevitik.nanobank.service.validation.card;

import com.thevitik.nanobank.model.Card;
import com.thevitik.nanobank.model.User;
import com.thevitik.nanobank.service.validation.Validator;

import javax.servlet.http.HttpServletRequest;

public class DepositValidator extends Validator<Card> {

    @Override
    public Card validated(HttpServletRequest request) throws IllegalArgumentException {
        checkRequiredParameters(request, new String[]{"balance", "card_id"});
        validate(request.getParameter("balance"), "balance").integer().intBetween(1, 99999);
        return new Card();
    }

    public Validator<Card> validateCard(Card card, User user) {
        if (card == null) {
            throw new IllegalArgumentException("Invalid card");
        }
        if (!card.getOwner().equals(user)) {
            throw new IllegalArgumentException("You are not the owner of this card");
        }
        return this;
    }
}
