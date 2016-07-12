package com.bitdubai.android_core.app.common.version_1.communication.client_system_broker.exceptions;

/**
 * Created by mati on 2016.03.25..
 */
public class FermatServiceNotConnectedException extends Exception {

    public FermatServiceNotConnectedException() {
        super("Service not connected");
    }

    public FermatServiceNotConnectedException(String detailMessage) {
        super(detailMessage);
    }

    public FermatServiceNotConnectedException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }

    public FermatServiceNotConnectedException(Throwable throwable) {
        super(throwable);
    }
}
