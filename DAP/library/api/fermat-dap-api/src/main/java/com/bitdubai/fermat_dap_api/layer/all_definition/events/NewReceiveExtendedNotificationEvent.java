package com.bitdubai.fermat_dap_api.layer.all_definition.events;

import com.bitdubai.fermat_bch_api.layer.crypto_vault.watch_only_vault.ExtendedPublicKey;
import com.bitdubai.fermat_dap_api.layer.all_definition.enums.EventType;
import com.bitdubai.fermat_dap_api.layer.all_definition.network_service_message.DAPMessage;
import com.bitdubai.fermat_dap_api.layer.dap_actor_network_service.interfaces.ActorNotification;

/**
 * Created by Nerio on 25/11/15.
 */
public class NewReceiveExtendedNotificationEvent extends AbstractDAPEvent {

//    private String SenderKey;
//    private String Alias;
//    private String DestinationKey;
//    private ExtendedPublicKey extendedPublicKey = null;

    private ActorNotification actorNotification;

//    public ExtendedPublicKey getExtendedPublicKey() {
//        return extendedPublicKey;
//    }

//    public void setExtendedPublicKey(ExtendedPublicKey extendedPublicKey) {
//        this.extendedPublicKey = extendedPublicKey;
//    }

//    public String getDestinationKey() {
//        return DestinationKey;
//    }

//    public void setDestinationKey(String destinationKey) {
//        DestinationKey = destinationKey;
//    }

    public NewReceiveExtendedNotificationEvent(EventType eventType) {
        super(eventType);
    }

    public ActorNotification getActorNotification() {
        return actorNotification;
    }

    public void setActorNotification(ActorNotification actorNotification) {
        this.actorNotification = actorNotification;
    }
//    public String getSenderKey() {
//        return SenderKey;
//    }

//    public void setSenderKey(String senderKey) {
//        SenderKey = senderKey;
//    }

//    public String getAlias() {
//        return Alias;
//    }

//    public void setAlias(String alias) {
//        Alias = alias;
//    }
}
