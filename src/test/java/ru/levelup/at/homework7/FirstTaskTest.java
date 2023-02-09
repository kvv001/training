package ru.levelup.at.homework7;

import org.junit.jupiter.api.Test;
import ru.levelup.at.homework5.steps.ActionStep;
import ru.levelup.at.homework5.steps.AssertionStep;

public class FirstTaskTest extends BaseTest {

    private static final String SUBJECT = "Тема письма";
    private static final String MESSAGE_TEXT = "Тело письма";

    @Test

    void saveAndSendDraftMessage() {

        // 1. Войти в почту
        ActionStep.loginToMailRuService(LOGIN_NAME,MAIL_PASSWORD);

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
}

