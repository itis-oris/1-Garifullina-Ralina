package ru.kpfu.servlets.zodiac.service;

import lombok.AllArgsConstructor;
import ru.kpfu.servlets.zodiac.dao.ZodiacCompatibilityDao;
import ru.kpfu.servlets.zodiac.entity.ZodiacCompatibility;
import java.sql.SQLException;

@AllArgsConstructor
public class ZodiacCompatibilityService {
    private ZodiacCompatibilityDao zodiacCompatibilityDao;

    public ZodiacCompatibility getCompatibility(int signIdMan, int signIdWoman) throws SQLException {
        return zodiacCompatibilityDao.getCompatibility(signIdMan, signIdWoman);
    }
}
