/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pl.polsl.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;

/**
 * First class of "model" package.
 *
 * @author Kamil Musialowski
 * @version 1.5
 */
public class Model {

    private Connection connection;

    /**
     * Constructor of Model class.
     */
    public Model() {
        try {
            Class.forName("org.apache.derby.jdbc.ClientDriver");
            this.connection = DriverManager.getConnection("jdbc:derby://localhost:1527/Audiobooks");
            Statement sta = this.connection.createStatement();
            sta.executeUpdate("CREATE TABLE AUDIOBOOKS(AUDIOBOOK_ID INT NOT NULL GENERATED ALWAYS AS IDENTITY, AUTHOR VARCHAR(50),"
                    + " TITLE VARCHAR(50) NOT NULL, GENRE VARCHAR(20) NOT NULL, PRICE DOUBLE NOT NULL, PRIMARY KEY(AUDIOBOOK_ID))");
            sta.executeUpdate("CREATE TABLE USERS(USER_ID INT NOT NULL GENERATED ALWAYS AS IDENTITY, NICKNAME VARCHAR(30) NOT NULL, "
                    + "PASSWORD VARCHAR(30) NOT NULL, STATUS INTEGER NOT NULL, PRIMARY KEY(USER_ID))");
            sta.executeUpdate("CREATE TABLE LEND(LEND_ID INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY, USER_ID INTEGER NOT NULL, "
                    + "AUDIOBOOK_ID INTEGER NOT NULL, START_DATE DATE NOT NULL, END_DATE DATE NOT NULL, PRIMARY KEY(LEND_ID), "
                    + "FOREIGN KEY(USER_ID) REFERENCES APP.USERS(USERS_ID), "
                    + "FOREIGN KEY(AUDIOBOOK_ID) REFERENCES APP.AUDIOBOOKS(AUDIOBOOK_ID))");
        } catch (ClassNotFoundException | SQLException sqle) {
            System.err.println(sqle.getMessage());
        }
    }

    /**
     * DB connection getter.
     *
     * @return DB connection.
     */
    public Connection getConnection() {
        return this.connection;
    }

    /**
     * DB connection setter.
     *
     * @param connection DB connection to be set.
     */
    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    /**
     * Selects all audiobooks data from DB
     *
     * @return Selected data set.
     * @throws SQLException SQL exception
     */
    public ResultSet selectAudiobooksAll() throws SQLException {
        Statement stmt = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                ResultSet.CONCUR_READ_ONLY);
        ResultSet rs = stmt.executeQuery("SELECT * FROM APP.AUDIOBOOKS");
        return rs;
    }

    /**
     * Selects all users data from DB
     *
     * @return Selected data set.
     * @throws SQLException SQL exception
     */
    public ResultSet selectUsersAll() throws SQLException {
        Statement stmt = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                ResultSet.CONCUR_READ_ONLY);
        ResultSet rs = stmt.executeQuery("SELECT * FROM APP.USERS");
        return rs;
    }

    /**
     * Selects all lends data from DB
     *
     * @return Selected data set.
     * @throws SQLException SQL exception
     */
    public ResultSet selectLendAll() throws SQLException {
        Statement stmt = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                ResultSet.CONCUR_READ_ONLY);
        ResultSet rs = stmt.executeQuery("SELECT * FROM APP.LEND");
        return rs;
    }
/**
 * Inserts new audiobook into DB
 * @param title Audiobooks title
 * @param author Audiobooks author
 * @param genre Audiobooks genre
 * @param price Audiobooks price
 * @throws SQLException SQL exception
 * @throws OwnException Invalid data exception
 */
    public void insertAudiobook(String title, String author, String genre, Double price) throws SQLException, OwnException {
        if (price < 0 || title.equals("") || genre.equals("") || author == null || title == null) {
            throw new OwnException("Price cannot be negative and there must be a title and genre.");
        }
        Statement stmt = connection.createStatement();
        String sqlQuery = "INSERT INTO APP.AUDIOBOOKS (APP.AUDIOBOOKS.TITLE, APP.AUDIOBOOKS.AUTHOR, APP.AUDIOBOOKS.GENRE, APP.AUDIOBOOKS.PRICE) "
                + " VALUES ('" + title + "', '" + author + "', '" + genre + "', " + price + ")";
        stmt.executeUpdate(sqlQuery);
        stmt.close();
    }
