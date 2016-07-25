package com.bitdubai.android_core.app.common.version_1.communication.client_system_broker.exceptions;

import java.lang.reflect.Method;

/**
 * Created by mati on 2016.04.27..
 */
public class LargeWorkOnMainThreadException extends RuntimeException {

    public LargeWorkOnMainThreadException(Object object, Method method) {
        super(new StringBuilder().append("LargeWorkOnMainThreadException in:").append(object.getClass().getInterfaces()[0].getName()).append("\n").append("Method: ").append(method).append("\n").append("Please use a new thread to work with large data request").toString());
    }

    public LargeWorkOnMainThreadException(Object object, Method method, Exception e) {
        super(new StringBuilder().append("LargeWorkOnMainThreadException in:").append(object.getClass().getInterfaces()[0].getName()).append("\n").append("Method: ").append(method).append("\n").append("Please use a new thread to work with large data request").toString(), e);
    }

}
