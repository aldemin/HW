package com.geekbrains;

import java.sql.*;

public class DB {
    private Connection connection = null;
    private Statement statement = null;
    private PreparedStatement preparedStatement = null;
    private ResultSet res = null;

    public void connect() {
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:Goods.s3db");
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void disconnect() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void createTable() {
        try {
            statement = connection.createStatement();
            statement.addBatch("CREATE TABLE IF NOT EXISTS 'goods' " +
                    "('id' INTEGER PRIMARY KEY AUTOINCREMENT," +
                    " 'prodid' INTEGER, " +
                    "'title' TEXT," +
                    " 'cost' INTEGER);");
            statement.addBatch("DELETE FROM goods");
            statement.executeBatch();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void fillTable() {
        try {
            connection.setAutoCommit(false);
            preparedStatement = connection.prepareStatement(
                    "INSERT INTO 'goods' ('prodid', 'title', 'cost') VALUES (?, ?, ?);");
            for (int i = 1; i <= 10000; i++) {
                preparedStatement.setInt(1, i);
                preparedStatement.setString(2, "Prod" + i);
                preparedStatement.setInt(3, i + 6 * 2);
                preparedStatement.execute();
            }
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String readPosByTitle(String title) {
        StringBuilder result = new StringBuilder();
        try {
            preparedStatement = connection.prepareStatement(
                    "SELECT * FROM goods WHERE title = ?");
            preparedStatement.setString(1, title);
            res = preparedStatement.executeQuery();

            while (res.next()) {
                result.append(res.getString("title"));
                result.append(" costs ");
                result.append(res.getInt("cost"));
                result.append("\n");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result.toString();
    }

    public String readPosByCost(int firstCost, int secondCost) {
        StringBuilder result = new StringBuilder();
        try {
            preparedStatement = connection.prepareStatement(
                    "SELECT * FROM goods WHERE cost > ? AND cost < ?");
            preparedStatement.setInt(1, firstCost);
            preparedStatement.setInt(2, secondCost);
            res = preparedStatement.executeQuery();

            while (res.next()) {
                result.append(res.getString("title"));
                result.append(" costs ");
                result.append(res.getInt("cost"));
                result.append("\n");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result.toString();
    }

    public void writeNewCost(String title, int newCost) {
        try {
            preparedStatement = connection.prepareStatement(
                    "UPDATE goods SET cost = ? WHERE title = ?");
            preparedStatement.setInt(1, newCost);
            preparedStatement.setString(2, title);
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
