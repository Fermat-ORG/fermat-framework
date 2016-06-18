package com.bitdubai.android_core.app.common.version_1.communication.client_system_broker.exceptions;

import java.lang.reflect.Method;

/**
 * Created by mati on 2016.04.27..
 */
public class LargeWorkOnMainThreadException extends RuntimeException {

    public LargeWorkOnMainThreadException(Object object, Method method) {
        super("LargeWorkOnMainThreadException in:"+object.getClass().getInterfaces()[0].getName()+"\n" +
                "Method: "+ method+ "\n" +
                "Please use a new thread to work with large data request");
    }
    public LargeWorkOnMainThreadException(Object object, Method method,Exception e) {
        super("LargeWorkOnMainThreadException in:"+object.getClass().getInterfaces()[0].getName()+"\n" +
                "Method: "+ method+ "\n" +
                "Please use a new thread to work with large data request", e);
    }

}
