package ru.levelup.at.homework7;

import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.levelup.at.homework7.steps.ActionStep;
import ru.levelup.at.homework7.steps.AssertionStep;

@DisplayName("Тесты работы №3 по Selenium")
@Feature("Входящие письма")
public class ThreeWithStepsTest extends BaseTest {

    private static final String SUBJECT = "ThirdExercise. Тема письма";
    private static final String MESSAGE_TEXT = "ThirdExercise. Текст для заполнения тела письма.";

    @Test
    @DisplayName("Создание и отправка созданого письма")
    @Story("Создание письма")
    @Story("Отправка созданного письма")
    @Story("Удаление письма")
    void sendNewMessageForInboxFolderAndDeleteMessage() {

        // 1. Войти в почту
        ActionStep.loginToMailRuService(LOGIN_NAME, MAIL_PASSWORD);

        // 2. Assert, что вход выполнен успешно
        AssertionStep.authorizedAsGivenUser(LOGIN_NAME);

        final int receivedMessagesBefore = ActionStep.getListOfReceivedLettersCount();
        final int deletedMessagesBefore = ActionStep.getListOfDeletedLettersCount();

        // 3. Создать новое письмо (заполнить адресата (самого себя), тему письма и тело)
        ActionStep.createLetter(LOGIN_NAME, SUBJECT, MESSAGE_TEXT);

        // 4. Отправить письмо
        ActionStep.sendLetter();

        // 5. Verify, что письмо появилось в папке Входящие
        ActionStep.verifyNumberOfReceivedLettersIncreaseByOne(receivedMessagesBefore);

        // 6. Verify контент, адресата и тему письма (должно совпадать с пунктом 3)
        AssertionStep.checkSentOrReceivedMessageAddresseeSubjectAndText(LOGIN_NAME, SUBJECT, MESSAGE_TEXT);

        // 7. Удалить письмо
        ActionStep.deleteLetter();

        // 8. Verify что письмо появилось в папке Корзина
        ActionStep.verifyNumberOfDeletedLettersIncreaseByOne(deletedMessagesBefore);

        // 9. Выйти из учётной записи
        ActionStep.logout();
    }
}
