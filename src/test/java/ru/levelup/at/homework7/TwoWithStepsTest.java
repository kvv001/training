package ru.levelup.at.homework7;

import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.levelup.at.homework7.steps.ActionStep;
import ru.levelup.at.homework7.steps.AssertionStep;

@DisplayName("Тесты второго упражнения домашней работы №3 по Selenium")
@Feature("Пользовательская папка")
public class TwoWithStepsTest extends BaseTest {

    private static final String SUBJECT = "SecondExercise. Тема письма - Тест";
    private static final String MESSAGE_TEXT = "SecondExercise. Текст для заполнения тела письма.";

    @Test
    @DisplayName("Создание и отправка созданого письма")
    @Story("Создание письма")
    @Story("Отправка созданного письма")
    void sendNewMessageForTestFolder() {

        // 1. Войти в почту
        ActionStep.loginToMailRuService(LOGIN_NAME, MAIL_PASSWORD);

        // 2. Assert, что вход выполнен успешно
        AssertionStep.authorizedAsGivenUser(LOGIN_NAME);

        final int sentMessagesBefore = ActionStep.getListOfSentLettersCount();
        final int testMessagesBefore = ActionStep.getListOfTestLettersCount();

        // 3. Создать новое письмо (заполнить адресата, тему письма и тело)
        ActionStep.createLetter(LOGIN_NAME, SUBJECT, MESSAGE_TEXT);

        // 4. Отправить письмо
        ActionStep.sendLetter();

        // 5. Verify, что письмо появилось в папке отправленные
        ActionStep.verifyNumberOfSentLettersIncreaseByOne(sentMessagesBefore);

        // 6. Verify, что письмо появилось в папке «Тест»
        ActionStep.verifyNumberOfTestLettersIncreaseByOne(testMessagesBefore);

        // 7. Verify контент, адресата и тему письма (должно совпадать с пунктом 3)
        AssertionStep.checkSentOrReceivedMessageAddresseeSubjectAndText(LOGIN_NAME, SUBJECT, MESSAGE_TEXT);

        // 8. Выйти из учётной записи
        ActionStep.logout();
    }
}
