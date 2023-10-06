package org.seleniumNetworkManager;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.bidi.BiDi;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.v115.network.Network;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class NetworkManager {

    private ConcurrentHashMap<String, String> urlMap;
    private ConcurrentHashMap<String, String> bodyMap;

    private DevTools devTools;
    private BiDi biDiTools;
    private static List<String> browsers =  Arrays.asList(new String[] {"chrome"});

    NetworkManager(WebDriver driver){

        String browserName;
        ChromeDriver cd;
        FirefoxDriver fd;
        browserName = ((RemoteWebDriver)driver).getCapabilities().getBrowserName().toLowerCase();

        if(browserName.equals("chrome")){
            cd = (ChromeDriver)driver;
            devTools = cd.getDevTools();
        }else if (browserName.equals("firefox")){
            fd = (FirefoxDriver)driver;
            biDiTools = fd.getBiDi();


        }
    }

    public void startRecording(){
        devTools.createSession();
        devTools.send(Network.enable(Optional.empty(),Optional.empty(), Optional.empty()))

        devTools.addListener(Network.responseReceived(), responseReceived -> {
            String requestId, url;
            requestId = responseReceived.getRequestId().toString();
            url = responseReceived.getResponse().getUrl();

            if(url != null && requestId != null)
                urlMap.put(requestId, url);
            else{
                System.out.println("Could not print the line");
            }
        });

        devTools.addListener(Network.loadingFinished(), loadingFinished -> {
            String requestId, body;
            requestId = loadingFinished.getRequestId().toString();
            Network.getResponseBody(loadingFinished.getRequestId());

        });

    }
    public void stopRecording(){
        devTools.disconnectSession();
    }

}
