/*
* @#DAPAssetTransmissionNetworkServiceSubsystem.java - 2015
* Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
* BITDUBAI/CONFIDENTIAL
*/
package com.bitdubai.fermat_dap_api.layer.dap_network_services.asset_transmission;

import com.bitdubai.fermat_api.Plugin;
import com.bitdubai.fermat_dap_api.layer.dap_network_services.asset_transmission.exceptions.CantStartSubsystemException;

/**
 * The Class <code>com.bitdubai.fermat_dap_api.layer.dap_network_services.asset_transmission.DAPAssetTransmissionNetworkServiceSubsystem</code>
 * <p/>
 * Created by Hendry Rodriguez - (elnegroevaristo@gmail.com) on 08/10/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public interface DAPAssetTransmissionNetworkServiceSubsystem {

    void start() throws CantStartSubsystemException;
    Plugin getPlugin();
}
