package com.bitdubai.fermat_cht_api.layer.actor_connection.utils;

import com.bitdubai.fermat_api.layer.actor_connection.common.enums.ConnectionState;
import com.bitdubai.fermat_api.layer.actor_connection.common.structure_abstract_classes.ActorConnection;

import java.util.UUID;

/**
 * Created by José D. Vilchez A. (josvilchezalmera@gmail.com) on 05/04/16.
 */
public final class ChatActorConnection extends ActorConnection<ChatLinkedActorIdentity> {
    private final String country;
    private final String state;
    private final String city;
    private String status = "";

    public ChatActorConnection(UUID connectionId, ChatLinkedActorIdentity linkedIdentity, String publicKey, String alias, byte[] image, ConnectionState connectionState, long creationTime, long updateTime, String status) {
        super(connectionId, linkedIdentity, publicKey, alias, image, connectionState, creationTime, updateTime);
        this.country="";
        this.state="";
        this.city="";
        this.status=status;
    }

    public ChatActorConnection(UUID connectionId, ChatLinkedActorIdentity linkedIdentity, String publicKey, String alias, byte[] image, ConnectionState connectionState, long creationTime, long updateTime, String country, String state, String city, String status) {
        super(connectionId, linkedIdentity, publicKey, alias, image, connectionState, creationTime, updateTime);
        this.country=country;
        this.state=state;
        this.city=city;
        this.status=status;
    }

    public String getCountry() {
        return country;
    }

    public String getState() {
        return state;
    }

    public String getCity() {
        return city;
    }

    public String getStatus() {
        return status;
    }
}
