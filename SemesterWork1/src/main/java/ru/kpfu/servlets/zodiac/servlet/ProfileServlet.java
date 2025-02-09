package ru.kpfu.servlets.zodiac.servlet;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.kpfu.servlets.zodiac.entity.User;
import ru.kpfu.servlets.zodiac.entity.Zodiac;
import ru.kpfu.servlets.zodiac.service.UserService;
import ru.kpfu.servlets.zodiac.service.ZodiacService;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/profile")
public class ProfileServlet extends HttpServlet {
    private UserService userService;
    private ZodiacService zodiacService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        this.userService = (UserService) getServletContext().getAttribute("userService");
        this.zodiacService = (ZodiacService) getServletContext().getAttribute("zodiacService");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = (User) request.getSession().getAttribute("user");
        request.setAttribute("user", user);

        // Получаем знак зодиака, основываясь на дате рождения пользователя
        java.sql.Date birthDate = user.getBirthDate();
        if (birthDate != null) {
            String birthDateString = birthDate.toString();
            try {
                Zodiac zodiac = zodiacService.getZodiac(birthDateString);
                request.setAttribute("zodiac", zodiac);
                request.getSession().setAttribute("zodiac", zodiac);
            } catch (SQLException e) {
                request.setAttribute("error", "Не удалось определить знак зодиака");
            }
        } else {
            request.setAttribute("error", "Дата рождения не указана");
        }

        RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher("/WEB-INF/view/user/profile.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        User user = (User) request.getSession().getAttribute("user");

        try {
            // Удаление пользователя
            userService.delete(user.getId());
            request.getSession().invalidate();
            response.sendRedirect(getServletContext().getContextPath() + "/registration");
        } catch (SQLException e) {
            request.setAttribute("error", "Не удалось удалить аккаунт. Попробуйте позже.");
            doGet(request, response); // Возвращаем пользователя на страницу профиля
        }
    }
}

