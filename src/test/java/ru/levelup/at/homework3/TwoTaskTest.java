package ru.levelup.at.homework3;

import com.google.common.base.Verify;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class TwoTaskTest extends BaseTest {
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

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".ph-project-promo-close-icon"))).click();

        //--3.Создать новое письмо (заполнить адресата, тему письма и тело)
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@title='Написать письмо']"))).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[type='text']"))).sendKeys(LOGIN_NAME);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input[name='Subject']"))).sendKeys("Тест");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div[role='textbox']"))).sendKeys("Тест");

        //--4.ОТправить птсьмо
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[data-test-id='send']"))).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".button2_close"))).click();

        //--5. Verify, что письмо появилось в папке отправленные

        driver.findElement(By.cssSelector("a[data-folder-link-id='500000']")).click();
        wait.until(ExpectedConditions.titleIs("Отправленные - Почта Mail.ru"));
        Integer sentMessagesBefore= 0;
        wait.until(ExpectedConditions.numberOfElementsToBe(By.cssSelector("a.js-letter-list-item"), sentMessagesBefore + 1));


        // 6. Verify, что письмо появилось в папке «Тест»
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("a[data-folder-link-id='1']"))).click();
        wait.until(ExpectedConditions.titleContains("Тест - Почта Mail.ru"));
        Integer testMessagesBefore= 0;
        wait.until(ExpectedConditions.numberOfElementsToBe(By.cssSelector("a.js-letter-list-item"), testMessagesBefore + 1));

        //--7. Verify контент, адресата и тему письма (должно совпадать с пунктом 3)
        wait.until(ExpectedConditions.elementToBeClickable(By
            .cssSelector("a.js-letter-list-item" + ":nth-of-type(1)"))).click();

        var actualAddressee = wait.until(ExpectedConditions.visibilityOfElementLocated(By
            .cssSelector(".letter__recipients > .letter-contact"))).getAttribute("title");
        Assertions.assertThat(actualAddressee).isEqualTo(LOGIN_NAME);

        var actualSubject = wait.until(ExpectedConditions.visibilityOfElementLocated(By
            .cssSelector("h2.thread-subject"))).getText();
        Assertions.assertThat(actualSubject).isEqualTo("Тест");

        var actualMessageText = wait.until(ExpectedConditions.visibilityOfElementLocated(By
            .cssSelector("div.js-readmsg-msg > div > div > div > :first-child"))).getText();
        Assertions.assertThat(actualMessageText).isEqualTo("Тест");

        // 8. Выйти из учётной записи
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("img.ph-avatar-img"))).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By
            .cssSelector("[data-testid='whiteline-account-exit']"))).click();



    }
}
