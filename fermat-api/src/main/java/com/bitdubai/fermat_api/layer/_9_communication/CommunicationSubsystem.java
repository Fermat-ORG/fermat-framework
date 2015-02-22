package com.bitdubai.fermat_api.layer._9_communication;

import com.bitdubai.fermat_api.Plugin;

/**
 * Created by ciencias on 20.01.15.
 */
public interface CommunicationSubsystem {
    public void start () throws CantStartSubsystemException;
    public Plugin getPlugin();
}
