/*
 * @#AssetTransmissionNetworkServiceManager.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_dap_api.layer.dap_network_services.asset_transmission.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.components.enums.PlatformComponentType;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.TransactionProtocolManager;
import com.bitdubai.fermat_dap_api.layer.all_definition.digital_asset.DigitalAssetMetadata;
import com.bitdubai.fermat_dap_api.layer.all_definition.enums.DistributionStatus;
import com.bitdubai.fermat_dap_api.layer.dap_actor.DAPActor;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_issuer.interfaces.ActorAssetIssuer;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.interfaces.ActorAssetUser;
import com.bitdubai.fermat_dap_api.layer.dap_network_services.asset_transmission.exceptions.CantSendDigitalAssetMetadataException;
import com.bitdubai.fermat_dap_api.layer.dap_network_services.asset_transmission.exceptions.CantSendTransactionNewStatusNotificationException;

/**
 * The interface <code>com.bitdubai.fermat_dap_api.layer.dap_network_services.asset_transmission.interfaces.AssetTransmissionNetworkServiceManager</code> define
 * the basic methods
 * <p/>
 * <p/>
 * Created by Roberto Requena - (rart3001@gmail.com) on 09/02/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public interface AssetTransmissionNetworkServiceManager extends TransactionProtocolManager<DigitalAssetMetadataTransaction> {

    /**
     * This method sends the digital asset metadata to a remote device.
     *
     * @param actorSender                The {@link DAPActor} that is sending the metadata
     * @param actorReceiver              The {@link DAPActor} that will receive the metadata
     * @param digitalAssetMetadataToSend The {@link DigitalAssetMetadata} that is going to be sent.
     * @throws CantSendDigitalAssetMetadataException
     */
    void sendDigitalAssetMetadata(DAPActor actorSender, DAPActor actorReceiver, DigitalAssetMetadata digitalAssetMetadataToSend) throws CantSendDigitalAssetMetadataException;

    /**
     * Method that send the Transaction New Status Notification
     *
     * @param actorSenderPublicKey {@link String} that represents the public key from the actor that sends the message.
     * @param senderType {@link PlatformComponentType} that represents the type of actor that sends the message
     * @param actorReceiverPublicKey {@link String} that represents the public key from the actor that receives the message.
     * @param receiverType {@link PlatformComponentType} that represents the type of actor that receives the message
     * @param transactionId {@link String} the id of the transaction related with the status notification
     * @param newDistributionStatus {@link DistributionStatus} with the new status for the transaction.
     * @throws CantSendTransactionNewStatusNotificationException in case something goes wrong while trying to send the message.
     */
    void sendTransactionNewStatusNotification(String actorSenderPublicKey,
                                              PlatformComponentType senderType,
                                              String actorReceiverPublicKey,
                                              PlatformComponentType receiverType,
                                              String transactionId,
                                              DistributionStatus newDistributionStatus) throws CantSendTransactionNewStatusNotificationException;

    /**
     * Method that send the Transaction New Status Notification
     * <p/>
     * This method is deprecated, instead you should use its overload version that sends from an
     * {@link DAPActor} to another one. This method would remain in there for compatibility. Probably this one will disappear soon.
     *
     * @param transactionId         (GenesisTransaction)
     * @param newDistributionStatus
     */
    @Deprecated
    void sendTransactionNewStatusNotification(ActorAssetUser actorAssetUserSender, String actorAssetIssuerReceiverPublicKey, String transactionId, DistributionStatus newDistributionStatus) throws CantSendTransactionNewStatusNotificationException;


    /**
     * Method that send DigitalAssetMetadata.
     * This method is deprecated, instead you should use its overload version that sends from an
     * {@link DAPActor} to another one. This method would remain in there for compatibility. Probably it will dissapear soon.
     *
     * @param actorAssetIssuerSender
     * @param actorAssetUserReceiver
     * @param digitalAssetMetadataToSend
     * @throws CantSendDigitalAssetMetadataException
     */
    @Deprecated
    void sendDigitalAssetMetadata(ActorAssetIssuer actorAssetIssuerSender, ActorAssetUser actorAssetUserReceiver, DigitalAssetMetadata digitalAssetMetadataToSend) throws CantSendDigitalAssetMetadataException;

}
