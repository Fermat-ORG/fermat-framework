package com.bitdubai.fermat_dap_api.layer.dap_network_services.asset_transmission.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.components.interfaces.PlatformComponentProfile;
import com.bitdubai.fermat_dap_api.layer.all_definition.digital_asset.DigitalAssetMetadata;
import com.bitdubai.fermat_dap_api.layer.dap_network_services.asset_transmission.exceptions.CantConnectToAssetTransmissionNetworkServiceException;
import com.bitdubai.fermat_dap_api.layer.dap_network_services.asset_transmission.exceptions.CantRequestListAssetTransmissionNetworkServiceException;
import com.bitdubai.fermat_dap_api.layer.dap_network_services.asset_transmission.exceptions.CantSendDigitalAssetMetadataException;

/**
 * Created by root on 06/10/15.
 */
public interface AssetTransmissionNetworkServiceManager {

    public void requestListAssetTransmissionNetworkService(PlatformComponentProfile actorAssetUser) throws CantRequestListAssetTransmissionNetworkServiceException;

    public void connectTo(PlatformComponentProfile assetTransmissionNetworkServiceRemote) throws CantConnectToAssetTransmissionNetworkServiceException;

    public void sendDigitalAssetMetadata(DigitalAssetMetadata toSend, PlatformComponentProfile assetTransmissionNetworkServiceRemote) throws CantSendDigitalAssetMetadataException;

}
