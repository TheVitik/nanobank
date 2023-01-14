package com.thevitik.nanobank.controller.admin;

import com.thevitik.nanobank.model.Card;
import com.thevitik.nanobank.model.UnblockRequest;
import com.thevitik.nanobank.repository.impl.CardRepository;
import com.thevitik.nanobank.repository.impl.UnblockRequestRepository;
import com.thevitik.nanobank.service.auth.AuthService;
import com.thevitik.nanobank.service.card.CardAccessService;
import com.thevitik.nanobank.service.validation.card.CardAccessValidator;
import com.thevitik.nanobank.service.validation.card.UnblockValidator;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "UnblockRequests", value = "/admin/requests")
public class UnblockRequestController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        AuthService auth = new AuthService(req);
        if (!auth.isLogged()) {
            resp.sendRedirect(req.getContextPath() + "/login");
        } else {
            UnblockRequestRepository repository = new UnblockRequestRepository();
            try {
                List<UnblockRequest> requests = repository.getAll();
                req.setAttribute("requests", requests);
            } catch (Exception e) {
                req.getSession().setAttribute("error", e.getMessage());
            }
            req.getRequestDispatcher("../admin/requests.jsp").forward(req, resp);
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
            CardAccessService service = new CardAccessService(new CardRepository(), new UnblockRequestRepository(), auth);
            try {
                service.confirm(req, new UnblockValidator());
            } catch (Exception e) {
                req.getSession().setAttribute("error", e.getMessage());
            }
            resp.sendRedirect(req.getContextPath() + "/admin/requests");
        }
    }
}
