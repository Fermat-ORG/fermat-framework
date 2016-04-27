package com.bitdubai.android_core.app.common.version_1.communication.client_system_broker;

import java.lang.reflect.Method;

/**
 * Created by mati on 2016.04.27..
 */
public class WorkOnMainThreadException extends RuntimeException {

    public WorkOnMainThreadException(Object object,Method method) {
        super("WorkInMainThreadException in:"+object.getClass().getInterfaces()[0].getName()+"\n" +
                "Method: "+ method);
    }
}
