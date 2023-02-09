package ru.levelup.at.homework5.steps;

import ru.levelup.at.homework5.LoginRegistrationPage;
import ru.levelup.at.homework5.MainPage;
import org.openqa.selenium.WebDriver;

public class BaseStep  {

    protected final WebDriver driver;

    protected static LoginRegistrationPage loginRegistrationPage;
    protected static MainPage mainPage;

    protected BaseStep(WebDriver driver) {
        this.driver = driver;
        loginRegistrationPage = new LoginRegistrationPage(driver);
        mainPage = new MainPage(driver);
    }
}
