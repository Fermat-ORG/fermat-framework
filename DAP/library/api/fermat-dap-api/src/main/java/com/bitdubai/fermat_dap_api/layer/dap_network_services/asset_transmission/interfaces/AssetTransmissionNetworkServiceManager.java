/*
 * @#AssetTransmissionNetworkServiceManager.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_dap_api.layer.dap_network_services.asset_transmission.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.components.interfaces.PlatformComponentProfile;
import com.bitdubai.fermat_dap_api.layer.all_definition.digital_asset.DigitalAssetMetadata;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.interfaces.ActorAssetUser;
import com.bitdubai.fermat_dap_api.layer.dap_network_services.asset_transmission.exceptions.CantConnectToAssetTransmissionNetworkServiceException;
import com.bitdubai.fermat_dap_api.layer.dap_network_services.asset_transmission.exceptions.CantRequestListAssetTransmissionNetworkServiceException;
import com.bitdubai.fermat_dap_api.layer.dap_network_services.asset_transmission.exceptions.CantSendDigitalAssetMetadataException;

/**
 * The interface <code>com.bitdubai.fermat_dap_api.layer.dap_network_services.asset_transmission.interfaces.AssetTransmissionNetworkServiceManager</code> define
 * the basic methods
 * <p/>
 *
 * Created by Roberto Requena - (rart3001@gmail.com) on 09/02/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public interface AssetTransmissionNetworkServiceManager {

    /**
     *
     * @param digitalAssetMetadataToSend
     * @param actorAssetUser
     * @throws CantSendDigitalAssetMetadataException
     */
    public void sendDigitalAssetMetadata(DigitalAssetMetadata digitalAssetMetadataToSend, ActorAssetUser actorAssetUser) throws CantSendDigitalAssetMetadataException;

}
