/*
* @#DAPAssetUserActorNetworkServiceSubsystem.java - 2015
* Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
* BITDUBAI/CONFIDENTIAL
*/
package com.bitdubai.fermat_dap_api.layer.dap_actor_network_service.asset_user;

import com.bitdubai.fermat_api.Plugin;
import com.bitdubai.fermat_dap_api.layer.dap_actor_network_service.asset_user.exceptions.CantStartSubsystemException;

/**
 * The Class <code>com.bitdubai.fermat_dap_api.layer.dap_actor_network_service.asset_user.DAPAssetUserActorNetworkServiceSubsystem</code>
 * <p/>
 * Created by Hendry Rodriguez - (elnegroevaristo@gmail.com) on 08/10/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public interface DAPAssetUserActorNetworkServiceSubsystem {

    void start() throws CantStartSubsystemException;
    Plugin getPlugin();
}
