package com.bitdubai.android_core.app.common.version_1.communication.client_system_broker.exceptions;

/**
 * Created by MAtias Furszyfer on 2016.06.08..
 */
public class MethodTimeOutException extends RuntimeException {

    private static final String DEFAULT_MESSAGE = "Method tooks too long and reach to the timeout established in the module";

    public MethodTimeOutException() {
        super(DEFAULT_MESSAGE);
    }
}
