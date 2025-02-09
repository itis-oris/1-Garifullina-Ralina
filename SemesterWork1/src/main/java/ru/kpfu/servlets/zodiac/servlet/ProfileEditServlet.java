package ru.kpfu.servlets.zodiac.servlet;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import ru.kpfu.servlets.zodiac.entity.User;
import ru.kpfu.servlets.zodiac.service.UserService;
import ru.kpfu.servlets.zodiac.service.ZodiacService;
import ru.kpfu.servlets.zodiac.util.EmailAlreadyExistsException;
import ru.kpfu.servlets.zodiac.entity.Zodiac;

import java.io.IOException;
import java.sql.SQLException;
@WebServlet("/profileEdit")
public class ProfileEditServlet extends HttpServlet {
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
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        User user = (User) session.getAttribute("user");
        request.setAttribute("user", user);

        java.sql.Date birthDate = user.getBirthDate();
        if (birthDate != null) {
            String birthDateString = birthDate.toString();
            try {
                Zodiac zodiac = zodiacService.getZodiac(birthDateString);
                request.setAttribute("zodiac", zodiac);
            } catch (SQLException e) {
                request.setAttribute("error", "Не удалось определить знак зодиака");
            }
        } else {
            request.setAttribute("error", "Дата рождения не указана");
        }

        request.getRequestDispatcher("WEB-INF/view/user/profileEdit.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = (User) request.getSession().getAttribute("user");

        String newName = request.getParameter("name");
        String newBirthDate = request.getParameter("birthDate");
        String email = request.getParameter("email");
        boolean changePassword = "on".equals(request.getParameter("changePassword"));
        String newPassword = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmPassword");

        boolean hasErrors = false;

        if (newName == null || newName.isEmpty()) {
            request.setAttribute("errorNewName", "Имя не может быть пустым.");
            hasErrors = true;
        }

        if (newBirthDate == null || newBirthDate.isEmpty()) {
            request.setAttribute("errorNewBirthDate", "День рождения не может быть пустой.");
        } else if (isValidBirthDate(newBirthDate)) {
            request.setAttribute("errorNewBirthDate", "Введите корректную дату рождения");
        }


        if (email == null || email.isEmpty()) {
            request.setAttribute("errorEmail", "Email не может быть пустым.");
            hasErrors = true;
        } else if (!isValidEmail(email)) { // Проверка на корректность email
            request.setAttribute("errorEmail", "Некорректный формат email.");
            hasErrors = true;
        }


        // Если пользователь хочет изменить пароль
        if (changePassword) {
            if (newPassword == null || newPassword.isEmpty()) {
                request.setAttribute("errorPassword", "Новый пароль не может быть пустым.");
                hasErrors = true;
            } else if (!newPassword.equals(confirmPassword)) {
                request.setAttribute("errorConfirmPassword", "Пароли не совпадают.");
                hasErrors = true;
            }
        }


        if (hasErrors) {
            request.setAttribute("user", user);
            request.getRequestDispatcher("WEB-INF/view/user/profileEdit.jsp").forward(request, response);
        }else{
            try {
                user.setUserName(newName);
                java.sql.Date birthDate = java.sql.Date.valueOf(newBirthDate);
                user.setBirthDate(birthDate);
                user.setEmail(email);

                if (changePassword) {
                    user.setPassword(newPassword);
                }

                userService.update(user, changePassword);

                request.getSession().setAttribute("user", user);
                response.sendRedirect(getServletContext().getContextPath() + "/profile");

            } catch (EmailAlreadyExistsException e) {
                request.setAttribute("errorEmail", e.getMessage());
                request.getRequestDispatcher("WEB-INF/view/user/profileEdit.jsp").forward(request, response);

            } catch (SQLException e) {
                throw new RuntimeException("Ошибка при обновлении профиля клиента", e);
            }
        }


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