/**
 * Inserts new lend into DB
 * @param userID User ID
 * @param audiobookID Audiobook ID
 * @throws SQLException SQL exception
 * @throws OwnException No userID and/or audiobookID exception
 */
    public void lendAudiobook(String userID, String audiobookID) throws SQLException, OwnException {
        if (userID.equals("") || audiobookID.equals("")) {
            throw new OwnException("Empty user and/or audiobook ID field.");
        }
        Statement stmt = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                ResultSet.CONCUR_READ_ONLY);
        String endDate = LocalDate.now().plusDays(30).toString();
        stmt.executeUpdate("INSERT INTO APP.LEND" + "(USER_ID, AUDIOBOOK_ID, START_DATE, END_DATE) VALUES"
                + "(" + userID + ", " + audiobookID + ", CURRENT DATE, '" + endDate + "')");
    }
/**
 * Selects audiobook by ID
 * @param audiobookID Audiobook IS
 * @return Selected data set
 * @throws SQLException SQL exception
 * @throws OwnException Invalid audiobookID exception
 */
    public ResultSet selectAudiobookByID(String audiobookID) throws SQLException, OwnException {
        if (audiobookID == "" || audiobookID == null) {
            throw new OwnException("Not valid audiobook ID.");
        }
        Statement stmt = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                ResultSet.CONCUR_READ_ONLY);
        ResultSet rs = stmt.executeQuery("SELECT * FROM APP.AUDIOBOOKS WHERE AUDIOBOOK_ID=" + audiobookID);
        return rs;
    }
/**
 * Selects user status by ID
 * @param userID User id
 * @return Users status
 * @throws SQLException SQL exception
 * @throws OwnException Invalid userID exception
 */
    public int selectUserStatusByID(String userID) throws SQLException, OwnException {
        if (userID == "" || userID == null) {
            throw new OwnException("Not valid user ID.");
        }
        Statement stmt = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                ResultSet.CONCUR_READ_ONLY);
        ResultSet rs = stmt.executeQuery("SELECT STATUS FROM APP.USERS WHERE USER_ID=" + userID);
        rs.next();
        int status = rs.getInt("STATUS");
        return status;
    }
/**
 * Selects user by logging-in data
 * @param username User username
 * @param password User password
 * @return Selected data set
 * @throws SQLException SQL exception
 * @throws OwnException Null username and/or password exception
 */
    public ResultSet selectUserByLogInData(String username, String password) throws SQLException, OwnException {
        if (username == null || password == null) {
            throw new OwnException("Not valid log in data.");
        }
        Statement stmt = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                ResultSet.CONCUR_READ_ONLY);
        ResultSet rs = stmt.executeQuery("SELECT * FROM APP.USERS WHERE APP.USERS.NICKNAME='" + username + "' "
                + "AND APP.USERS.PASSWORD='" + password + "'");
        return rs;
    }
/**
 * Selects audiobooks by genre
 * @param genre Audiobooks genre
 * @return Selected data set
 * @throws SQLException SQL exception
 * @throws OwnException Null genre exception
 */
    public ResultSet selectAudiobooksByGenre(String genre) throws SQLException, OwnException {
        if (genre == null) {
            throw new OwnException("Null value as genre.");
        }
        Statement stmt = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                ResultSet.CONCUR_READ_ONLY);
        ResultSet rs = stmt.executeQuery("SELECT * FROM APP.AUDIOBOOKS WHERE GENRE='" + genre + "'");
        return rs;
    }
}
