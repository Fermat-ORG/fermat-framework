package com.bitdubai.fermat_pip_api.layer.pip_network_service;

import com.bitdubai.fermat_api.Plugin;


/**
 * Created by natalia on 2015.07.28..
 */
public interface NetworkSubsystem {

    public void start () throws CantStartSubsystemException;
    public Plugin getPlugin();
}
