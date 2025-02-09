package ru.kpfu.servlets.zodiac.dao;

import ru.kpfu.servlets.zodiac.entity.ZodiacCompatibility;
import ru.kpfu.servlets.zodiac.util.ConnectionProvider;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ZodiacCompatibilityDao {
    private final ConnectionProvider connectionProvider;

    public ZodiacCompatibilityDao(ConnectionProvider connectionProvider) {
        this.connectionProvider = connectionProvider;
    }

    public ZodiacCompatibility getCompatibility(int signIdMan, int signIdWoman) throws SQLException {
        String sql = "SELECT * FROM zodiac_compatibility WHERE sign1 = ? AND sign2 = ?";
        ZodiacCompatibility zodiacCompatibility = null;
        try (Connection connection = connectionProvider.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, signIdMan);
            statement.setInt(2, signIdWoman);
            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                zodiacCompatibility = new ZodiacCompatibility(
                        rs.getInt("zodiac_compatibility_id"),
                        rs.getInt("sign1"),
                        rs.getInt("sign2"),
                        rs.getString("percent_info"),
                        rs.getString("love_info"),
                        rs.getString("family_info"),
                        rs.getString("friends_info"),
                        rs.getString("work_info")
                );
            }
        }
        return zodiacCompatibility;
    }
}
