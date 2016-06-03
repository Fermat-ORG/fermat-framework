package org.fermat.fermat_dap_api.layer.dap_actor_network_service.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;

import org.fermat.fermat_dap_api.layer.dap_actor_network_service.enums.AssetNotificationDescriptor;

import java.util.UUID;

/**
 * Created by Nerio on 11/02/16.
 */
public interface ActorNotification {

    UUID getId();

    String getActorSenderAlias();

    String getActorSenderPublicKey();

    Actors getActorSenderType();

    byte[] getActorSenderProfileImage();

    String getActorDestinationPublicKey();

    Actors getActorDestinationType();

    void setFlagRead(boolean flagRead);

    /**
     * The method <code>getAssetNotificationDescriptor</code> tells us the nature of the notification
     *
     * @return the descriptor of the notification
     */
    AssetNotificationDescriptor getAssetNotificationDescriptor();

    long getSentDate();

    BlockchainNetworkType getBlockchainNetworkType();

    String getMessageXML();

}
