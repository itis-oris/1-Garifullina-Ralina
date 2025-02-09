package ru.kpfu.servlets.zodiac.dao;

import ru.kpfu.servlets.zodiac.entity.User;
import ru.kpfu.servlets.zodiac.util.ConnectionProvider;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class UserDao {
    private final ConnectionProvider connectionProvider;

    public UserDao(ConnectionProvider connectionProvider) {
        this.connectionProvider = connectionProvider;
    }

    public void createUser(User user) throws SQLException {
        String sql = "INSERT INTO users (email, password, user_name, birth_date) VALUES (?, ?, ?, ?)";
        try (Connection connection = connectionProvider.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, user.getEmail());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getUserName());
            statement.setDate(4, user.getBirthDate());

            statement.executeUpdate();
        }
    }

    public User getUserById(int id) throws SQLException {
        String sql = "SELECT * FROM users WHERE user_id = ?";
        User user = null;
        try (Connection connection = connectionProvider.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                user = new User(
                        rs.getInt("user_id"),
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getString("user_name"),
                        rs.getDate("birth_date")
                );
            }
        }
        return user;
    }


    public User getUserByEmail(String email) throws SQLException {
        String sql = "SELECT * FROM users WHERE email = ?";
        User user = null;
        try (Connection connection = connectionProvider.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, email);
            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                user = new User(
                        rs.getInt("user_id"),
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getString("user_name"),
                        rs.getDate("birth_date")
                );
            }
        }

        return user;
    }

    public Optional<User> findByEmail(String email) throws SQLException {
        String sql = "SELECT * FROM users WHERE email = ?";
        try (Connection connection = connectionProvider.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, email);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return Optional.of( new User(
                            resultSet.getInt("user_id"),
                            resultSet.getString("email"),
                            resultSet.getString("password"),
                            resultSet.getString("user_name"),
                            resultSet.getDate("birth_date")
                    ));
                }
            }
        }

        return Optional.empty();
    }
    //Использование Optional позволяет избежать NullPointerException при работе с результатами запроса.


    public void updateUser(User user) throws SQLException {
        String sql = "UPDATE users SET user_name = ?, birth_date = ?, password = ? WHERE user_id = ?";
        try (Connection connection = connectionProvider.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, user.getUserName());
            statement.setDate(2, user.getBirthDate());
            statement.setString(3, user.getPassword());
            statement.setInt(4, user.getId());
            statement.executeUpdate();
        }
    }


    public void delete(int userId) throws SQLException {
        String sql = "DELETE FROM users WHERE user_id = ?";
        try (Connection connection = connectionProvider.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, userId);
            statement.executeUpdate();
        }
    }
}
