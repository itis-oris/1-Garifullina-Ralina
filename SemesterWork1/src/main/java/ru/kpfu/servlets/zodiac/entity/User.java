package ru.kpfu.servlets.zodiac.entity;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class User {
    private Integer id;
    private String email;
    private String password;
    private String userName;
    private java.sql.Date birthDate;

    public User(Integer id, String email, String password, String userName, java.sql.Date birthDate) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.userName = userName;
        this.birthDate = birthDate;
    }
}
