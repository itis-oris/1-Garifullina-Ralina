package ru.kpfu.servlets.zodiac.service;

import lombok.AllArgsConstructor;
import ru.kpfu.servlets.zodiac.dao.ZodiacLunarSignAdviceDao;
import ru.kpfu.servlets.zodiac.entity.ZodiacLunarSignAdvice;

import java.sql.SQLException;

@AllArgsConstructor
public class ZodiacLunarSignAdviceService {
    private ZodiacLunarSignAdviceDao zodiacLunarSignAdviceDao;

    public ZodiacLunarSignAdvice getZodiacLunarSignAdvice(String zodiacSign) throws SQLException {
        return zodiacLunarSignAdviceDao.getZodiacLunarSignAdvice(zodiacSign);
    }
}
