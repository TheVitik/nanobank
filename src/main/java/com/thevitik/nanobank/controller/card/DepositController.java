package com.thevitik.nanobank.controller.card;

import com.thevitik.nanobank.repository.impl.CardRepository;
import com.thevitik.nanobank.repository.impl.UserRepository;
import com.thevitik.nanobank.service.auth.AuthService;
import com.thevitik.nanobank.service.auth.LoginService;
import com.thevitik.nanobank.service.card.CardBalanceService;
import com.thevitik.nanobank.service.validation.auth.LoginValidator;
import com.thevitik.nanobank.service.validation.card.DepositValidator;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "Deposit", value = "/deposit")
public class DepositController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        AuthService auth = new AuthService(req);
        if (!auth.isLogged()) {
            resp.sendRedirect(req.getContextPath() + "/login");
        }
        req.getSession().removeAttribute("error");
        req.getSession().removeAttribute("success");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        AuthService auth = new AuthService(req);
        if (!auth.isLogged()) {
            resp.sendRedirect(req.getContextPath() + "/login");
        } else {
            CardBalanceService balanceService = new CardBalanceService(new CardRepository(), auth);
            try {
                balanceService.deposit(req, new DepositValidator());
                req.getSession().setAttribute("success", "The money was deposited on your card");
            } catch (SQLException e) {
                e.printStackTrace();
                req.setAttribute("error", e.getMessage());
                req.getRequestDispatcher("home.jsp").forward(req, resp);
            }
            resp.sendRedirect(req.getContextPath() + "/home");
        }
    }
}
