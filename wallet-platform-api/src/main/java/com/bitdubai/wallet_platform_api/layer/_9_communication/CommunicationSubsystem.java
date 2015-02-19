package com.bitdubai.wallet_platform_api.layer._9_communication;

import com.bitdubai.wallet_platform_api.Plugin;

/**
 * Created by ciencias on 20.01.15.
 */
public interface CommunicationSubsystem {
    public void start () throws CantStartSubsystemException;
    public Plugin getPlugin();
}
