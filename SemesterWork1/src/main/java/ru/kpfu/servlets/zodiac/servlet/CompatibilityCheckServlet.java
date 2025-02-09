package ru.kpfu.servlets.zodiac.servlet;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.kpfu.servlets.zodiac.entity.ZodiacCompatibility;
import ru.kpfu.servlets.zodiac.service.ZodiacCompatibilityService;
import ru.kpfu.servlets.zodiac.service.ZodiacService;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/compatibilityCheck")
public class CompatibilityCheckServlet extends HttpServlet {

    private ZodiacService zodiacService;
    private ZodiacCompatibilityService zodiacCompatibilityService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        this.zodiacService = (ZodiacService)getServletContext().getAttribute("zodiacService");
        this.zodiacCompatibilityService = (ZodiacCompatibilityService) getServletContext().getAttribute("zodiacCompatibilityService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        getServletContext().getRequestDispatcher("/WEB-INF/view/zodiac/compatibilityCheck.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String manBirthDate = request.getParameter("manBirthDate");
        String womanBirthDate = request.getParameter("womanBirthDate");


        if (manBirthDate == null || womanBirthDate == null || manBirthDate.isEmpty() || womanBirthDate.isEmpty()) {
            request.setAttribute("error", "Пожалуйста, заполните оба поля даты рождения");
            getServletContext().getRequestDispatcher("/WEB-INF/view/zodiac/compatibilityCheck.jsp").forward(request, response);
            return;
        }

        if (!isValidBirthDate(manBirthDate) || !isValidBirthDate(womanBirthDate)) {
            request.setAttribute("error", "Введите корректные даты рождения");
            getServletContext().getRequestDispatcher("/WEB-INF/view/zodiac/compatibilityCheck.jsp").forward(request, response);
            return;
        }

        Integer manSign;
        Integer womanSign;

        try {
            manSign = zodiacService.getZodiacSign(manBirthDate);
            womanSign = zodiacService.getZodiacSign(womanBirthDate);
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }

        ZodiacCompatibility result;

        try {
            result = zodiacCompatibilityService.getCompatibility(manSign, womanSign);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        if (result != null) {
            request.setAttribute("result", result);
            getServletContext().getRequestDispatcher("/WEB-INF/view/zodiac/compatibilityResult.jsp").forward(request, response);
        } else {
            request.setAttribute("error", "Совместимость не найдена");
            getServletContext().getRequestDispatcher("/WEB-INF/view/zodiac/compatibilityCheck.jsp").forward(request, response);
        }
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
