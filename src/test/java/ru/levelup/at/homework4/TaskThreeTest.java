package ru.levelup.at.homework4;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.Duration;
import homework4.LoginRegistrationPage;
import homework4.MainPage;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class TaskThreeTest extends BaseTest {
    private static final String SUBJECT = "Задание 3";
    private static final String MESSAGE_TEXT = "Задание 3";

    @Test
    void sendMessageInboxFolderAndDelMessage() {

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
        Assertions.assertThat(mainPage.getActualUsername()).isEqualTo(LOGIN_NAME);

        mainPage.clickLeftMenuInboxButton();
        final int receivedMessagesBefore = mainPage.getListOfLettersCount();

        mainPage.clickLeftMenuTrashButton();
        final int deletedMessagesBefore = mainPage.getListOfLettersCount();

        // --3. Создать новое письмо (заполнить адресата (самого себя), тему письма и тело)
        mainPage.clickComposeLetterButton();

        mainPage.enterAddresseeToAddresseeField(LOGIN_NAME);
        mainPage.enterSubjectToSubjectField(SUBJECT);
        mainPage.enterMessageTextToMessageTextField(MESSAGE_TEXT);

        // --4. Отправить письмо
        mainPage.clickSendButton();
        mainPage.clickLetterSentCloseButton();

        // --5. Verify, что письмо появилось в папке Входящие
        mainPage.clickLeftMenuInboxButtonAndWaitForNumberOfLettersIncreaseByOne(receivedMessagesBefore);

        // --6. Verify контент, адресата и тему письма (должно совпадать с пунктом 3)
        mainPage.clickOnFirstLetterInListOfLetters();

        Assertions.assertThat(mainPage.getActualAddressee()).isEqualTo(LOGIN_NAME);
        Assertions.assertThat(mainPage.getActualSubject()).isEqualTo(SUBJECT);
        Assertions.assertThat(mainPage.getActualMessageText()).isEqualTo(MESSAGE_TEXT);

        // --7. Удалить письмо
        mainPage.clickDeleteButton();

        // --8. Verify. что письмо появилось в папке Корзина
        mainPage.clickLeftMenuTrashButtonAndWaitForNumberOfLettersIncreaseByOne(deletedMessagesBefore);

        // --9. Выйти из учётной записи
        mainPage.clickRightMenuButton();
        mainPage.clickLogoutButton();
    }
}
