package com.bitdubai.fermat_api.layer.osa_android;

/**
 * Created by ciencias on 30.12.14.
 */
public interface OsSubsystem {

    public void start () throws WrongOsException;
    public Os getOs();

}
