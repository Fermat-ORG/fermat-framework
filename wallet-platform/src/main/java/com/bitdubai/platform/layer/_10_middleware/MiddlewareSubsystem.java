package com.bitdubai.platform.layer._10_middleware;


/**
 * Created by ciencias on 20.01.15.
 */
public interface MiddlewareSubsystem {
    public void start () throws CantStartSubsystemException;
    public MiddlewareEngine getMiddlewareEngine();
}
