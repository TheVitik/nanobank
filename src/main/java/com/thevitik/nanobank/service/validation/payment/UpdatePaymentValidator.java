package com.thevitik.nanobank.service.validation.payment;

import com.thevitik.nanobank.model.Payment;
import com.thevitik.nanobank.model.User;
import com.thevitik.nanobank.service.validation.Validator;

import javax.servlet.http.HttpServletRequest;

public class UpdatePaymentValidator extends Validator<Payment> {

    @Override
    public Payment validated(HttpServletRequest request) throws IllegalArgumentException {
        return new Payment();
    }

    public Validator<Payment> validatePayment(Payment payment, User user) throws IllegalArgumentException {
        if (payment == null) {
            throw new IllegalArgumentException("Payment not found");
        }
        if (!payment.getSender().getOwner().equals(user)) {
            throw new IllegalArgumentException("You are not the owner of the payment");
        }
        if (payment.getStatus() == Payment.SENT) {
            throw new IllegalArgumentException("The payment was sent already");
        }
        return this;
    }
}
