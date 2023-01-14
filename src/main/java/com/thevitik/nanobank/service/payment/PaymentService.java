package com.thevitik.nanobank.service.payment;

import com.thevitik.nanobank.model.Card;
import com.thevitik.nanobank.model.Payment;
import com.thevitik.nanobank.repository.CardRepositoryInterface;
import com.thevitik.nanobank.repository.PaymentRepositoryInterface;
import com.thevitik.nanobank.service.auth.AuthService;
import com.thevitik.nanobank.service.validation.payment.UpdatePaymentValidator;
import com.thevitik.nanobank.service.validation.payment.CreatePaymentValidator;
import com.thevitik.nanobank.service.validation.payment.GetPaymentsValidator;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

public class PaymentService {

    PaymentRepositoryInterface paymentRepository;
    CardRepositoryInterface cardRepository;
    AuthService auth;

    public PaymentService(
            PaymentRepositoryInterface paymentRepository,
            CardRepositoryInterface cardRepository,
            AuthService auth
    ) {
        this.paymentRepository = paymentRepository;
        this.cardRepository = cardRepository;
        this.auth = auth;
    }

    public List<Payment> getPayments(HttpServletRequest request, GetPaymentsValidator validator) throws SQLException {
        if (validator.shouldSort(request)) {
            String p = request.getParameter("sort");
            return paymentRepository.getUserPayments(auth.getUser(), getSortBy(p), getSortType(p));
        }
        return paymentRepository.getUserPayments(auth.getUser());
    }

    public void send(HttpServletRequest request, CreatePaymentValidator validator) throws SQLException {
        Payment payment = validator.validated(request);

        Card sender = cardRepository.findById(Integer.parseInt(request.getParameter("sender_id")));
        Card receiver = cardRepository.findByNumber(Long.parseLong(request.getParameter("receiver")));

        validator.validateCards(sender, receiver, auth.getUser(), payment.getBalance());

        if(getStatus(request.getParameter("prepare")) == Payment.SENT){
            sender.addBalance(-payment.getBalance());
            receiver.addBalance(payment.getBalance());
        }

        payment.setSender(sender)
                .setReceiver(receiver)
                .setStatus(getStatus(request.getParameter("prepare")));

        paymentRepository.create(payment);
    }

    public void confirm(HttpServletRequest request, UpdatePaymentValidator validator) throws SQLException, IllegalArgumentException {
        validator.validate(request.getParameter("payment_id"), "payment id").integer();
        Payment payment = paymentRepository.findById(Integer.parseInt(request.getParameter("payment_id")));
        validator.validatePayment(payment, auth.getUser());
        payment.setStatus(Payment.SENT);
        payment.setSender(payment.getSender().addBalance(-payment.getBalance()));
        payment.setReceiver(payment.getReceiver().addBalance(payment.getBalance()));
        paymentRepository.update(payment);
    }

    private int getStatus(String param) {
        if (param == null) {
            return Payment.SENT;
        }
        return Payment.PREPARED;
    }

    private String getSortBy(String param) {
        return param.split("-")[0];
    }

    private String getSortType(String param) {
        return param.split("-")[1];
    }
}
