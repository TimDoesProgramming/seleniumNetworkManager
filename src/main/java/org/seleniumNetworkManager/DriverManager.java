package org.seleniumNetworkManager;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.safari.SafariDriver;

public class DriverManager {
    public static WebDriver driver;
    private static Browsers currentBrowser;
    private DriverManager(Browsers browsers){

        switch (browsers) {
            case CHROME -> driver = new ChromeDriver();
            case FIREFOX -> driver = new FirefoxDriver();
            case EDGE -> driver = new EdgeDriver();
            case SAFARI -> driver = new SafariDriver();
            default -> driver = new ChromeDriver();
        }
    }

    public static WebDriver getDriver(Browsers browser) {
        if(driver == null)
            return (WebDriver)(new DriverManager(browser));
        return driver;
    }
    public static void terminateDriver(){
        if(driver != null){
            driver.quit();
            driver = null;
        }
    }

    public static Browsers getCurrentBrowser() {
        return currentBrowser;
    }

    public static void setCurrentBrowser(Browsers currentBrowser) {
        DriverManager.currentBrowser = currentBrowser;
    }
}
