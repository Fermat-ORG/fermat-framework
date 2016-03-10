package com.bitdubai.fermat_art_api.layer.actor_network_service.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.components.enums.PlatformComponentType;
import com.bitdubai.fermat_art_api.all_definition.interfaces.ArtIdentity;
import com.bitdubai.fermat_art_api.layer.actor_network_service.enums.NotificationDescriptor;

import java.util.UUID;

/**
 * Created by GAbriel Araujo 9/03/16.
 */
public interface ActorNotification extends ArtIdentity{

    UUID getId();

    String getActorSenderAlias();

    String getActorSenderPublicKey();

    PlatformComponentType getActorSenderType();

    byte[] getActorSenderProfileImage();

    String getActorDestinationPublicKey();

    PlatformComponentType getActorDestinationType();

    void setFlagRead(boolean flagRead);


    NotificationDescriptor getNotificationDescriptor();

    long getSentDate();


    String getMessageXML();
}
