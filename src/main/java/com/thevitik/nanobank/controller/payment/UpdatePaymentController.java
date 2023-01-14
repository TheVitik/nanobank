package com.thevitik.nanobank.controller.payment;

import com.thevitik.nanobank.repository.impl.CardRepository;
import com.thevitik.nanobank.repository.impl.PaymentRepository;
import com.thevitik.nanobank.service.auth.AuthService;
import com.thevitik.nanobank.service.payment.PaymentService;
import com.thevitik.nanobank.service.validation.payment.CreatePaymentValidator;
import com.thevitik.nanobank.service.validation.payment.UpdatePaymentValidator;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "UpdatePayment", value = "/payments/update")
public class UpdatePaymentController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        AuthService auth = new AuthService(req);
        if (!auth.isLogged()) {
            resp.sendRedirect(req.getContextPath() + "/login");
        } else {
            CardRepository repository = new CardRepository();
            PaymentService paymentService = new PaymentService(new PaymentRepository(repository), repository, auth);
            try {
                paymentService.confirm(req, new UpdatePaymentValidator());
                req.getSession().setAttribute("success", "Your payment was sent successfully!");
            } catch (Exception e) {
                e.printStackTrace();
                req.getSession().setAttribute("error", e.getMessage());
            }
            resp.sendRedirect(req.getContextPath() + "/payments");
        }
    }
}
