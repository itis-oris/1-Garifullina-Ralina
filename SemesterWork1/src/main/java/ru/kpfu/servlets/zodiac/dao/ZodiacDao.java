package ru.kpfu.servlets.zodiac.dao;

import ru.kpfu.servlets.zodiac.entity.Zodiac;
import ru.kpfu.servlets.zodiac.util.ConnectionProvider;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


public class ZodiacDao {
    private final ConnectionProvider connectionProvider;

    public ZodiacDao(ConnectionProvider connectionProvider) {
        this.connectionProvider = connectionProvider;
    }

    public Zodiac getZodiac(String birthDate) throws SQLException {
        String sql = "SELECT sign_id, sign_name, start_month, end_month, start_day, end_day, strong_side, little_weakness, information, image_path " +
                "FROM zodiac_signs";
        Zodiac zodiac = null;

        LocalDate birthDateObj = LocalDate.parse(birthDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        int month = birthDateObj.getMonthValue();
        int day = birthDateObj.getDayOfMonth();

        try (Connection connection = connectionProvider.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet rs = statement.executeQuery()) {

            while (rs.next()) {
                int signId = rs.getInt("sign_id");
                String signName = rs.getString("sign_name");
                int startMonth = rs.getInt("start_month");
                int endMonth = rs.getInt("end_month");
                int startDay = rs.getInt("start_day");
                int endDay = rs.getInt("end_day");
                String strongSide = rs.getString("strong_side");
                String weakness = rs.getString("little_weakness");
                String info = rs.getString("information");
                String imagePath = rs.getString("image_path");

                // Проверяем, попадает ли дата рождения в диапазон знака зодиака
                if ((month == startMonth && day >= startDay) || (month == endMonth && day <= endDay)) {
                    zodiac = new Zodiac(signId, signName, strongSide, weakness, info, imagePath);
                    break; // Прекращаем поиск после нахождения знака
                }
            }
        }
        return zodiac;
    }

    public Integer getZodiacSign(String birthDate) throws SQLException {
        String sql = "SELECT sign_id, start_month, end_month, start_day, end_day FROM zodiac_signs";
        Integer zodiacSignId = null;

        LocalDate birthDateObj = LocalDate.parse(birthDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        int month = birthDateObj.getMonthValue();
        int day = birthDateObj.getDayOfMonth();

        try (Connection connection = connectionProvider.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet rs = statement.executeQuery()) {

            while (rs.next()) {
                int signId = rs.getInt("sign_id");
                int startMonth = rs.getInt("start_month");
                int endMonth = rs.getInt("end_month");
                int startDay = rs.getInt("start_day");
                int endDay = rs.getInt("end_day");

                // Проверяем, попадает ли дата рождения в диапазон знака зодиака
                if ((month == startMonth && day >= startDay) || (month == endMonth && day <= endDay)) {
                    zodiacSignId = signId;
                    break; // Прекращаем поиск после нахождения знака
                }
            }
        }
        return zodiacSignId;
    }
}

