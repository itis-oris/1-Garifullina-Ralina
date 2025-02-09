package ru.kpfu.servlets.zodiac.dao;

import ru.kpfu.servlets.zodiac.entity.MoonPhaseAdvice;
import ru.kpfu.servlets.zodiac.util.ConnectionProvider;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MoonPhaseAdviceDao {
    private final ConnectionProvider connectionProvider;

    public MoonPhaseAdviceDao(ConnectionProvider connectionProvider) {
        this.connectionProvider = connectionProvider;
    }

    public MoonPhaseAdvice getMoonPhaseAdvice(String moonPhase) throws SQLException {
        String sql = "SELECT * FROM moon_phase_advice WHERE moon_phase = ?";
        try (Connection connection = connectionProvider.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, moonPhase);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return new MoonPhaseAdvice(
                            resultSet.getInt("id"),
                            resultSet.getString("moon_phase"),
                            resultSet.getString("general_info"),
                            resultSet.getString("affairs"),
                            resultSet.getString("work"),
                            resultSet.getString("home_work"),
                            resultSet.getString("money"),
                            resultSet.getString("love_relationships"),
                            resultSet.getString("communication"),
                            resultSet.getString("travel"),
                            resultSet.getString("hair_care"),
                            resultSet.getString("beauty_self_care"),
                            resultSet.getString("health"),
                            resultSet.getString("nutrition")
                    );
                }
            }
        }
        return null;
    }
}
