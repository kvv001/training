package ru.levelup.at.homework3;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

import static org.assertj.core.api.Assertions.assertThat;

public class TaskThreeTest extends BaseTest {
    @Test
    void SendingLetter() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofMillis(4000));
        driver.navigate().to("https://mail.ru/");
        var actualTitle = driver.getTitle();

        // --1. Войти в почту
        assertThat(actualTitle).isEqualTo("Mail.ru: почта, поиск в интернете, новости, игры");

        driver.findElement(By.cssSelector("[data-testid='enter-mail-primary']")).click();

        WebElement frame = driver.findElement(By.cssSelector("iframe.ag-popup__frame__layout__iframe"));
        WebDriver frameDriver = driver.switchTo().frame(frame);
        frameDriver.findElement(By.cssSelector("[name='username']")).sendKeys(LOGIN_NAME);
        frameDriver.findElement(By.cssSelector("[name='username']")).sendKeys(Keys.ENTER);
        frameDriver.findElement(By.cssSelector("[name='password']")).sendKeys(MAIL_PASSWORD);
        frameDriver.findElement(By.cssSelector("[name='password']")).sendKeys(Keys.ENTER);

        //--2.Assert
        driver.navigate().refresh();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.ph-project__account")));
        assertThat("kvv_test@mail.ru").isEqualTo(
            driver.findElement(By.cssSelector("div.ph-project__account")).getAttribute("aria-label"));

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".ph-project-promo-close-icon")))
            .click();

        //--3.Создать новое письмо (заполнить адресата, тему письма и тело)
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@title='Написать письмо']"))).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[type='text']"))).sendKeys(LOGIN_NAME);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input[name='Subject']")))
            .sendKeys("задание3");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div[role='textbox']")))
            .sendKeys("Тело письма");

        //--4.ОТправить птсьмо
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[data-test-id='send']"))).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".button2_close"))).click();

        //--5. Verify, что письмо появилось в папке входящие

        driver.findElement(By.cssSelector("[href='/inbox/?']")).click();
        wait.until(ExpectedConditions.titleIs("Входящие - Почта Mail.ru"));
        int receivedMessagesBefore = 0;
        wait.until(ExpectedConditions.numberOfElementsToBe(By.cssSelector("a.js-letter-list-item"),
            receivedMessagesBefore + 1));

        // --6. Verify контент, адресата и тему письма (должно совпадать с пунктом 3)
        wait.until(ExpectedConditions.elementToBeClickable(By
            .cssSelector("a.js-letter-list-item" + ":nth-of-type(1)"))).click();

        var actualAddressee = wait.until(ExpectedConditions.visibilityOfElementLocated(By
            .cssSelector("a.js-letter-list-item > .letter-contact"))).getAttribute("title");
        Assertions.assertThat(actualAddressee).isEqualTo(LOGIN_NAME);

        var actualSubject = wait.until(ExpectedConditions.visibilityOfElementLocated(By
            .cssSelector("h2.thread-subject"))).getText();
        Assertions.assertThat(actualSubject).isEqualTo("задание3");

        var actualMessageText = wait.until(ExpectedConditions.visibilityOfElementLocated(By
            .cssSelector("div.js-readmsg-msg > div > div > div > :first-child"))).getText();
        Assertions.assertThat(actualMessageText).isEqualTo("Тело письма");

        // --7. Удалить письмо

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("span[class*='button2_delete']"))).click();

        // --8. Verify что письмо появилось в папке Корзина
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[href='/trash/?']"))).click();
        wait.until(ExpectedConditions.titleContains("Корзина - Почта Mail.ru"));
        int deletedMessagesBefore=0;
        wait.until(ExpectedConditions.numberOfElementsToBe(By
            .cssSelector("a.js-letter-list-item"), deletedMessagesBefore + 1));

        // --9. Выйти из учётной записи
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("img.ph-avatar-img"))).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By
            .cssSelector("[data-testid='whiteline-account-exit']"))).click();




    }
}
