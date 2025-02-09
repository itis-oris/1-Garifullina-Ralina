package ru.kpfu.servlets.zodiac.dao;

import ru.kpfu.servlets.zodiac.entity.ZodiacLunarSignAdvice;
import ru.kpfu.servlets.zodiac.util.ConnectionProvider;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ZodiacLunarSignAdviceDao {
    private final ConnectionProvider connectionProvider;

    public ZodiacLunarSignAdviceDao(ConnectionProvider connectionProvider) {
        this.connectionProvider = connectionProvider;
    }

    public ZodiacLunarSignAdvice getZodiacLunarSignAdvice(String zodiacSign) throws SQLException {
        String sql = "SELECT * FROM zodiac_lunar_sign_advice WHERE zodiac_sign = ?";
        try (Connection connection = connectionProvider.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, zodiacSign);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return new ZodiacLunarSignAdvice(
                            resultSet.getInt("id"),
                            resultSet.getString("zodiac_sign"),
                            resultSet.getString("general_info"),
                            resultSet.getString("possibilities"),
                            resultSet.getString("prohibitions"),
                            resultSet.getString("mood"),
                            resultSet.getString("initiatives"),
                            resultSet.getString("work_business"),
                            resultSet.getString("finances"),
                            resultSet.getString("love_family"),
                            resultSet.getString("health"),
                            resultSet.getString("creativity_education")
                    );
                }
            }
        }
        return null;
    }
}
