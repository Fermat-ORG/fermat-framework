package com.bitdubai.wallet_platform_api.layer._3_os;

/**
 * Created by ciencias on 30.12.14.
 */
public interface OsSubsystem {

    public void start () throws WrongOsException;
    public Os getOs();

}
