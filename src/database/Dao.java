package database;

import client.User;

import java.sql.*;
import java.util.ArrayList;

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

    public void updateStatus(Connection db, String username, String status) throws SQLException {
        Statement statement = db.createStatement();
        String createSql = "UPDATE users SET status=? WHERE username=? ";
        var createStmt = db.prepareStatement(createSql);
        createStmt.setString(1, status);
        createStmt.setString(2, username);
        try {
            createStmt.execute();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public ArrayList<User> getActiveUsers(Connection db) throws SQLException {
        var activeUsers = new ArrayList<User>();
        Statement statement = db.createStatement();
        ResultSet set = statement.executeQuery("SELECT * FROM users WHERE status LIKE '" + "activ" + "'");
        while (set.next()) {
            var user = new User(
                    set.getString("full_name"),
                    set.getString("username"),
                    set.getString("email"),
                    set.getString("phone"),
                    set.getString("gender"),
                    set.getString("password")
            );
            activeUsers.add(user);
        }
        return activeUsers;
    }

    public int searchUserByEmail(Connection db, String email) throws SQLException {
        Statement statement = db.createStatement();
        ResultSet set = statement.executeQuery("SELECT * FROM users WHERE email LIKE '" + email + "'");
        if (set.next())
            return -1;
        else return 0;
    }

    public int searchUserByUserName(Connection db, String username) throws SQLException {
        Statement statement = db.createStatement();
        ResultSet set = statement.executeQuery("SELECT * FROM users WHERE username LIKE '" + username + "'");
        if (set.next())
            return -1;
        else return 0;
    }

    public User getUserByUserName(Connection db, String username) throws SQLException {
        Statement statement = db.createStatement();
        ResultSet set = statement.executeQuery("SELECT * FROM users WHERE username LIKE '" + username + "'");
        if (set.next()) {
            return new User(
                    set.getString("full_name"),
                    set.getString("username"),
                    set.getString("email"),
                    set.getString("phone"),
                    set.getString("gender"),
                    set.getString("password")
            );
        }
        return new User(
                null,
                null,
                null,
                null,
                null,
                null
        );
    }

}
