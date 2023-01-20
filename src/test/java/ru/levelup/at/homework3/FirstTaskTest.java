package ru.levelup.at.homework3;

import static org.assertj.core.api.Assertions.assertThat;
import java.time.Duration;
import java.util.List;
import com.google.common.base.Verify;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

 public class FirstTaskTest extends BaseTest {




    @Test
    void SendingLetter () {
        WebDriverWait wait = new WebDriverWait(driver,Duration.ofMillis(4000));
         driver.navigate().to("https://mail.ru/");
         var actualTitle=driver.getTitle();

        // --1. Войти в почту
             assertThat(actualTitle).isEqualTo("Mail.ru: почта, поиск в интернете, новости, игры");

        driver.findElement(By.cssSelector("[data-testid='enter-mail-primary']")).click();

        WebElement frame = driver.findElement(By.cssSelector("iframe.ag-popup__frame__layout__iframe"));
        WebDriver frameDriver = driver.switchTo().frame(frame);
        frameDriver.findElement(By.cssSelector("[name='username']")).sendKeys("kvv_test@mail.ru");
        frameDriver.findElement(By.cssSelector("[name='username']")).sendKeys(Keys.ENTER);
        frameDriver.findElement(By.cssSelector("[name='password']")).sendKeys("Level_up@");
        frameDriver.findElement(By.cssSelector("[name='password']")).sendKeys(Keys.ENTER);


        //--2.Assert
        driver.navigate().refresh();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.ph-project__account")));
        assertThat("kvv_test@mail.ru").isEqualTo(driver.findElement(By.cssSelector("div.ph-project__account")).getAttribute("aria-label"));

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".ph-project-promo-close-icon"))).click();

        //--3.Создать новое письмо (заполнить адресата, тему письма и тело)
        driver.findElement(By.xpath("//a[@title='Написать письмо']"));
        driver.findElement(By.xpath("//a[@title='Написать письмо']")).click();
        driver.findElement(By.cssSelector(".container--H9L5q")).click();
        driver.findElement(By.cssSelector("[type='text']")).sendKeys("chpok8787@mail.ru");
        driver.findElement(By.cssSelector("[name='Subject']")).click();
        driver.findElement(By.cssSelector("[name='Subject']")).sendKeys("homework3");
        driver.findElement(By.cssSelector("[name='Subject']")).click();
        driver.findElement(By.cssSelector("div.cke_editable")).sendKeys("Тело письма");
        driver.findElement(By.cssSelector("[class='vkuiButton__content']"));
        driver.findElement(By.cssSelector("[data-test-id='save']")).click();
        driver.findElement(By.cssSelector("button[tabindex='700']")).click();


        //--5. Verify, письмо сохранено в черновиках
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(BUTTON_DRAFT))).click();
        wait.until(ExpectedConditions.titleIs(PAGE_TITLE_DRAFT));
        final int sentMessagesBefore = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector("a.js-letter-list-item"))).size();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[href='/drafts/?']"))).click();
        wait.until(ExpectedConditions.titleIs(PAGE_TITLE_DRAFT));
        final int draftMessagesBefore = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector("a.js-letter-list-item"))).size();


        // --6. Verify контент, адресата и тему письма (должно совпадать с пунктом 3)
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("a.js-letter-list-item" + ":nth-of-type(1)"))).click();

        var actualAddressee = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@data-type='to']//span[contains(@class, 'text')]"))).getText();
        assertThat(actualAddressee).isEqualTo("chpok8787@mail.ru");

        var actualSubject = wait.until(ExpectedConditions.visibilityOfElementLocated(By
                                    .xpath("//div[contains(@class, 'compose-app_window')]//input[@name='Subject']")))
                                .getAttribute("value");
        assertThat(actualSubject).isEqualTo("homework3");

        var actualMessageText = wait.until(ExpectedConditions.visibilityOfElementLocated(By
            .cssSelector("div.js-readmsg-msg > div > div > div > :first-child"))).getText();
        assertThat(actualMessageText).isEqualTo("Тело письма");


        //--7. Отправить письмо
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[class='vkuiButton__content']"))).click();
     // подтверждение отправки:
       wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[class='ico ico_16-close ico_size_s']"))).click();

        //--8. Verify, что письмо исчезло из черновиков
        driver.findElement(By.cssSelector("a[data-folder-link-id='500001']")).click();
        wait.until(ExpectedConditions.titleIs("Черновики - Почта Mail.ru"));
        driver.navigate().refresh();
        List<WebElement> draftLetter1 = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector("a.js-letter-list-item")));
        int draftLetterCountAfter1 = draftLetter1.size();
        Verify.verify(true);


        //--9. Verify, что письмо появилось в папке отправленные;
        driver.findElement(By.cssSelector("a[data-folder-link-id='500000']")).click();
        wait.until(ExpectedConditions.titleIs("Отправленные - Почта Mail.ru"));
        wait.until(ExpectedConditions.numberOfElementsToBe(By.cssSelector("a.js-letter-list-item"), sentMessagesBefore + 1));

        //--10. Выйти из учётной записи
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("img.ph-avatar-img"))).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[data-testid='whiteline-account-exit']"))).click();



    }

 }

