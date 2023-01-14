package com.thevitik.nanobank.controller.admin;

import com.thevitik.nanobank.model.Card;
import com.thevitik.nanobank.model.User;
import com.thevitik.nanobank.repository.impl.CardRepository;
import com.thevitik.nanobank.repository.impl.UnblockRequestRepository;
import com.thevitik.nanobank.repository.impl.UserRepository;
import com.thevitik.nanobank.service.auth.AuthService;
import com.thevitik.nanobank.service.auth.UserService;
import com.thevitik.nanobank.service.card.CardAccessService;
import com.thevitik.nanobank.service.validation.auth.UpdateUserValidator;
import com.thevitik.nanobank.service.validation.card.UnblockValidator;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "AdminUsers", value = "/admin/users")
public class UserController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        AuthService auth = new AuthService(req);
        if (!auth.isLogged()) {
            resp.sendRedirect(req.getContextPath() + "/login");
        } else {
            UserService service = new UserService(new UserRepository(), auth);
            try {
                List<User> users = service.getUsers();
                req.setAttribute("users", users);
            } catch (Exception e) {
                req.getSession().setAttribute("error", e.getMessage());
            }
            req.getRequestDispatcher("../admin/users.jsp").forward(req, resp);
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
            UserService service = new UserService(new UserRepository(), auth);
            try {
                service.update(req, new UpdateUserValidator());
            } catch (Exception e) {
                req.getSession().setAttribute("error", e.getMessage());
            }
            resp.sendRedirect(req.getContextPath() + "/admin/users");
        }
    }
}
