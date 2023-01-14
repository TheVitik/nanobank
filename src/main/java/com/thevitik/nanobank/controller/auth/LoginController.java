package com.thevitik.nanobank.controller.auth;

import com.thevitik.nanobank.repository.impl.UserRepository;
import com.thevitik.nanobank.service.auth.AuthService;
import com.thevitik.nanobank.service.auth.LoginService;
import com.thevitik.nanobank.service.validation.auth.LoginValidator;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "Login", value = "/login")
public class LoginController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        AuthService auth = new AuthService(req);
        if (auth.isLogged()) {
            resp.sendRedirect(req.getContextPath() + "/home");
        } else {
            req.getRequestDispatcher("login.jsp").forward(req, resp);
        }
        req.getSession().removeAttribute("error");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LoginService service = new LoginService(req, new LoginValidator(), new UserRepository());
        if (service.isLogged()) {
            resp.sendRedirect(req.getContextPath() + "/home");
        } else {
            try {
                service.login();
                resp.sendRedirect(req.getContextPath() + "/home");
            } catch (IllegalStateException e) {
                resp.sendRedirect(req.getContextPath() + "/home");
            } catch (Exception e) {
                req.getSession().setAttribute("error", e.getMessage());
                resp.sendRedirect(req.getContextPath() + "/login");
            }
        }
    }
}
