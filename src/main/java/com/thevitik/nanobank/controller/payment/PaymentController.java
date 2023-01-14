package com.thevitik.nanobank.controller.payment;

import com.thevitik.nanobank.model.Card;
import com.thevitik.nanobank.model.Payment;
import com.thevitik.nanobank.repository.impl.CardRepository;
import com.thevitik.nanobank.repository.impl.PaymentRepository;
import com.thevitik.nanobank.service.auth.AuthService;
import com.thevitik.nanobank.service.card.CardBalanceService;
import com.thevitik.nanobank.service.payment.PaymentService;
import com.thevitik.nanobank.service.validation.card.DepositValidator;
import com.thevitik.nanobank.service.validation.payment.GetPaymentsValidator;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "Payments", value = "/payments")
public class PaymentController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        AuthService auth = new AuthService(req);
        if (!auth.isLogged()) {
            resp.sendRedirect(req.getContextPath() + "/login");
        } else {
            try {
                CardRepository repository = new CardRepository();
                PaymentService paymentService = new PaymentService(new PaymentRepository(repository), repository, auth);
                List<Payment> payments = paymentService.getPayments(req, new GetPaymentsValidator());
                req.setAttribute("payments", payments);
            } catch (Exception e) {
                req.getSession().setAttribute("error", e.getMessage());
            }
            req.getRequestDispatcher("payments.jsp").forward(req, resp);
        }
        req.getSession().removeAttribute("error");
        req.getSession().removeAttribute("success");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }
}
