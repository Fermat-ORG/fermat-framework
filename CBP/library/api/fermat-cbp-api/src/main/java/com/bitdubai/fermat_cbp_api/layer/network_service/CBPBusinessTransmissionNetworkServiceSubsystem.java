package com.bitdubai.fermat_cbp_api.layer.network_service;

import com.bitdubai.fermat_api.Plugin;
import com.bitdubai.fermat_cbp_api.all_definition.exceptions.CantStartSubsystemException;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 20/11/15.
 */
public interface CBPBusinessTransmissionNetworkServiceSubsystem {
    void start() throws CantStartSubsystemException;
    Plugin getPlugin();
}
