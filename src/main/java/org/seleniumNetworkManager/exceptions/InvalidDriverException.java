package org.seleniumNetworkManager.exceptions;

import net.bytebuddy.implementation.bind.MethodDelegationBinder;

public class InvalidDriverException extends Exception{
    InvalidDriverException(){
        super();
    }
    InvalidDriverException(String message, String browser){
        super(message);
        System.out.println("The current");
    }



}
