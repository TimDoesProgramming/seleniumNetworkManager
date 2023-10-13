package org.seleniumNetworkManager;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.bidi.BiDi;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.v115.network.model.RequestId;
import org.openqa.selenium.devtools.v115.network.Network;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.seleniumNetworkManager.exceptions.InvalidDriverException;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class NetworkManager {

    private ConcurrentHashMap<String, String> urlMap;
    private ConcurrentHashMap<String, String> bodyMap;

    private DevTools devTools;
    private BiDi biDiTools;
    private static List<String> browsers =  Arrays.asList(new String[] {"chrome"});

    NetworkManager(WebDriver driver) throws InvalidDriverException {

        String browserName;
        ChromeDriver cd;
        FirefoxDriver fd;
        EdgeDriver ed;
        SafariDriver sd;
        browserName = ((RemoteWebDriver)driver).getCapabilities().getBrowserName().toLowerCase();

        if(browserName.equalsIgnoreCase("chrome")){
            cd = (ChromeDriver)driver;
            devTools = cd.getDevTools();
        }else if (browserName.equalsIgnoreCase("firefox")){
            fd = (FirefoxDriver)driver;
            biDiTools = fd.getBiDi();
        }
        else if(browserName.equalsIgnoreCase("edge")){
            ed = (EdgeDriver) driver;
            devTools = ed.getDevTools();
        }
        //devtools?
        else if(browserName.equalsIgnoreCase("safari")){
            sd = (SafariDriver) driver;

        }else{
            throw new InvalidDriverException();
        }
    }

    public void startRecording(){

        final RequestId[] requestIds = new RequestId[1];
        devTools.createSession();
        devTools.send(Network.enable(Optional.empty(),Optional.empty(), Optional.empty()));

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

        devTools.addListener(Network.webSocketCreated(), handler->{

        });

        devTools.addListener(Network.loadingFinished(), loadingFinished -> {
            String requestId, body, url;
            requestId = body = url = null;
            try{
                requestIds[0] = loadingFinished.getRequestId();
                requestId = requestIds[0].toString();
                url = urlMap.get(requestId);
                body = Network.getResponseBody(requestIds[0]).toString();
                bodyMap.put(url, body);

            }catch(Exception e){
                System.out.println(
                        "The request Id is: "+requestId+
                        "\nThe current url: "+ url+
                        "\n" + e);
            }


        });

    }

    /**
     *
     * @param endpoint
     * @return The body of the request
     */
    public String getBody(String endpoint){
        String body;
        try{
            body = bodyMap.get(endpoint);

        }catch (NullPointerException npe){
            System.out.println(""+npe);
            return null;
        }
        return body;
    }
    public void stopRecording(){
        devTools.disconnectSession();
    }

}
