package ru.levelup.at.homework2;


import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class NegativeTest {

    @Tag("Negative")
    @ParameterizedTest
    @ValueSource(ints = { 578, 9875 })
    void billetIsBad(int input) {
        assertThatIllegalArgumentException().isThrownBy(() -> LuckyTicket.ticket(input));
    }
}

