package ru.levelup.at.homework2;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import static org.assertj.core.api.Assertions.assertThat;

public class PositiveTest {


        @Tag("Positive")
        @ParameterizedTest
        @ValueSource(ints = { 123321, 223322 })
        void sumGood(int input) {
            boolean expected = true;
            boolean actual = LuckyTicket.ticket(input);
            assertThat(actual).isEqualTo(expected);
        }

        @Tag("Positive")
        @ParameterizedTest
        @ValueSource(ints = { 123456, 567321 })
        void sumIsNotGood(int input) {
            boolean expected = false;
            boolean actual = LuckyTicket.ticket(input);
            assertThat(actual).isEqualTo(expected);
        }
}
