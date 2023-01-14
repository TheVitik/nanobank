package com.thevitik.nanobank.controller.auth;

import com.thevitik.nanobank.service.auth.AuthService;
import com.thevitik.nanobank.service.auth.LoginService;
import com.thevitik.nanobank.service.validation.auth.LoginValidator;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "Logout", value = "/logout")
public class LogoutController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        AuthService auth = new AuthService(req);
        auth.logout();
        resp.sendRedirect(req.getContextPath() + "/login");
    }
}
