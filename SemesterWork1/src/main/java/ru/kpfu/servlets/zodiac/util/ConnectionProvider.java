package ru.kpfu.servlets.zodiac.util;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.FlywayException;

import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionProvider {
    final static Logger logger = LogManager.getLogger(ConnectionProvider.class);

    private static HikariDataSource dataSource;
    private static ConnectionProvider instance;
    private static final String url = "jdbc:postgresql://localhost:5432/horoscope";
    private static final String username = "postgres";
    private static final String password = "Garral7112386";

    private ConnectionProvider() {
        try {
            Class.forName("org.postgresql.Driver");

            HikariConfig config = new HikariConfig();
            config.setJdbcUrl(url);
            config.setUsername(username);
            config.setPassword(password);
            config.setConnectionTimeout(50000);
            config.setMaximumPoolSize(10);

            dataSource = new HikariDataSource(config);

            Flyway flyway = Flyway.configure().dataSource(dataSource).load();
            logger.info("start migration");
            flyway.migrate();
            logger.info("migration done");
        } catch (ClassNotFoundException | FlywayException e) {
            logger.error("", e);
        }
    }

    public static ConnectionProvider getInstance() {
        if (instance == null) {
            synchronized (ConnectionProvider.class) {
                if (instance == null) {
                    instance = new ConnectionProvider();
                }
            }
        }
        return instance;
    }

    // Метод для получения соединения
    public synchronized Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    // Метод для освобождения соединения
    public synchronized void releaseConnection(Connection connection) throws SQLException {
        if (connection != null) {
            connection.close();
        }
    }

    // Метод для закрытия пула соединений
    public void destroy() {
        if (dataSource != null) {
            dataSource.close();
            logger.info("Connection pool has been closed");
        }
    }

}