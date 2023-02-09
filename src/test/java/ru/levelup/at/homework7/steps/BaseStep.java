package ru.levelup.at.homework7.steps;

import org.openqa.selenium.WebDriver;
import ru.levelup.at.homework5.LoginRegistrationPage;
import ru.levelup.at.homework5.MainPage;

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
