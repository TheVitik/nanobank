package com.thevitik.nanobank.controller.auth;

import com.thevitik.nanobank.repository.impl.UserRepository;
import com.thevitik.nanobank.service.auth.AuthService;
import com.thevitik.nanobank.service.auth.RegisterService;
import com.thevitik.nanobank.service.validation.auth.RegisterValidator;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "Register", value = "/register")
public class RegisterController extends HttpServlet {

    /**
     * Show register page
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        AuthService auth = new AuthService(req);
        if (auth.isLogged()) {
            resp.sendRedirect(req.getContextPath() + "/home");
        } else {
            req.getRequestDispatcher("register.jsp").forward(req, resp);
        }
        req.getSession().removeAttribute("error");
    }

    /**
     * Register new user
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RegisterService service = new RegisterService(req, new RegisterValidator(), new UserRepository());
        AuthService auth = new AuthService(req);
        if (!auth.isLogged()) {
            resp.sendRedirect(req.getContextPath() + "/login");
        } else {
            try {
                service.register();
                resp.sendRedirect(req.getContextPath() + "/home");
            } catch (Exception e) {
                req.getSession().setAttribute("error", e.getMessage());
                resp.sendRedirect(req.getContextPath() + "/register");
            }
        }
    }
}
