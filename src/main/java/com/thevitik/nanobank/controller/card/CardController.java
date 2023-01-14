package com.thevitik.nanobank.controller.card;

import com.thevitik.nanobank.model.Card;
import com.thevitik.nanobank.repository.impl.CardRepository;
import com.thevitik.nanobank.service.auth.AuthService;
import com.thevitik.nanobank.service.card.CardBalanceService;
import com.thevitik.nanobank.service.card.CardService;
import com.thevitik.nanobank.service.card.CreateCardService;
import com.thevitik.nanobank.service.validation.card.DepositValidator;
import com.thevitik.nanobank.service.validation.card.GetCardsValidator;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Random;

@WebServlet(name = "Cards", value = "/cards")
public class CardController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        AuthService auth = new AuthService(req);
        if (!auth.isLogged()) {
            resp.sendRedirect(req.getContextPath() + "/login");
        } else {
            CardService service = new CardService(new CardRepository(), auth);
            try {
                List<Card> cards = service.getCards(req, new GetCardsValidator());
                req.setAttribute("cards", cards);
            } catch (Exception e) {
                req.getSession().setAttribute("error", e.getMessage());
            }
            req.getRequestDispatcher("cards.jsp").forward(req, resp);
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
            CreateCardService cardService = new CreateCardService(new CardRepository(), new Random());
            try {
                cardService.create(auth.getUser());
            } catch (Exception e) {
                req.getSession().setAttribute("error", e.getMessage());
            }
            resp.sendRedirect(req.getContextPath() + "/cards");
        }
    }
}
