package ru.levelup.at.homework3;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.time.Duration;
import java.time.Instant;

import static org.openqa.selenium.support.ui.ExpectedConditions.presenceOfAllElementsLocatedBy;

public abstract class BaseTest {



    protected WebDriver driver;
    protected final String LOGIN_NAME ="kvv_test@mail.ru";
    protected final String MAIL_PASSWORD ="Level_up@";
    protected final String BUTTON_DRAFT = "a[data-folder-link-id='500001']";
    protected static final String PAGE_TITLE_DRAFT = "Черновики - Почта Mail.ru";

    private Instant wait;

    @BeforeEach
    void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(9000));


    }




}
