package com.bitdubai.smartwallet.platform.layer._1_os;

/**
 * Created by ciencias on 30.12.14.
 */
public interface OsSubsystem {

    public void start () throws WrongOsException;
    public Os getOs();

}
