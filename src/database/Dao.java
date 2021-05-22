package database;

import client.User;

import java.sql.*;

public class Dao {
    public void insertUser(Connection db, User user) throws SQLException {
        Statement statement = db.createStatement();
        String createSql = "INSERT INTO users (full_name, username,password,email,phone,gender) VALUES (?, ?, ?, ?,?,?)";
        var createStmt = db.prepareStatement(createSql);
        createStmt.setString(1, user.getFullName());
        createStmt.setString(2, user.getUserName());
        createStmt.setString(3, user.getPassword());
        createStmt.setString(4, user.getEmail());
        createStmt.setString(5, user.getPhoneNumber());
        createStmt.setString(6, user.getGender());
        try {
            createStmt.execute();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

}
