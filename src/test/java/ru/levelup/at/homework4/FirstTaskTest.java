package ru.levelup.at.homework4;

import static org.assertj.core.api.Assertions.assertThat;

import com.google.common.base.Verify;
import java.time.Duration;
import java.util.List;
import homework4.LoginRegistrationPage;
import homework4.MainPage;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class FirstTaskTest extends BaseTest {

    private static final String SUBJECT = "FirstExercise. Тема письма";
    private static final String MESSAGE_TEXT = "FirstExercise. Текст для заполнения тела письма.";


    @Test
    void saveAndSendDraftMessage() {
        // --1. Войти в почту
        LoginRegistrationPage loginRegistrationPage = new LoginRegistrationPage(driver);

        loginRegistrationPage.open();
        loginRegistrationPage.clickOnLoginButton();

        loginRegistrationPage.enterUsernameToUsernameField(LOGIN_NAME);
        loginRegistrationPage.sendEnterKeyToUsernameField();

        loginRegistrationPage.enterPasswordToPasswordField(MAIL_PASSWORD);
        loginRegistrationPage.sendEnterKeyToPasswordField();

        MainPage mainPage = PageFactory.initElements(driver, MainPage.class);

        mainPage.closePromoPopup();

        // --2. Assert, что вход выполнен успешно
        assertThat(mainPage.getActualUsername()).isEqualTo(LOGIN_NAME);

        // --3. Создать новое письмо (заполнить адресата, тему письма и тело)
        mainPage.clickComposeLetterButton();

        mainPage.enterAddresseeToAddresseeField(LOGIN_NAME);
        mainPage.enterSubjectToSubjectField(SUBJECT);
        mainPage.enterMessageTextToMessageTextField(MESSAGE_TEXT);

        // --4. Сохранить как черновик
        mainPage.clickSaveButton();
        mainPage.clickComposeLetterCloseButton();

        // --5. Verify, что письмо сохранено в черновиках
        mainPage.clickLeftMenuSentButton();
        final int sentMessagesBefore = mainPage.getListOfLettersCount();

        mainPage.clickLeftMenuDraftsButton();
        final int draftMessagesBefore = mainPage.getListOfLettersCount();

        // --6. Verify контент, адресата и тему письма (должно совпадать с пунктом 3)
        mainPage.clickOnFirstLetterInListOfLetters();

        assertThat(mainPage.getDraftActualAddressee()).isEqualTo(LOGIN_NAME);
        assertThat(mainPage.getDraftActualSubject()).isEqualTo(SUBJECT);
        assertThat(mainPage.getActualMessageText()).isEqualTo(MESSAGE_TEXT);

        // --7. Отправить письмо
        mainPage.clickSendButton();
        mainPage.clickLetterSentCloseButton();

        // --8. Verify, что письмо исчезло из черновиков
        mainPage.clickLeftMenuDraftsButtonAndWaitForNumberOfLettersDecreaseByOne(draftMessagesBefore);

        // --9. Verify, что письмо появилось в папке отправленные
        mainPage.clickLeftMenuSentButtonAndWaitForNumberOfLettersIncreaseByOne(sentMessagesBefore);

        // --10. Выйти из учётной записи
        mainPage.clickRightMenuButton();
        mainPage.clickLogoutButton();
    }

 }

