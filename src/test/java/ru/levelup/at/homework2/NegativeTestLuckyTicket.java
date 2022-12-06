package ru.levelup.at.homework2;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class NegativeTestLuckyTicket {

    @Tag("Negative")
    @ParameterizedTest
    @ValueSource(strings = {"345667", "123456"})
    void sumsOfTwoHalvesAreEqual(String input) {
        boolean expected = true;

    }

    @Tag("Negative")
    @ParameterizedTest
    @ValueSource(strings = {"111111", "222222"})
    void sumsOfTwoHalvesAreNotEqual(String input) {
        boolean expected = false;

    }
}
