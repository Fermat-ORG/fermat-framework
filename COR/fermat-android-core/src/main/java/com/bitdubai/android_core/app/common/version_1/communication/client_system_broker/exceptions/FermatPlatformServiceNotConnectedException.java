package com.bitdubai.android_core.app.common.version_1.communication.client_system_broker.exceptions;

/**
 * Created by Matias Furszyfer on 2016.06.08..
 */
public class FermatPlatformServiceNotConnectedException extends RuntimeException {

    private static final String DEFAULT_MESSAGE = "Fermat Platform Service is not connected yet, Android Framework have to capture this exception. Contact to Furszy";

    public FermatPlatformServiceNotConnectedException() {
        super(DEFAULT_MESSAGE);
    }

    public FermatPlatformServiceNotConnectedException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }
}
