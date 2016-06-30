package com.bitdubai.android_core.app.common.version_1.communication.client_system_broker.exceptions;

import java.lang.reflect.Method;

/**
 * Created by mati on 2016.04.27..
 */
public class LargeDataRequestException extends RuntimeException {

    public LargeDataRequestException(Object object, Method method) {
        super("LargeDataRequestException in:"+object.getClass().getInterfaces()[0].getName()+"\n" +
                "Method: "+ method+ "\n");
    }

    public LargeDataRequestException(Object object, Method method,Exception e) {
        super("LargeDataRequestException in:"+object.getClass().getInterfaces()[0].getName()+"\n" +
                "Method: "+ method+ "\n",e);
    }


}
