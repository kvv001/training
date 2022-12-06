package ru.levelup.at.homework2;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class PositiveTestLuckyTicket {

    @Tag("Positive")
    @ParameterizedTest
    @ValueSource(strings = {"111111", "123321"})
    void sumsOfTwoHalvesAreEqual(String input) {
        boolean expected = true;

    }

    @Tag("Positive")
    @ParameterizedTest
    @ValueSource(strings = {"345667", "123456"})
    void sumsOfTwoHalvesAreNotEqual(String input) {
        boolean expected = false;

    }
}
