package com.thevitik.nanobank.service.validation.payment;

import com.thevitik.nanobank.model.Card;
import com.thevitik.nanobank.model.Payment;
import com.thevitik.nanobank.model.User;
import com.thevitik.nanobank.service.validation.Validator;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Objects;

public class CreatePaymentValidator extends Validator<Payment> {

    @Override
    public Payment validated(HttpServletRequest request) throws IllegalArgumentException {
        checkRequiredParameters(request, new String[]{"sender_id", "receiver", "balance"});
        validate(request.getParameter("balance"), "balance").integer().intBetween(1, 99999);
        validate(request.getParameter("sender_id"), "sender").integer();
        validate(request.getParameter("receiver"), "receiver").creditCard();
        return makeObject(request);
    }

    public Validator<Payment> validateCards(Card sender, Card receiver, User senderUser, Integer balance) {
        if (sender == null) {
            throw new IllegalArgumentException("Invalid sender card");
        }
        if (!sender.getOwner().equals(senderUser)) {
            throw new IllegalArgumentException("You are not the owner of sender card");
        }
        if (receiver == null) {
            throw new IllegalArgumentException("Invalid receiver card");
        }
        if (Objects.equals(sender.getId(), receiver.getId())) {
            throw new IllegalArgumentException("You can't send money to the same card");
        }
        if (sender.getBalance() < balance * 100) {
            throw new IllegalArgumentException("Not enough money to transfer");
        }
        return this;
    }

    private Payment makeObject(HttpServletRequest request) {
        return new Payment().setStatus(Payment.PREPARED)
                .setBalance(Integer.parseInt(request.getParameter("balance")))
                .setSentDate(new Date());
    }
}
