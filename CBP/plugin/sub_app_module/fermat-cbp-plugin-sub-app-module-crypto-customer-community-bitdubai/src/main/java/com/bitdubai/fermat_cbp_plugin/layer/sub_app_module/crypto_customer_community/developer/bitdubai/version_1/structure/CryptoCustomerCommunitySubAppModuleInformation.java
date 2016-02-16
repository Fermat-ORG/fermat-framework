package com.bitdubai.fermat_cbp_plugin.layer.sub_app_module.crypto_customer_community.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.actor_connection.common.enums.ConnectionState;
import com.bitdubai.fermat_cbp_api.layer.actor_connection.crypto_customer.utils.CryptoCustomerActorConnection;
import com.bitdubai.fermat_cbp_api.layer.actor_network_service.crypto_customer.utils.CryptoCustomerExposingData;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_customer_community.interfaces.CryptoCustomerCommunityInformation;

import java.util.List;

/**
 * Created by Alejandro Bicelis on 2/2/2016.
 */

public class CryptoCustomerCommunitySubAppModuleInformation implements CryptoCustomerCommunityInformation {

    private final String publicKey;
    private final String alias    ;
    private final byte[] image    ;
    private final ConnectionState connectionState;

    public CryptoCustomerCommunitySubAppModuleInformation(final String publicKey,
                                                          final String alias,
                                                          final byte[] image) {

        this.publicKey = publicKey;
        this.alias     = alias    ;
        this.image     = image    ;
        this.connectionState = null;
    }

    public CryptoCustomerCommunitySubAppModuleInformation(final String publicKey,
                                                          final String alias,
                                                          final byte[] image,
                                                          final ConnectionState connectionState) {

        this.publicKey          = publicKey      ;
        this.alias              = alias          ;
        this.image              = image          ;
        this.connectionState    = connectionState;
    }

    public CryptoCustomerCommunitySubAppModuleInformation(final CryptoCustomerActorConnection actorConnection) {

        this.publicKey = actorConnection.getPublicKey();
        this.alias     = actorConnection.getAlias()    ;
        this.image     = actorConnection.getImage()    ;
        this.connectionState = null;

    }

    public CryptoCustomerCommunitySubAppModuleInformation(final CryptoCustomerExposingData exposingData) {

        this.publicKey = exposingData.getPublicKey();
        this.alias     = exposingData.getAlias()    ;
        this.image     = exposingData.getImage()    ;
        this.connectionState = null;

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
    public List listCryptoCustomerWallets() {
        return null;
    }

    @Override
    public ConnectionState getConnectionState() {
        return this.connectionState;
    }

    @Override
    public String toString() {
        return "CryptoCustomerCommunitySubAppModuleInformation{" +
                "publicKey='" + publicKey + '\'' +
                ", alias='" + alias + '\'' +
                ", connectionState='" + connectionState + '\'' +
                ", image=" + (image != null) +
                '}';
    }
}
