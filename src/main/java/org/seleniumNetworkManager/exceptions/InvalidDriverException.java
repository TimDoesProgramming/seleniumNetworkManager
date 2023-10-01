package org.seleniumNetworkManager.exceptions;

import net.bytebuddy.implementation.bind.MethodDelegationBinder;

public class InvalidDriverException extends Exception{
    InvalidDriverException(){
        super();
    }
    InvalidDriverException(String message, String browser){
        System.out.println("The current");
        super(message);

    }



}
