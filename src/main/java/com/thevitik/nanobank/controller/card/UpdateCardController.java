package com.thevitik.nanobank.controller.card;

import com.thevitik.nanobank.repository.impl.CardRepository;
import com.thevitik.nanobank.repository.impl.UnblockRequestRepository;
import com.thevitik.nanobank.service.auth.AuthService;
import com.thevitik.nanobank.service.card.CardAccessService;
import com.thevitik.nanobank.service.card.CardBalanceService;
import com.thevitik.nanobank.service.validation.card.CardAccessValidator;
import com.thevitik.nanobank.service.validation.card.DepositValidator;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "UpdateCard", value = "/cards/update")
public class UpdateCardController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        AuthService auth = new AuthService(req);
        if (!auth.isLogged()) {
            resp.sendRedirect(req.getContextPath() + "/login");
        } else {
            CardAccessService service = new CardAccessService(new CardRepository(), new UnblockRequestRepository(), auth);
            try {
                service.update(req, new CardAccessValidator());
            } catch (Exception e) {
                req.getSession().setAttribute("error", e.getMessage());
            }
            resp.sendRedirect(req.getContextPath() + "/cards");
        }
    }
}
