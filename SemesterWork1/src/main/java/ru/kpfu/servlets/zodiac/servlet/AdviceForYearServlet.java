package ru.kpfu.servlets.zodiac.servlet;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.kpfu.servlets.zodiac.entity.AdviceForYear;
import ru.kpfu.servlets.zodiac.entity.Zodiac;
import ru.kpfu.servlets.zodiac.service.AdviceForYearService;


import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/adviceForYear")
public class AdviceForYearServlet extends HttpServlet {
    private AdviceForYearService adviceForYearService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        this.adviceForYearService = (AdviceForYearService) getServletContext().getAttribute("adviceForYearService");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Zodiac zodiac = (Zodiac) request.getSession().getAttribute("zodiac");

        AdviceForYear advice;

        try {
            advice = adviceForYearService.getCompatibility(zodiac.getSignId());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        if (advice != null) {
            request.setAttribute("advice", advice);
            getServletContext().getRequestDispatcher("/WEB-INF/view/zodiac/adviceForYear.jsp").forward(request, response);
        }
    }
}
