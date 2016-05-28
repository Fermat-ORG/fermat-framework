package com.bitdubai.android_core.app.common.version_1.communication.client_system_broker.exceptions;

import java.lang.reflect.Method;

/**
 * Created by MAtias Furszyfer on 2016.04.27..
 */
public class InvalidMethodExecutionException extends RuntimeException {

    public InvalidMethodExecutionException(Object object, Method method,String cause) {
        super("InvalidMethodExecutionException in:"+object.getClass().getInterfaces()[0].getName()+"\n" +
                "Method: "+ method+ "\n" +
                "Method detail is not corresponding to the actual method call, cause: "+cause);
    }
}
