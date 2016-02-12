/*
 * @#AssetTransmissionNetworkServiceManager.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_dap_api.layer.dap_network_services.asset_transmission.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.components.enums.PlatformComponentType;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.TransactionProtocolManager;
import com.bitdubai.fermat_dap_api.layer.all_definition.enums.DistributionStatus;
import com.bitdubai.fermat_dap_api.layer.all_definition.network_service_message.DAPMessage;
import com.bitdubai.fermat_dap_api.layer.all_definition.network_service_message.content_message.AssetMetadataContentMessage;
import com.bitdubai.fermat_dap_api.layer.dap_network_services.asset_transmission.exceptions.CantSendDigitalAssetMetadataException;
import com.bitdubai.fermat_dap_api.layer.dap_network_services.asset_transmission.exceptions.CantSendTransactionNewStatusNotificationException;

/**
 * The interface <code>com.bitdubai.fermat_dap_api.layer.dap_network_services.asset_transmission.interfaces.AssetTransmissionNetworkServiceManager</code> define
 * the basic methods
 * <p/>
 * <p/>
 * Created by Roberto Requena - (rart3001@gmail.com) on 09/02/15.
 * Modified by Víctor Mars - (marsvicam@gmail.com) on 15/12/15.
 * @version 1.0
 * @since Java JDK 1.7
 */
public interface AssetTransmissionNetworkServiceManager extends TransactionProtocolManager<DigitalAssetMetadataTransaction> {

    /**
     * This method sends the digital asset metadata to a remote device.
     *
     * @param dapMessage {@link DAPMessage<AssetMetadataContentMessage>} instance, which should contain all the information to send
     *                                                                  this metadata to the destination device.
     * @throws CantSendDigitalAssetMetadataException
     */
    void sendDigitalAssetMetadata(DAPMessage dapMessage) throws CantSendDigitalAssetMetadataException;

    /**
     * Method that send the Transaction New Status Notification
     *
     * @param actorSenderPublicKey {@link String} that represents the public key from the actor that sends the message.
     * @param senderType {@link PlatformComponentType} that represents the type of actor that sends the message
     * @param actorReceiverPublicKey {@link String} that represents the public key from the actor that receives the message.
     * @param receiverType {@link PlatformComponentType} that represents the type of actor that receives the message
     * @param genesisTransaction {@link String} the genesisTransaction related with the status notification
     * @param newDistributionStatus {@link DistributionStatus} with the new status for the transaction.
     * @throws CantSendTransactionNewStatusNotificationException in case something goes wrong while trying to send the message.
     */
    void sendTransactionNewStatusNotification(String actorSenderPublicKey,
                                              PlatformComponentType senderType,
                                              String actorReceiverPublicKey,
                                              PlatformComponentType receiverType,
                                              String genesisTransaction,
                                              DistributionStatus newDistributionStatus) throws CantSendTransactionNewStatusNotificationException;
}
