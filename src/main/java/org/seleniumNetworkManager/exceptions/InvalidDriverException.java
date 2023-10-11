package org.seleniumNetworkManager.exceptions;

import net.bytebuddy.implementation.bind.MethodDelegationBinder;

public class InvalidDriverException extends Exception{
    public InvalidDriverException(){
        super();
    }
    public InvalidDriverException(String message){
        super(message);
    }
    public InvalidDriverException(String message, String browser){
        super(message);
        System.out.println("The selected browser is incorrect: "+ browser);
    }



}
