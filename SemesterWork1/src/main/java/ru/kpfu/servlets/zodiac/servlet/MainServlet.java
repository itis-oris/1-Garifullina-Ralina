package ru.kpfu.servlets.zodiac.servlet;


import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.kpfu.servlets.zodiac.entity.MoonPhaseAdvice;
import ru.kpfu.servlets.zodiac.entity.User;
import ru.kpfu.servlets.zodiac.entity.Zodiac;
import ru.kpfu.servlets.zodiac.entity.ZodiacLunarSignAdvice;
import ru.kpfu.servlets.zodiac.service.MoonPhaseAdviceService;
import ru.kpfu.servlets.zodiac.service.ZodiacLunarSignAdviceService;
import ru.kpfu.servlets.zodiac.service.ZodiacService;
import ru.kpfu.servlets.zodiac.util.MoonPhaseCalculator;
import ru.kpfu.servlets.zodiac.util.MoonZodiacCalculator;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;

@WebServlet("/main")
public class MainServlet extends HttpServlet {

    private ZodiacService zodiacService;
    private MoonPhaseAdviceService moonPhaseAdviceService;
    private ZodiacLunarSignAdviceService zodiacLunarSignAdviceService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        this.zodiacService = (ZodiacService) getServletContext().getAttribute("zodiacService");
        this.moonPhaseAdviceService = (MoonPhaseAdviceService) getServletContext().getAttribute("moonPhaseAdviceService");
        this.zodiacLunarSignAdviceService = (ZodiacLunarSignAdviceService) getServletContext().getAttribute("zodiacLunarSignAdviceService");
    }


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Получаем текущую дату
        Date now = new Date();
        // Вычисляем фазу Луны
        String moonPhase = MoonPhaseCalculator.getMoonPhase(now);
        // Вычисляем знак зодиака Луны
        String moonZodiacSign = MoonZodiacCalculator.getMoonZodiac(now);

        try {
            MoonPhaseAdvice moonPhaseAdvice = moonPhaseAdviceService.getMoonPhaseAdvice(moonPhase);
            request.setAttribute("moonPhaseAdvice", moonPhaseAdvice);
        } catch (SQLException e) {
            request.setAttribute("error", "Не удалось получить информацию по фазе луны");
        }


        try {
            ZodiacLunarSignAdvice zodiacSignAdvice = zodiacLunarSignAdviceService.getZodiacLunarSignAdvice(moonZodiacSign);
            request.setAttribute("zodiacSignAdvice", zodiacSignAdvice);
        } catch (SQLException e) {
            request.setAttribute("error", "Не удалось получить информацию по знаку зодиака луны");
        }


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
        RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher("/WEB-INF/view/main.jsp");
        dispatcher.forward(request, response);
    }
}
