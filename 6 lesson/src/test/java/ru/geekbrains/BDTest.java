package ru.geekbrains;

import org.junit.*;
import static org.junit.Assert.*;

import java.sql.*;

public class BDTest {
    private static class BD {
        private static Connection connection = null;
        private static Statement statement = null;
        private static PreparedStatement preparedStatement = null;

        public static void connect() {
            try {
                Class.forName("org.sqlite.JDBC");
                connection = DriverManager.getConnection("jdbc:sqlite:Students.s3db");
                connection.setAutoCommit(false);
            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

        public static void disconnect() {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        public static void createTable() {
            try {
                statement = connection.createStatement();
                statement.addBatch("CREATE TABLE IF NOT EXISTS 'students' " +
                        "('id' INTEGER PRIMARY KEY AUTOINCREMENT," +
                        " 'surname' TEXT, " +
                        "'score' INTEGER);");
                statement.addBatch("DELETE FROM students");
                statement.executeBatch();
                connection.commit();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        public static void fillTable() {
            try {
                preparedStatement = connection.prepareStatement(
                        "INSERT INTO 'students' ('surname', 'score') VALUES (?, ?);");
                for (int i = 1; i <= 100; i++) {
                    preparedStatement.setString(1, "Student №" + i);
                    preparedStatement.setInt(2, i);
                    preparedStatement.addBatch();
                }
                preparedStatement.executeBatch();
                connection.commit();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private Connection connection;
    private Statement statement;
    private ResultSet resultSet;

    @BeforeClass
    public static void setUp() {
        BD.connect();
        BD.createTable();
        BD.fillTable();
    }

    @AfterClass
    public static void tearDown() {
        BD.disconnect();
    }

    @Before
    public void init() {
        this.connection = BD.connection;
        this.statement = BD.statement;
        this.resultSet = null;
    }

    @Test
    public void insertTest() {
        String expectedAnswer = "666 Alexander";
        String actualAnswer = null;

        try {
            String queryInsert = "INSERT INTO students ('surname','score') VALUES ('Alexander', 666)";
            String querySelect = "SELECT * FROM students WHERE surname = 'Alexander'";
            this.statement = this.connection.createStatement();
            this.statement.execute(queryInsert);
            this.resultSet = this.statement.executeQuery(querySelect);
            if (resultSet.next()) {
                actualAnswer = this.resultSet.getInt("score") + " " + this.resultSet.getString("surname");
            }
            this.connection.rollback();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        assertEquals(expectedAnswer, actualAnswer);
    }

    @Test
    public void updateTest() {
        String expectedAnswer = "35 Alexander";
        String actualAnswer = null;

        try {
            String querySelect = "SELECT * FROM students WHERE surname = 'Alexander'";
            String queryUpdate = "UPDATE students SET surname = 'Alexander' WHERE surname = 'Student №35'";
            this.statement = this.connection.createStatement();
            this.statement.execute(queryUpdate);
            this.resultSet = this.statement.executeQuery(querySelect);
            if (resultSet.next()) {
                actualAnswer = this.resultSet.getInt("score") + " " + this.resultSet.getString("surname");
            }
            this.connection.rollback();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        assertEquals(expectedAnswer, actualAnswer);
    }

    @Test
    public void selectTest() {
        String expectedAnswer = "35 Student №35";
        String actualAnswer = null;

        try {
            String query = "SELECT * FROM students WHERE surname = 'Student №35'";
            this.statement = this.connection.createStatement();
            this.resultSet = this.statement.executeQuery(query);
            if (resultSet.next()) {
                actualAnswer = this.resultSet.getInt("score") + " " + this.resultSet.getString("surname");
            }
            this.connection.rollback();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        assertEquals(expectedAnswer, actualAnswer);
    }
}
