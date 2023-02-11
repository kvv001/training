package ru.levelup.at.homework7;

import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.levelup.at.homework7.steps.ActionStep;
import ru.levelup.at.homework7.steps.AssertionStep;

@DisplayName("Тесты работы №3 по Selenium")
@Feature("Черновики")
public class FirstWithStepsTest extends BaseTest {

    private static final String SUBJECT = "FirstExercise. Тема письма";
    private static final String MESSAGE_TEXT = "FirstExercise. Текст для заполнения тела письма.";

    @Test
    @DisplayName("Сохранение сообщения в черновиках и отправка сообщения из черновиков")
    @Story("Создание письма")
    @Story("Сохранение черновика письма")
    @Story("Отправка черновика письма")
    void saveAndSendDraftMessage() {

        // 1. Войти в почту
        ActionStep.loginToMailRuService(LOGIN_NAME, MAIL_PASSWORD);

        // 2. Assert, что вход выполнен успешно
        AssertionStep.authorizedAsGivenUser(LOGIN_NAME);

        // 3. Создать новое письмо (заполнить адресата, тему письма и тело)
        ActionStep.createLetter(LOGIN_NAME, SUBJECT, MESSAGE_TEXT);

        // 4. Сохранить его как черновик
        actionStep.saveLetter();

        // 5. Verify, что письмо сохранено в черновиках
        final int sentMessagesBefore = ActionStep.getListOfSentLettersCount();
        final int draftMessagesBefore = actionStep.getListOfDraftLettersCount();

        // 6. Verify контент, адресата и тему письма (должно совпадать с пунктом 3)
        assertionStep.checkDraftMessageAddresseeSubjectAndText(LOGIN_NAME, SUBJECT, MESSAGE_TEXT);

        // 7. Отправить письмо
        ActionStep.sendLetter();

        // 8. Verify, что письмо исчезло из черновиков
        actionStep.verifyNumberOfDraftLettersDecreaseByOne(draftMessagesBefore);

        // 9. Verify, что письмо появилось в папке отправленные
        ActionStep.verifyNumberOfSentLettersIncreaseByOne(sentMessagesBefore);

        // 10. Выйти из учётной записи
        ActionStep.logout();
    }

    @Test
    @DisplayName("Сохранение сообщения в черновиках и отправка сообщения из черновиков с последующим падением теста")
    @Story("Создание письма")
    @Story("Сохранение черновика письма")
    @Story("Отправка черновика письма")
    void saveAndSendDraftMessageAndFail() {

        // 1. Войти в почту
        ActionStep.loginToMailRuService(LOGIN_NAME, MAIL_PASSWORD);

        // 2. Assert, что вход выполнен успешно
        AssertionStep.authorizedAsGivenUser(LOGIN_NAME);

        // 3. Создать новое письмо (заполнить адресата, тему письма и тело)
        ActionStep.createLetter(LOGIN_NAME, SUBJECT, MESSAGE_TEXT);

        // 4. Сохранить его как черновик
        actionStep.saveLetter();

        // 5. Verify, что письмо сохранено в черновиках
        final int sentMessagesBefore = ActionStep.getListOfSentLettersCount();
        final int draftMessagesBefore = actionStep.getListOfDraftLettersCount();

        // 6. Verify контент, адресата и тему письма (должно совпадать с пунктом 3)
        assertionStep.checkDraftMessageAddresseeSubjectAndText(LOGIN_NAME, SUBJECT, MESSAGE_TEXT);

        // 7. Отправить письмо
        ActionStep.sendLetter();

        // 8. Verify, что письмо исчезло из черновиков
        actionStep.verifyNumberOfDraftLettersDecreaseByOne(draftMessagesBefore);

        // 9. Verify, что письмо появилось в папке отправленные
        ActionStep.verifyNumberOfSentLettersIncreaseByOne(sentMessagesBefore);

        // 10. Выйти из учётной записи
        ActionStep.logout();

        // Шаг добавлен для того, чтобы тест упал
        ActionStep.createLetter(LOGIN_NAME, SUBJECT, MESSAGE_TEXT);
    }
}
