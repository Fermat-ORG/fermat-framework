package org.fermat.fermat_dap_api.layer.all_definition.network_service_message.content_message;

import com.bitdubai.fermat_bch_api.layer.crypto_vault.watch_only_vault.ExtendedPublicKey;

import org.fermat.fermat_dap_api.layer.all_definition.enums.DAPMessageType;

/**
 * Created by Nerio on 26/11/15.
 */
public class AssetExtendedPublicKeyContentMessage implements DAPContentMessage {
    ExtendedPublicKey extendedPublicKey;
    String actorPublicKey;

    /**
     * Default constructor
     */
    public AssetExtendedPublicKeyContentMessage() {
    }

    /**
     * overloaded constructor
     * @param extendedPublicKey
     */
    public AssetExtendedPublicKeyContentMessage(ExtendedPublicKey extendedPublicKey, String actorPublicKey) {
        this.extendedPublicKey = extendedPublicKey;
        this.actorPublicKey = actorPublicKey;
    }

    /**
     * Gets the Extended Public Key
     * @return
     */
    public ExtendedPublicKey getExtendedPublicKey() {
        return extendedPublicKey;
    }

    /**
     * extended public key setter
     * @param extendedPublicKey
     */
    public void setExtendedPublicKey(ExtendedPublicKey extendedPublicKey) {
        this.extendedPublicKey = extendedPublicKey;
    }

    /**
     * gets the actor public key
     * @return
     */
    public String getActorPublicKey() {
        return actorPublicKey;
    }

    /**
     * sets the actor public key
     * @param actorPublicKey
     */
    public void setActorPublicKey(String actorPublicKey) {
        this.actorPublicKey = actorPublicKey;
    }

    /**
     * Every content message should have a unique type associate to it.
     *
     * @return {@link DAPMessageType} The message type that corresponds to this content message.
     */
    @Override
    public DAPMessageType messageType() {
        return DAPMessageType.EXTENDED_PUBLIC_KEY;
    }
}
