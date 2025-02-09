package ru.kpfu.servlets.zodiac.servlet;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.kpfu.servlets.zodiac.entity.User;
import ru.kpfu.servlets.zodiac.service.UserService;
import ru.kpfu.servlets.zodiac.util.EmailAlreadyExistsException;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/registration")
public class RegistrationServlet extends HttpServlet {
    private UserService userService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        this.userService = (UserService) getServletContext().getAttribute("userService");
    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        getServletContext().getRequestDispatcher("/WEB-INF/view/registration.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String userName = request.getParameter("userName");
        String birthDateStr = request.getParameter("birthDate");

        if (isEmpty(email) || isEmpty(password) || isEmpty(userName) || isEmpty(birthDateStr)) {
            request.setAttribute("error", "Пожалуйста, заполните все обязательные поля");
            getServletContext().getRequestDispatcher("/WEB-INF/view/registration.jsp").forward(request, response);
            return;
        }


        if (isValidEmail(email)) {
            if (isValidBirthDate(birthDateStr)) {
                java.sql.Date birthDate = java.sql.Date.valueOf(birthDateStr);

                User user = new User(0, email, password, userName, birthDate);
                try {
                    userService.register(user);
                    userService.auth(user, request);

                    response.sendRedirect(getServletContext().getContextPath() + "/login"); // Перенаправление на страницу профиля
                } catch (SQLException e) {
                    request.setAttribute("error", "Ошибка при регистрации: " + e.getMessage());
                    getServletContext().getRequestDispatcher("/WEB-INF/view/registration.jsp").forward(request, response);
                } catch (EmailAlreadyExistsException e) {
                    throw new RuntimeException(e);
                }
            } else {
                request.setAttribute("error", "Неверная дата рождения");
                getServletContext().getRequestDispatcher("/WEB-INF/view/registration.jsp").forward(request, response);
            }
        } else {
            request.setAttribute("error", "Неверный формат email");
            getServletContext().getRequestDispatcher("/WEB-INF/view/registration.jsp").forward(request, response);
        }
    }

    private boolean isEmpty(String value) {
        return value == null || value.trim().isEmpty();
    }

    private boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        return email.matches(emailRegex);
    }

    private boolean isValidBirthDate(String birthDateStr) {
        try {
            java.sql.Date birthDate = java.sql.Date.valueOf(birthDateStr);

            // Проверяем, что дата больше 1 января 1900 года
            if (birthDate.before(java.sql.Date.valueOf("1900-01-01"))) {
                return false;
            }

            // Проверяем, что дата меньше 20 декабря 2024 года
            if (birthDate.after(java.sql.Date.valueOf("2024-12-20"))) {
                return false;
            }

            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

}
