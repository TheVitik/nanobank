package com.thevitik.nanobank.service.validation.card;

import com.thevitik.nanobank.model.Card;
import com.thevitik.nanobank.model.User;
import com.thevitik.nanobank.service.validation.Validator;

import javax.servlet.http.HttpServletRequest;

public class CardAccessValidator extends Validator<Card> {

    @Override
    public Card validated(HttpServletRequest request) throws IllegalArgumentException {
        checkRequiredParameters(request, new String[]{"card_id"});
        validate(request.getParameter("card_id"), "card").integer();
        return new Card();
    }

    public Validator<Card> validateCard(Card card, User user, boolean action) throws IllegalAccessException {
        if (card == null) {
            throw new IllegalArgumentException("Invalid card");
        }
        if (!card.getOwner().equals(user)) {
            if (user.getRole() != User.ADMIN) {
                throw new IllegalAccessException("You have no access to this card");
            }
        }
        if (card.isBlocked() == action) {
            System.out.println(card.isBlocked());
            System.out.println(action);
            throw new IllegalArgumentException("Nothing to update");
        }
        return this;
    }
}
