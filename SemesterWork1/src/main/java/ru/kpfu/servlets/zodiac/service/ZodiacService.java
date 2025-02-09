package ru.kpfu.servlets.zodiac.service;

import lombok.AllArgsConstructor;
import ru.kpfu.servlets.zodiac.dao.ZodiacDao;
import ru.kpfu.servlets.zodiac.entity.Zodiac;

import java.sql.SQLException;

@AllArgsConstructor
public class ZodiacService {
    private ZodiacDao zodiacDao;

    public Zodiac getZodiac(String birthDate) throws SQLException {
        return zodiacDao.getZodiac(birthDate);
    }

    public Integer getZodiacSign(String birthDate) throws SQLException {
        return zodiacDao.getZodiacSign(birthDate);
    }
}
