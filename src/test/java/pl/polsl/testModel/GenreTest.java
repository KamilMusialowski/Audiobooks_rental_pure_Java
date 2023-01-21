/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pl.polsl.testModel;

import java.util.stream.Stream;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import pl.polsl.model.Genre;

/**
 *
 * @author Kamil Musiałowski
 * @version 1.5
 */
public class GenreTest {

    /**
     * Tests if Genre objects names are exact same as its labels.
     *
     * @param genre Genre class object.
     */
    @ParameterizedTest
    @MethodSource("enumArgumentsProvider")
    public void testGetLabel(Genre genre) {
        assertEquals(genre.name(), genre.getLabel(), "Label not as expected!");
    }

    /**
     * testGetLabel arguments provider.
     *
     * @return Stream containing arguments for test.
     */
    static Stream<Genre> enumArgumentsProvider() {
        return Stream.of(
                Genre.CRIME,
                Genre.FANTASY,
                Genre.NOVEL,
                Genre.UNSPECIFIED
        );
    }

}
