package ru.netology.db;

import lombok.SneakyThrows;

import java.sql.Connection;
import java.sql.DriverManager;

public class SqlGetters {
    @SneakyThrows
    public Connection getConnection() {
        return DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/app", "app", "pass"
        );
    }

    @SneakyThrows
    public void cleanDatabase() {
        var conn = getConnection();
        conn.prepareStatement("DROP TABLE IF EXISTS cards;");
        conn.prepareStatement("DROP TABLE IF EXISTS users;");
        conn.prepareStatement("DROP TABLE IF EXISTS auth_codes");
        conn.prepareStatement("DROP TABLE IF EXISTS card_transactions");
    }

    @SneakyThrows
    public String getUserIdVasya() {
        var conn = getConnection();
        var dataStmt = conn.createStatement().executeQuery("SELECT * FROM users");
        String id = "";
        try (var rs = dataStmt) {
            while (rs.next()) {
                id = rs.getString("id");
                var login = rs.getString("login");
                if (login.equals("vasya")) {
                    break;
                }
            }
        }
        return id;
    }

    @SneakyThrows
    public String getCode() {
        var conn = getConnection();
        var dataStmt = conn.createStatement().executeQuery("SELECT * FROM auth_codes ORDER BY created DESC");
        String code = "";
        try (var rs = dataStmt) {
            while (rs.next()) {
                code = rs.getString("code");
                var user_id = rs.getString("user_id");
                if (user_id.equalsIgnoreCase(getUserIdVasya())) {
                    break;
                }
            }
        }
        return code;
    }
}
