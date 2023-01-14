package com.thevitik.nanobank.controller.payment;

import com.thevitik.nanobank.repository.impl.CardRepository;
import com.thevitik.nanobank.repository.impl.PaymentRepository;
import com.thevitik.nanobank.service.auth.AuthService;
import com.thevitik.nanobank.service.payment.PaymentService;
import com.thevitik.nanobank.service.validation.payment.CreatePaymentValidator;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "CreatePayment", value = "/payments/create")
public class CreatePaymentController extends HttpServlet {
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
                paymentService.send(req, new CreatePaymentValidator());
                req.getSession().setAttribute("success", "The money was transferred successfully");
            } catch (Exception e) {
                req.getSession().setAttribute("error", e.getMessage());
            }
            resp.sendRedirect(req.getContextPath() + "/home");
        }
    }
}
