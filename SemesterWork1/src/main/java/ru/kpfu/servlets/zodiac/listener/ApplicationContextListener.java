package ru.kpfu.servlets.zodiac.listener;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.kpfu.servlets.zodiac.config.EncoderConfiguration;
import ru.kpfu.servlets.zodiac.dao.*;
import ru.kpfu.servlets.zodiac.service.*;
import ru.kpfu.servlets.zodiac.util.ConnectionProvider;


@WebListener
public class ApplicationContextListener implements ServletContextListener {
    final static Logger logger = LogManager.getLogger(ApplicationContextListener.class);

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        EncoderConfiguration encoderConfiguration = new EncoderConfiguration();
        PasswordEncoder passwordEncoder = encoderConfiguration.bCryptPasswordEncoder();

        logger.info("contextInitialized");

        ConnectionProvider connectionProvider;
        connectionProvider = ConnectionProvider.getInstance();


        ZodiacDao zodiacDao = new ZodiacDao(connectionProvider);
        UserDao userDao = new UserDao(connectionProvider);
        ZodiacCompatibilityDao zodiacCompatibilityDao = new ZodiacCompatibilityDao(connectionProvider);
        AdviceForYearDao adviceForYearDao = new AdviceForYearDao(connectionProvider);
        MoonPhaseAdviceDao moonPhaseAdviceDao = new MoonPhaseAdviceDao(connectionProvider);
        ZodiacLunarSignAdviceDao zodiacLunarSignAdviceDao = new ZodiacLunarSignAdviceDao(connectionProvider);

        ZodiacService zodiacService = new ZodiacService(zodiacDao);
        UserService userService = new UserService(userDao, passwordEncoder);
        ZodiacCompatibilityService zodiacCompatibilityService = new ZodiacCompatibilityService(zodiacCompatibilityDao);
        AdviceForYearService adviceForYearService = new AdviceForYearService(adviceForYearDao);
        MoonPhaseAdviceService moonPhaseAdviceService = new MoonPhaseAdviceService(moonPhaseAdviceDao);
        ZodiacLunarSignAdviceService zodiacLunarSignAdviceService = new ZodiacLunarSignAdviceService(zodiacLunarSignAdviceDao);

        ServletContext servletContext = sce.getServletContext();

        servletContext.setAttribute("zodiacService", zodiacService);
        servletContext.setAttribute("userService", userService);
        servletContext.setAttribute("zodiacCompatibilityService", zodiacCompatibilityService);
        servletContext.setAttribute("adviceForYearService", adviceForYearService);
        servletContext.setAttribute("moonPhaseAdviceService", moonPhaseAdviceService);
        servletContext.setAttribute("zodiacLunarSignAdviceService", zodiacLunarSignAdviceService);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        ConnectionProvider.getInstance().destroy();
    }
}