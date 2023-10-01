package org.seleniumNetworkManager;

import org.openqa.selenium.
import org.openqa.selenium.remote.RemoteWebDriver;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class NetworkManager {

    ConcurrentHashMap<String, String> urlMap;
    ConcurrentHashMap<String, String> bodyMap;

    private static List<String> browsers = Arrays.asList(new String[] {"chrome"});

    NetworkManager(WebDriver driver){

        String browserName;
        browserName = ((RemoteWebDriver)driver).getCapabilities().getBrowserName().toLowerCase();

        if(browsers.contains(browserName)){

        }
    }

}
