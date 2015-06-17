package com.bitdubai.fermat_p2p_api.layer.p2p_communication;

import com.bitdubai.fermat_api.Plugin;

/**
 * Created by ciencias on 20.01.15.
 */
public interface CommunicationSubsystem {
    public void start () throws CantStartSubsystemException;
    public Plugin getPlugin();
}
