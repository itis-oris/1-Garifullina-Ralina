package ru.kpfu.servlets.zodiac.dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.kpfu.servlets.zodiac.entity.AdviceForYear;
import ru.kpfu.servlets.zodiac.util.ConnectionProvider;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AdviceForYearDao {
    final static Logger logger = LogManager.getLogger(AdviceForYearDao.class);
    private final ConnectionProvider connectionProvider;

    public AdviceForYearDao(ConnectionProvider connectionProvider) {
        this.connectionProvider = connectionProvider;
    }

    public AdviceForYear getCompatibility(int signId) {
        String sql = "SELECT * FROM advice_for_year WHERE zodiac_sign_id = ?";
        AdviceForYear adviceForYear = null;
        try {
            Connection connection = connectionProvider.getConnection();
            connection.setAutoCommit(false);
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, signId);
            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                adviceForYear = new AdviceForYear(
                        rs.getInt("advice_for_year_id"),
                        rs.getInt("zodiac_sign_id"),
                        rs.getInt("advice_year"),
                        rs.getString("advice_text")
                );
            }

            statement.close();
            // rollback | commit
            connection.commit();
            connectionProvider.releaseConnection(connection);
        }catch (SQLException e) {
            logger.error(e);
        }
        return adviceForYear;
    }
}
