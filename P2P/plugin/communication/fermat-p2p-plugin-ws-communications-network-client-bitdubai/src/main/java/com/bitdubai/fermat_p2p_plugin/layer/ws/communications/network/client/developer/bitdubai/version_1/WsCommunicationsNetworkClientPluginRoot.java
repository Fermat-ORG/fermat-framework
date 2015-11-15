/*
* @#WsCommunicationsNetworkClientPluginRoot.java - 2015
* Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
* BITDUBAI/CONFIDENTIAL
*/
package com.bitdubai.fermat_p2p_plugin.layer.ws.communications.network.client.developer.bitdubai.version_1;

import com.bitdubai.fermat_api.Plugin;

import java.util.UUID;

/**
 * The Class <code>com.bitdubai.fermat_p2p_plugin.layer.ws.communications.network.client.developer.bitdubai.version_1.WsCommunicationsNetworkClientPluginRoot</code>
 * <p/>
 * Created by Hendry Rodriguez - (elnegroevaristo@gmail.com) on 12/11/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class WsCommunicationsNetworkClientPluginRoot implements Plugin {


    /**
     * DealsWithPluginIdentity Interface member variables.
     */
    private UUID pluginId;

    @Override
    public void setId(UUID pluginId) {
        this.pluginId=pluginId;
    }



}
