package ru.levelup.at.homework7;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import ru.levelup.at.homework7.context.TestContext;
import ru.levelup.at.homework7.steps.ActionStep;
import ru.levelup.at.homework7.steps.AssertionStep;

public abstract class BaseTest {

    protected WebDriver driver;
    protected ActionStep actionStep;
    protected AssertionStep assertionStep;

    private static final GetUserCredentials userCredentials = new GetUserCredentials();
    protected static final String LOGIN_NAME = userCredentials.getCredentialByKey("user1_login");
    protected static final String MAIL_PASSWORD = userCredentials.getCredentialByKey("user1_password");

    @BeforeEach
    void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        actionStep = new ActionStep(driver);
        assertionStep = new AssertionStep(driver);
        TestContext.getInstance().putObject("driver", driver);
    }

    @AfterEach
    void tearDown() {
        driver.quit();
        TestContext.clear();
    }
}
