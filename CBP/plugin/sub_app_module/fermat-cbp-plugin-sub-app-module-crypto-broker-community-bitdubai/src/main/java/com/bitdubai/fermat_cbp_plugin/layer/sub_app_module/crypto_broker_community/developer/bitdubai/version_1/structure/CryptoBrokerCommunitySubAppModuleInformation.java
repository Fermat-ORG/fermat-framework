package com.bitdubai.fermat_cbp_plugin.layer.sub_app_module.crypto_broker_community.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_cbp_api.layer.actor_connection.crypto_broker.utils.CryptoBrokerActorConnection;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_broker_community.interfaces.CryptoBrokerCommunityInformation;

import java.util.List;

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

    public CryptoBrokerCommunitySubAppModuleInformation(final String publicKey,
                                                        final String alias,
                                                        final byte[] image) {

        this.publicKey = publicKey;
        this.alias     = alias    ;
        this.image     = image    ;
    }

    public CryptoBrokerCommunitySubAppModuleInformation(final CryptoBrokerActorConnection actorConnection) {

        this.publicKey = actorConnection.getPublicKey();
        this.alias     = actorConnection.getAlias()    ;
        this.image     = actorConnection.getImage()    ;
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
}
