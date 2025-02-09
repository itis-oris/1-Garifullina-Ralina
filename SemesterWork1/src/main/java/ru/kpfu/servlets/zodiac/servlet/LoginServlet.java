package ru.kpfu.servlets.zodiac.servlet;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.kpfu.servlets.zodiac.entity.User;
import ru.kpfu.servlets.zodiac.service.UserService;

import java.io.IOException;
import java.sql.*;
@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private UserService userService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        this.userService = (UserService) getServletContext().getAttribute("userService");
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        User user;
        try {
            user = userService.login(email, password);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        if (user != null ){
            userService.auth(user, request);
            response.sendRedirect(getServletContext().getContextPath() + "/main");
        } else {
            request.setAttribute("error", "Неверный email или пароль");
            getServletContext().getRequestDispatcher("/WEB-INF/view/login.jsp").forward(request, response);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        getServletContext().getRequestDispatcher("/WEB-INF/view/login.jsp").forward(request, response);
    }
}
