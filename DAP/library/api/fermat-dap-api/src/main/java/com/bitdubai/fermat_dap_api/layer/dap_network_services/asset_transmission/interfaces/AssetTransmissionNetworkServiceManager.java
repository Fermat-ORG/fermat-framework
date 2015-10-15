/*
 * @#AssetTransmissionNetworkServiceManager.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_dap_api.layer.dap_network_services.asset_transmission.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.TransactionProtocolManager;
import com.bitdubai.fermat_dap_api.layer.all_definition.digital_asset.DigitalAssetMetadata;
import com.bitdubai.fermat_dap_api.layer.all_definition.enums.DistributionStatus;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_issuer.interfaces.ActorAssetIssuer;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.interfaces.ActorAssetUser;
import com.bitdubai.fermat_dap_api.layer.dap_network_services.asset_transmission.exceptions.CantSendDigitalAssetMetadataException;
import com.bitdubai.fermat_dap_api.layer.dap_network_services.asset_transmission.exceptions.CantSendTransactionNewStatusNotificationException;

import java.util.UUID;

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
public interface AssetTransmissionNetworkServiceManager extends TransactionProtocolManager<DigitalAssetMetadataTransaction> {

    /**
     * Method that send DigitalAssetMetadata
     *
     * @param actorAssetIssuerSender
     * @param actorAssetUserReceiver
     * @param digitalAssetMetadataToSend
     * @throws CantSendDigitalAssetMetadataException
     */
    void sendDigitalAssetMetadata(ActorAssetIssuer actorAssetIssuerSender, ActorAssetUser actorAssetUserReceiver, DigitalAssetMetadata digitalAssetMetadataToSend) throws CantSendDigitalAssetMetadataException;

    /**
     * Method that send the Transaction New Status Notification
     *
     * @param transactionId (GenesisTransaction)
     * @param newDistributionStatus
     */
    void sendTransactionNewStatusNotification(ActorAssetUser actorAssetUserSender,  ActorAssetIssuer actorAssetIssuerReceiver, String transactionId, DistributionStatus newDistributionStatus) throws CantSendTransactionNewStatusNotificationException;

}
