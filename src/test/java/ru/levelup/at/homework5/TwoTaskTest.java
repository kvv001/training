package ru.levelup.at.homework5;

import org.junit.jupiter.api.Test;
import ru.levelup.at.homework5.steps.ActionStep;
import ru.levelup.at.homework5.steps.AssertionStep;

public class TwoTaskTest extends BaseTest {
    private static final String SUBJECT = "Задание 2. Тест";
    private static final String MESSAGE_TEXT = "Тело письма";

    @Test
    void sendNewMessageForTestFolder() {

        // --1. Войти в почту
        ActionStep.loginToMailRuService(LOGIN_NAME, MAIL_PASSWORD);

        // --2. Assert, что вход выполнен успешно
        AssertionStep.authorizedAsGivenUser(LOGIN_NAME);

        final int sentMessagesBefore = ActionStep.getListOfSentLettersCount();
        final int testMessagesBefore = ActionStep.getListOfTestLettersCount();

        // --3. Создать новое письмо (заполнить адресата, тему письма и тело)
        ActionStep.createLetter(LOGIN_NAME, SUBJECT, MESSAGE_TEXT);

        // --4. Отправить письмо
        ActionStep.sendLetter();

        // --5. Verify, что письмо появилось в папке отправленные
        ActionStep.verifyNumberOfSentLettersIncreaseByOne(sentMessagesBefore);

        // --6. Verify, что письмо появилось в папке «Тест»
        ActionStep.verifyNumberOfTestLettersIncreaseByOne(testMessagesBefore);

        // --7. Verify контент, адресата и тему письма (должно совпадать с пунктом 3)
        AssertionStep.checkSentOrReceivedMessageAddresseeSubjectAndText(LOGIN_NAME, SUBJECT, MESSAGE_TEXT);

        // --8. Выйти из учётной записи
        ActionStep.logout();

    }
}
