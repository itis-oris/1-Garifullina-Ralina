package ru.kpfu.servlets.zodiac.service;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.kpfu.servlets.zodiac.dao.UserDao;
import ru.kpfu.servlets.zodiac.entity.User;
import ru.kpfu.servlets.zodiac.util.EmailAlreadyExistsException;

import java.sql.SQLException;

@AllArgsConstructor
public class UserService {
    private UserDao userDao;
    private PasswordEncoder passwordEncoder;

    public User login(String email, String password) throws SQLException {
        return userDao.findByEmail(email)
                .filter(user -> passwordEncoder.matches(password, user.getPassword()))
                .orElse(null);
    }

    public void register(User user) throws SQLException, EmailAlreadyExistsException {
        if (userDao.getUserByEmail(user.getEmail()) != null) {
            throw new EmailAlreadyExistsException("Клиент с почтой " + user.getEmail() + " уже существует.");
        }
        String hashedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(hashedPassword);
        userDao.createUser(user);
    }

    public void update(User user, boolean changePassword) throws SQLException, EmailAlreadyExistsException {
        if (userDao.findByEmail(user.getEmail()).isPresent()
                && userDao.findByEmail(user.getEmail()).get().getId() != user.getId()) {
            throw new EmailAlreadyExistsException("Клиент с почтой " + user.getEmail() + " уже существует.");
        }

        if (changePassword) {
            String hashedPassword = passwordEncoder.encode(user.getPassword());
            user.setPassword(hashedPassword);
        }

        userDao.updateUser(user);
    }

    // Удаление пользователя
    public void delete(int userId) throws SQLException {
        userDao.delete(userId);
    }

    public void auth(User user, HttpServletRequest req) {
        req.getSession().setAttribute("user", user);
    }
    public boolean isNonAnonymous(HttpServletRequest req) {
        return req.getSession().getAttribute("user") != null;
    }

    public User getUser(HttpServletRequest req) {
        return (User) req.getSession().getAttribute("user");
    }
}
