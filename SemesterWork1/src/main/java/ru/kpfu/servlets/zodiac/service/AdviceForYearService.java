package ru.kpfu.servlets.zodiac.service;

import lombok.AllArgsConstructor;
import ru.kpfu.servlets.zodiac.dao.AdviceForYearDao;
import ru.kpfu.servlets.zodiac.entity.AdviceForYear;

import java.sql.SQLException;

@AllArgsConstructor
public class AdviceForYearService {
    private AdviceForYearDao adviceForYearDao;

    public AdviceForYear getCompatibility(int signId) throws SQLException {
        return adviceForYearDao.getCompatibility(signId);
    }
}
