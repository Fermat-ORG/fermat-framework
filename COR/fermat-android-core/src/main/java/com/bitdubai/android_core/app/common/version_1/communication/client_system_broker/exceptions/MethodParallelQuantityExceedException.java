package com.bitdubai.android_core.app.common.version_1.communication.client_system_broker.exceptions;

/**
 * Created by MAtias Furszyfer on 2016.06.08..
 */
public class MethodParallelQuantityExceedException extends RuntimeException {

    private static final String DEFAULT_MESSAGE = "Method exceed the quantity that has been stablished in the methodDetail annotation";

    public MethodParallelQuantityExceedException() {
        super(DEFAULT_MESSAGE);
    }
}
