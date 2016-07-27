package com.bitdubai.android_core.app.common.version_1.communication.client_system_broker.exceptions;

import java.lang.reflect.Method;

/**
 * Created by mati on 2016.04.27..
 */
public class LargeDataRequestException extends RuntimeException {

    public LargeDataRequestException(Object object, Method method) {
        super(new StringBuilder().append("LargeDataRequestException in:").append(object.getClass().getInterfaces()[0].getName()).append("\n").append("Method: ").append(method).append("\n").toString());
    }

    public LargeDataRequestException(Object object, Method method, Exception e) {
        super(new StringBuilder().append("LargeDataRequestException in:").append(object.getClass().getInterfaces()[0].getName()).append("\n").append("Method: ").append(method).append("\n").toString(), e);
    }


}
