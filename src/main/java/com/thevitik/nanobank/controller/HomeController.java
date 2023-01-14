package com.thevitik.nanobank.controller;

import com.thevitik.nanobank.model.Card;
import com.thevitik.nanobank.repository.impl.CardRepository;
import com.thevitik.nanobank.service.auth.AuthService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "Home", value = "/home")
public class HomeController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        AuthService auth = new AuthService(req);
        if (!auth.isLogged()) {
            resp.sendRedirect(req.getContextPath() + "/login");
        } else {
            CardRepository repository = new CardRepository();
            try {
                List<Card> cards = repository.getUserCards(auth.getUser());
                req.setAttribute("cards", cards);
            } catch (Exception e) {
                req.getSession().setAttribute("error", "An error occurred while receiving data");
            }
            req.getRequestDispatcher("home.jsp").forward(req, resp);
        }
        req.getSession().removeAttribute("error");
        req.getSession().removeAttribute("success");
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }
}
