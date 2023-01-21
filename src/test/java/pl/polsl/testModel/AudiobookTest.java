/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pl.polsl.testModel;

import java.util.stream.Stream;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import static org.junit.jupiter.params.provider.Arguments.arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import pl.polsl.model.Audiobook;
import pl.polsl.model.Genre;

/**
 *
 * @author Kamil Musiałowski
 * @version 1.5
 */
public class AudiobookTest {

    /**
     * Audiobook class object used in tests.
     */
    private static Audiobook audiobook;

    /**
     * Before each test audiobook field is initialized with default object.
     */
    @BeforeEach
    public void setUp() {
        audiobook = new Audiobook("Author", "Title", Genre.NOVEL, 30);
    }

    /**
     * Tests if equals method returns false for null object.
     *
     * @param o Object used during test.
     */
    @ParameterizedTest
    @NullSource
    public void testEqualsNull(Object o) {
        assertFalse(audiobook.equals(o));
    }

    /**
     * Tests if equals method returns true for positive case.
     *
     * @param o Object used during test.
     */
    @ParameterizedTest
    @MethodSource
    public void testEqualsPositiveCase(Object o) {
        assertTrue(audiobook.equals(o));
    }

    /**
     * Returns stream containing positive case for equals method test.
     *
     * @return Stream containing arguments for test.
     */
    static Stream<Arguments> testEqualsPositiveCase() {
        return Stream.of(
                arguments(audiobook)
        );
    }

    /**
     * Tests if equals method returns false for negative cases.
     *
     * @param o Object used during test.
     */
    @ParameterizedTest
    @MethodSource
    public void testEqualsNegativeCase(Object o) {
        assertFalse(audiobook.equals(o));
    }

    /**
     * Returns stream containing positive case for equals method test.
     *
     * @return Stream containing arguments for test.
     */
    static Stream<Arguments> testEqualsNegativeCase() {
        Audiobook differentAuthor = new Audiobook("AuthoDDr", "Title", Genre.NOVEL, 30);
        Audiobook differentTitle = new Audiobook("Author", "TitleDD", Genre.NOVEL, 30);
        Audiobook differentGenre = new Audiobook("Author", "Title", Genre.CRIME, 30);
        Audiobook differentPrice = new Audiobook("Author", "Title", Genre.NOVEL, 80);
        return Stream.of(
                arguments(differentAuthor),
                arguments(differentTitle),
                arguments(differentGenre),
                arguments(differentPrice)
        );
    }

    /**
     * Tests if toString method returns good format of string representing
     * Audiobook object.
     *
     * @param string String representing Audiobook object.
     */
    @ParameterizedTest
    @ValueSource(strings = {"Author Title ; NOVEL ; 30.0"})
    public void testToString(String string) {
        assertEquals(string, audiobook.toString(), "Returned string is not as expected!");
    }
}
