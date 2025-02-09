package ru.kpfu.servlets.zodiac.service;

import lombok.AllArgsConstructor;
import ru.kpfu.servlets.zodiac.dao.MoonPhaseAdviceDao;
import ru.kpfu.servlets.zodiac.entity.MoonPhaseAdvice;

import java.sql.SQLException;

@AllArgsConstructor
public class MoonPhaseAdviceService {
    private MoonPhaseAdviceDao moonPhaseAdviceDao;

    public MoonPhaseAdvice getMoonPhaseAdvice(String moonPhase) throws SQLException {
        return moonPhaseAdviceDao.getMoonPhaseAdvice(moonPhase);
    }
}
