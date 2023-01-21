/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pl.polsl.testModel;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.stream.Stream;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import static org.junit.jupiter.params.provider.Arguments.arguments;
import org.junit.jupiter.params.provider.MethodSource;
import pl.polsl.model.*;

/**
 *
 *
 * @author Kamil Musiałowski
 * @version 1.5
 */
public class ModelTest {

    /**
     * Model class object used in tests.
     */
    private Model model;

    /**
     * Initializes model before each test.
     */
    @BeforeEach
    public void setUp() {
        this.model = new Model();
    }

    /**
     * Test cases provider
     *
     * @return Stream of test arguments
     */
    private static Stream<Arguments> selectAudiobooksAllArgs() {
        return Stream.of(
                arguments(1, "Hounds of Baskervilles", "Arthur Conan Doyle")
        );
    }

    /**
     * Select all audiobooks methot test
     *
     * @param id Audiobook ID param
     * @param title Audiobook title param
     * @param author Audiobook author param
     * @throws SQLException SQL exception
     */
    @ParameterizedTest
    @MethodSource("selectAudiobooksAllArgs")
    public void testSelectAudiobooksAll(int id, String title, String author) throws SQLException {
        ResultSet functionRS = model.selectAudiobooksAll();
        int actual = 0;
        if (functionRS.last()) {
            actual = functionRS.getRow();
            functionRS.beforeFirst();
        }
        Statement stmt = model.getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                ResultSet.CONCUR_READ_ONLY);
        ResultSet expected = stmt.executeQuery("SELECT * FROM APP.AUDIOBOOKS");
        int expectedSize = 0;
        if (expected.last()) {
            expectedSize = expected.getRow();
            expected.beforeFirst();
        }
        assertTrue(functionRS.next(), "ResultSet is empty");
        assertEquals(id, functionRS.getInt("AUDIOBOOK_ID"), "ID not as expected");
        assertEquals(title, functionRS.getString("TITLE"), "Title not as expected");
        assertEquals(author, functionRS.getString("AUTHOR"), "Author not as expected");
        assertTrue(actual == expectedSize, "Result size is wrong");
    }

    /**
     * Select all audiobooks test
     *
     * @param connection DB connection
     * @param expectedMessage Expected exception message
     */
    @ParameterizedTest
    @MethodSource("selectAudiobooksAllExceptions")
    public void testSelectAudiobooksAllExceptions(Connection connection, String expectedMessage) {
        model.setConnection(connection);
        Exception exception = assertThrows(SQLException.class, () -> model.selectAudiobooksAll());
        assertEquals(expectedMessage, exception.getMessage());
    }

    /**
     * Test cases provider
     *
     * @return Test arguments
     * @throws SQLException SQL exception
     */
    private static Stream<Arguments> selectAudiobooksAllExceptions() throws SQLException {
        return Stream.of(
                arguments(createClosedConnection(), "Brak bieżącego połączenia.")
        );
    }

    /**
     * Test cases provider
     *
     * @return Test arguments
     * @throws SQLException SQL exception
     */
    private static Connection createClosedConnection() throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:derby://localhost:1527/Audiobooks");
        connection.close();
        return connection;
    }

    //////////////////////////////////////////////////////////////////////////////
    /**
     * Select all users test
     *
     * @param id User ID test param
     * @param name User username test param
     * @param password User password param
     * @throws SQLException SQL exception
     */
    @ParameterizedTest
    @MethodSource("selectUsersAllArgs")
    public void testSelectUsersAll(int id, String name, String password) throws SQLException {
        ResultSet functionRS = model.selectUsersAll();
        int actual = 0;
        if (functionRS.last()) {
            actual = functionRS.getRow();
            functionRS.beforeFirst();
        }
        Statement stmt = model.getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                ResultSet.CONCUR_READ_ONLY);
        ResultSet expected = stmt.executeQuery("SELECT * FROM APP.USERS");
        int expectedSize = 0;
        if (expected.last()) {
            expectedSize = expected.getRow();
            expected.beforeFirst();
        }
        assertTrue(functionRS.next(), "ResultSet is empty.");
        assertEquals(id, functionRS.getInt(1), "User ID is not as expected");
        assertEquals(name, functionRS.getString(2), "User nickname is not as expected");
        assertEquals(password, functionRS.getString(3), "User password is not as expected");
        assertTrue(actual == expectedSize, "Result size is wrong");
    }

    /**
     * Test cases provider
     *
     * @return Test arguments
     */
    private static Stream<Arguments> selectUsersAllArgs() {
        return Stream.of(
                arguments(1, "Standard_User", "Standard_User")
        );
    }

    /**
     * Select all users test
     *
     * @param connection DB connection
     * @param expectedMessage Expected exception message param
     */
    @ParameterizedTest
    @MethodSource("selectUsersAllExceptions")
    public void testSelectUsersAllExceptions(Connection connection, String expectedMessage) {
        model.setConnection(connection);
        Exception exception = assertThrows(SQLException.class, () -> model.selectUsersAll());
        assertEquals(expectedMessage, exception.getMessage());
    }

    /**
     * Test cases provider
     *
     * @return Test arguments
     * @throws SQLException SQL exception
     */
    private static Stream<Arguments> selectUsersAllExceptions() throws SQLException {
        return Stream.of(
                arguments(createClosedConnection(), "Brak bieżącego połączenia.")
        );
    }

    ////////////////////////////////////////////////////////////////////////////
    /**
     * Select all lends test
     *
     * @param id Lend id param
     * @param user User id param
     * @param audiobook Audiobook id param
     * @param startDate Start date
     * @param endDate End date
     * @throws SQLException SQL exception
     */
    @ParameterizedTest
    @MethodSource("selectLendAllArgs")
    public void testSelectLendAll(int id, int user, int audiobook, String startDate, String endDate) throws SQLException {
        ResultSet functionRS = model.selectLendAll();
        int actual = 0;
        if (functionRS.last()) {
            actual = functionRS.getRow();
            functionRS.beforeFirst();
        }
        Statement stmt = model.getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                ResultSet.CONCUR_READ_ONLY);
        ResultSet expected = stmt.executeQuery("SELECT * FROM APP.LEND");
        int expectedSize = 0;
        if (expected.last()) {
            expectedSize = expected.getRow();
            expected.beforeFirst();
        }
        assertTrue(functionRS.next(), "ResultSet is empty.");
        assertEquals(id, functionRS.getInt(1), "Lend ID is not as expected");
        assertEquals(user, functionRS.getInt(2), "User ID is not as expected");
        assertEquals(audiobook, functionRS.getInt(3), "Audiobook ID is not as expected");
        assertEquals(startDate, functionRS.getDate(4).toString(), "Start date is not as expected");
        assertEquals(endDate, functionRS.getDate(5).toString(), "End date is not as expected");
        assertTrue(actual == expectedSize, "Result size is wrong");
    }

    /**
     * Test cases provider
     *
     * @return Test arguments
     */
    private static Stream<Arguments> selectLendAllArgs() {
        return Stream.of(
                arguments(4, 2, 5, "2023-01-03", "2023-02-02")
        );
    }

    /**
     * Select all lends test
     *
     * @param connection DB connection
     * @param expectedMessage Expected exception message
     */
    @ParameterizedTest
    @MethodSource("selectLendAllExceptions")
    public void testSelectLendAllExceptions(Connection connection, String expectedMessage) {
        model.setConnection(connection);
        Exception exception = assertThrows(SQLException.class, () -> model.selectLendAll());
        assertEquals(expectedMessage, exception.getMessage());
    }

    /**
     * Test cases provider
     *
     * @return Test arguments
     * @throws SQLException SQL exception
     */
    private static Stream<Arguments> selectLendAllExceptions() throws SQLException {
        return Stream.of(
                arguments(createClosedConnection(), "Brak bieżącego połączenia.")
        );
    }

    ////////////////////////////////////////////////////////////////////////////
    /**
     * Audiobook insert test
     *
     * @param title Audiobook tile
     * @param author Audiobook author
     * @param genre Audiobook genre
     * @param price Audiobook price
     * @throws SQLException SQL exception
     * @throws OwnException Exception
     */
    @ParameterizedTest
    @MethodSource("insertAudiobookPositiveArgs")
    public void testInsertAudiobook(String title, String author, String genre, Double price) throws SQLException, OwnException {
        assertDoesNotThrow(() -> model.insertAudiobook(title, author, genre, price));
        Statement stmt = model.getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                ResultSet.CONCUR_READ_ONLY);
        stmt.executeUpdate("DELETE FROM APP.AUDIOBOOKS WHERE APP.AUDIOBOOKS.TITLE = 'Title'");

    }

    /**
     * Test cases provider
     *
     * @return Test arguments
     */
    private static Stream<Arguments> insertAudiobookPositiveArgs() {
        return Stream.of(
                arguments("Title", "Author", "Genre", 10.0)
        );
    }

    /**
     * Audiobook insert test
     *
     * @param title Audiobook test
     * @param author Audiobook author
     * @param genre Audiobook genre
     * @param price Audiobook price
     * @param expectedMessage Expected exception message
     */
    @ParameterizedTest
    @MethodSource("insertAudiobookArgs")
    public void testInsertAudiobook(String title, String author, String genre, Double price, String expectedMessage) {
        Exception exception = assertThrows(OwnException.class, () -> model.insertAudiobook(title, author, genre, price));
        assertEquals(expectedMessage, exception.getMessage());
    }

    /**
     * Test cases provider
     *
     * @return Test arguments
     * @throws SQLException SQL exception
     */
    private static Stream<Arguments> insertAudiobookArgs() {
        return Stream.of(
                arguments("", "", "", -1.0, "Price cannot be negative and there must be a title and genre."),
                arguments("", null, "", 1.0, "Price cannot be negative and there must be a title and genre."),
                arguments("", "", null, 1.0, "Price cannot be negative and there must be a title and genre."),
                arguments("Title", "", "", 1.0, "Price cannot be negative and there must be a title and genre."),
                arguments("", "Author", "", 1.0, "Price cannot be negative and there must be a title and genre."),
                arguments("Title", null, "Genre", 1.0, "Price cannot be negative and there must be a title and genre.")
        );
    }

    ////////////////////////////////////////////////////////////////////////////
    /**
     * Lending audiobook test
     *
     * @param userID User id
     * @param audiobookID Audiobook id
     * @throws SQLException SQL exception
     * @throws OwnException Exception
     */
    @ParameterizedTest
    @MethodSource("lendAudiobookPositiveArgs")
    public void testLendAudiobook(String userID, String audiobookID) throws SQLException, OwnException {
        assertDoesNotThrow(() -> model.lendAudiobook(userID, audiobookID));
        Statement stmt = model.getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                ResultSet.CONCUR_READ_ONLY);
        stmt.executeUpdate("DELETE FROM APP.LEND WHERE LEND_ID=(SELECT MAX(LEND_ID) FROM APP.LEND)");
    }

    /**
     * Test cases provider
     *
     * @return Test arguments
     */
    private static Stream<Arguments> lendAudiobookPositiveArgs() {
        return Stream.of(
                arguments("1", "1")
        );
    }

    /**
     * Lending audiobook test
     *
     * @param userID User id
     * @param audiobookID Audiobook id
     * @param expectedMessage Expected exception message
     */
    @ParameterizedTest
    @MethodSource("lendAudiobookArgs")
    public void testLendAudiobook(String userID, String audiobookID, String expectedMessage) {
        Exception exception = assertThrows(OwnException.class, () -> model.lendAudiobook(userID, audiobookID));
        assertEquals(expectedMessage, exception.getMessage());
    }

    /**
     * Test cases provider
     *
     * @return Test arguments
     */
    private static Stream<Arguments> lendAudiobookArgs() {
        return Stream.of(
                arguments("", "", "Empty user and/or audiobook ID field."),
                arguments("", null, "Empty user and/or audiobook ID field.")
        );
    }

    ////////////////////////////////////////////////////////////////////////////
    /**
     * Select audiobook by id test
     *
     * @param audiobookID Audiobook id
     * @throws SQLException SQL esception
     * @throws OwnException Exception
     */
    @ParameterizedTest
    @MethodSource("selectAudiobookByIDPositiveArgs")
    public void testSelectAudiobookByID(String audiobookID) throws SQLException, OwnException {
        ResultSet resultSet = model.selectAudiobookByID(audiobookID);
        assertTrue(resultSet.next());
    }

    /**
     * Test cases provider
     *
     * @return Test arguments
     */
    private static Stream<Arguments> selectAudiobookByIDPositiveArgs() {
        return Stream.of(
                arguments("1"),
                arguments("2"),
                arguments("3"),
                arguments("4")
        );
    }

    /**
     * Select audiobook by id test
     *
     * @param audiobookID Audiobook id
     * @param expectedMessage Expected exception message
     */
    @ParameterizedTest
    @MethodSource("selectAudiobookByIDNegativeArgs")
    public void testSelectAudiobookByID(String audiobookID, String expectedMessage) {
        Exception exception = assertThrows(OwnException.class, () -> model.selectAudiobookByID(audiobookID));
        assertEquals(expectedMessage, exception.getMessage(), "Message not as expected.");
    }

    /**
     * Test cases provider
     *
     * @return Test arguments
     */
    private static Stream<Arguments> selectAudiobookByIDNegativeArgs() {
        return Stream.of(
                arguments("", "Not valid audiobook ID."),
                arguments(null, "Not valid audiobook ID.")
        );
    }

    ////////////////////////////////////////////////////////////////////////////
    /**
     * Select user status by id
     *
     * @param userID User id
     * @throws SQLException SQL exception
     * @throws OwnException Exception
     */
    @ParameterizedTest
    @MethodSource("selectUserStatusByIDPositiveArgs")
    public void testSelectUserStatusByID(String userID) throws SQLException, OwnException {
        String status = Integer.toString(model.selectUserStatusByID(userID));
        assertEquals(status, userID, "Not valid status.");
    }

    /**
     * Test cases provider
     *
     * @return Test arguments
     */
    private static Stream<Arguments> selectUserStatusByIDPositiveArgs() {
        return Stream.of(
                arguments("1"),
                arguments("2"),
                arguments("3")
        );
    }

    /**
     * Select user status by id
     *
     * @param userID User id
     * @param expectedMessage Expected exception message
     */
    @ParameterizedTest
    @MethodSource("selectUserStatusByIDNegativeArgs")
    public void testSelectUserStatusByID(String userID, String expectedMessage) {
        Exception exception = assertThrows(OwnException.class, () -> model.selectAudiobookByID(userID));
        assertEquals(expectedMessage, exception.getMessage(), "Message not as expected.");
    }

    /**
     * Test cases provider
     *
     * @return Test arguments
     */
    private static Stream<Arguments> selectUserStatusByIDNegativeArgs() {
        return Stream.of(
                arguments("", "Not valid audiobook ID."),
                arguments(null, "Not valid audiobook ID.")
        );
    }

    ////////////////////////////////////////////////////////////////////////////
    /**
     * Select user by logging-in data
     *
     * @param username User username
     * @param password User password
     * @throws SQLException SQL exception
     * @throws OwnException Exception
     */
    @ParameterizedTest
    @MethodSource("selectUserByLogInDataPositiveArgs")
    public void testSelectUserByLogInData(String username, String password) throws SQLException, OwnException {
        ResultSet resultSet = model.selectUserByLogInData(username, password);
        assertTrue(resultSet.next());
    }

    /**
     * Test cases provider
     *
     * @return Test arguments
     */
    private static Stream<Arguments> selectUserByLogInDataPositiveArgs() {
        return Stream.of(
                arguments("Admin", "Admin"),
                arguments("Premium", "Premium")
        );
    }

    /**
     * Select user by logging-in data
     *
     * @param username User username
     * @param password User password
     * @throws SQLException SQL exception
     * @throws OwnException Exception
     */
    @ParameterizedTest
    @MethodSource("selectUserByLogInDataUnsuccesfulArgs")
    public void testSelectUserByLogInDataUnseccesful(String username, String password) throws SQLException, OwnException {
        ResultSet resultSet = model.selectUserByLogInData(username, password);
        assertFalse(resultSet.next());
    }

    /**
     * Test cases provider
     *
     * @return Test arguments
     */
    private static Stream<Arguments> selectUserByLogInDataUnsuccesfulArgs() {
        return Stream.of(
                arguments("Admi", "Ain"),
                arguments("Prm", "mium")
        );
    }

    /**
     * Select user by logging-in data
     *
     * @param username User username
     * @param password User password
     * @param expectedMessage Expected exception message
     */
    @ParameterizedTest
    @MethodSource("selectUserByLogInDataNegativeArgs")
    public void testSelectUserByLogInData(String username, String password, String expectedMessage) {
        Exception exception = assertThrows(OwnException.class, () -> model.selectUserByLogInData(username, password));
        assertEquals(expectedMessage, exception.getMessage());
    }

    /**
     * Test cases provider
     *
     * @return Test arguments
     */
    private static Stream<Arguments> selectUserByLogInDataNegativeArgs() {
        return Stream.of(
                arguments(null, "password1", "Not valid log in data."),
                arguments("user1", null, "Not valid log in data.")
        );
    }

    //////////////////////////////////////////////////////////////////////////////
    /**
     * Select audiobooks by genre test
     *
     * @param genre Audiobook genre
     * @throws SQLException SQL exception
     * @throws OwnException Exception
     */
    @ParameterizedTest
    @MethodSource("selectAudiobooksByGenrePositiveArgs")
    public void testSelectAudiobooksByGenre(String genre) throws SQLException, OwnException {
        ResultSet resultSet = model.selectAudiobooksByGenre(genre);
        assertTrue(resultSet.next());
    }

    /**
     * Test cases provider
     *
     * @return Test arguments
     */
    private static Stream<Arguments> selectAudiobooksByGenrePositiveArgs() {
        return Stream.of(
                arguments("Crime"),
                arguments("Fantasy"),
                arguments("Novel"),
                arguments("Unspecified")
        );
    }

    /**
     * Select audiobooks by genre test
     *
     * @param genre Audiobook genre
     * @throws SQLException SQL exception
     * @throws OwnException Exception
     */
    @ParameterizedTest
    @MethodSource("testSelectAudiobookByGenreInvalidGenreArgs")
    public void testSelectAudiobookByGenreInvalidGenre(String genre) throws SQLException, OwnException {
        ResultSet resultSet = model.selectAudiobooksByGenre(genre);
        assertFalse(resultSet.next());
    }

    /**
     * Test cases provider
     *
     * @return Test arguments
     */
    private static Stream<Arguments> testSelectAudiobookByGenreInvalidGenreArgs() {
        return Stream.of(
                arguments("Admi"),
                arguments("Pr")
        );
    }

    /**
     * Select audiobooks by genre test
     *
     * @param genre Audiobook genre
     * @param expectedMessage Expected exception message
     */
    @ParameterizedTest
    @MethodSource("selectAudiobooksByGenreNegativeArgs")
    public void selectAudiobooksByGenreNegative(String genre, String expectedMessage) {
        Exception exception = assertThrows(OwnException.class, () -> model.selectAudiobooksByGenre(genre));
        assertEquals(expectedMessage, exception.getMessage());
    }

    /**
     * Test cases provider
     *
     * @return Test arguments
     */
    private static Stream<Arguments> selectAudiobooksByGenreNegativeArgs() {
        return Stream.of(
                arguments(null, "Null value as genre.")
        );
    }
}
