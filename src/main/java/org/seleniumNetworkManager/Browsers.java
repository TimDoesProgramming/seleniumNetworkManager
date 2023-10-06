package org.seleniumNetworkManager;

public enum Browsers {
    CHROME("chrome"),
    FIREFOX("firefox");
//    EDGE("edge"),
//    SAFARI("safari");

    private String name;
    Browsers(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }
    public boolean hasValue(String browserName){
        for(Browsers b : Browsers.values()){
            if(b.getName().equals(browserName))
                return true;
        }
        return false;
    }
}
