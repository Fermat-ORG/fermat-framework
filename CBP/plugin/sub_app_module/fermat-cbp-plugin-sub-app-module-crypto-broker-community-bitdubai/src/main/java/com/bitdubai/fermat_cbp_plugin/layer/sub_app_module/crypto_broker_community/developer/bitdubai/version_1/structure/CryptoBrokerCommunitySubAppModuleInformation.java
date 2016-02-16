package com.bitdubai.fermat_cbp_plugin.layer.sub_app_module.crypto_broker_community.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.actor_connection.common.enums.ConnectionState;
import com.bitdubai.fermat_cbp_api.layer.actor_connection.crypto_broker.utils.CryptoBrokerActorConnection;
import com.bitdubai.fermat_cbp_api.layer.actor_network_service.crypto_broker.utils.CryptoBrokerExposingData;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_broker_community.interfaces.CryptoBrokerCommunityInformation;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

/**
 * The class <code>com.bitdubai.fermat_cbp_plugin.layer.sub_app_module.crypto_broker_community.developer.bitdubai.version_1.structure.CryptoBrokerCommunitySubAppModuleInformation</code>
 * bla bla bla.
 * <p>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 22/12/2015.
 */
public class CryptoBrokerCommunitySubAppModuleInformation implements CryptoBrokerCommunityInformation {

    private final String publicKey;
    private final String alias    ;
    private final byte[] image    ;
    private final ConnectionState connectionState;
    private final UUID connectionId;


    public CryptoBrokerCommunitySubAppModuleInformation(final String publicKey,
                                                        final String alias,
                                                        final byte[] image) {

        this.publicKey = publicKey;
        this.alias     = alias    ;
        this.image     = image    ;
        this.connectionState = null;
        this.connectionId = null;
    }

    public CryptoBrokerCommunitySubAppModuleInformation(final String publicKey,
                                                          final String alias,
                                                          final byte[] image,
                                                          final ConnectionState connectionState,
                                                          final UUID connectionId) {

        this.publicKey          = publicKey      ;
        this.alias              = alias          ;
        this.image              = image          ;
        this.connectionState    = connectionState;
        this.connectionId       = connectionId   ;
    }

    public CryptoBrokerCommunitySubAppModuleInformation(final CryptoBrokerActorConnection actorConnection) {

        this.publicKey = actorConnection.getPublicKey();
        this.alias     = actorConnection.getAlias()    ;
        this.image     = actorConnection.getImage()    ;
        this.connectionState = actorConnection.getConnectionState();
        this.connectionId = actorConnection.getConnectionId();
    }

    public CryptoBrokerCommunitySubAppModuleInformation(final CryptoBrokerExposingData exposingData) {

        this.publicKey = exposingData.getPublicKey();
        this.alias     = exposingData.getAlias()    ;
        this.image     = exposingData.getImage()    ;
        this.connectionState = null;
        this.connectionId = null;
    }

    @Override
    public String getPublicKey() {
        return publicKey;
    }

    @Override
    public String getAlias() {
        return alias;
    }

    @Override
    public byte[] getImage() {
        return image;
    }

    @Override
    public List listCryptoBrokerWallets() {
        return null;
    }

    @Override
    public ConnectionState getConnectionState() {
        return this.connectionState;
    }

    @Override
    public UUID getConnectionId() {
        return this.connectionId;
    }

    @Override
    public String toString() {
        return "CryptoBrokerCommunitySubAppModuleInformation{" +
                "publicKey='" + publicKey + '\'' +
                ", alias='" + alias + '\'' +
                ", connectionState='" + connectionState + '\'' +
                ", connectionId='" + connectionId + '\'' +
                ", image=" + (image != null) +
                '}';
    }
}
