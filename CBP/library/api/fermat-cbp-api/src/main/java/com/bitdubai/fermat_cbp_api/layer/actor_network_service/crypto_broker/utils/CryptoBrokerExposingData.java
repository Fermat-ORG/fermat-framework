package com.bitdubai.fermat_cbp_api.layer.actor_network_service.crypto_broker.utils;

import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.enums.FiatCurrency;
import com.bitdubai.fermat_api.layer.osa_android.location_system.Location;
import com.bitdubai.fermat_cbp_api.layer.actor_network_service.common.AbstractCBPActorExposingData;
import com.bitdubai.fermat_cbp_api.layer.identity.crypto_broker.interfaces.CryptoBrokerIdentity;
import com.bitdubai.fermat_cbp_api.layer.identity.crypto_broker.interfaces.CryptoBrokerIdentityExtraData;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.enums.ProfileStatus;

/**
 * The interface <code>com.bitdubai.fermat_cbp_api.layer.actor_network_service.crypto_broker.utils.CryptoBrokerExposingData</code>
 * represents a crypto broker and exposes all the functionality of it.
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 17/11/2015.
 * Updated by Manuel Perez on (darkpriestrelative@gmail.com) on 05/07/16.
 */
public final class CryptoBrokerExposingData extends AbstractCBPActorExposingData {

    private final CryptoBrokerIdentityExtraData cryptoBrokerIdentityExtraData;

    /**
     * Default constructor with parameters
     *
     * @param publicKey
     * @param alias
     * @param image
     * @param location
     * @param refreshInterval
     * @param accuracy
     */
    public CryptoBrokerExposingData(
            String publicKey,
            String alias,
            byte[] image,
            Location location,
            long refreshInterval,
            long accuracy,
            ProfileStatus profileStatus) {
        super(
                publicKey,
                alias,
                image,
                location,
                refreshInterval,
                accuracy, profileStatus);
        //Default CryptoBrokerIdentityExtraData
        this.cryptoBrokerIdentityExtraData = new CryptoBrokerIdentityExtraData(
                CryptoCurrency.BITCOIN,
                FiatCurrency.US_DOLLAR,
                "No Available Merchandises.");
    }

    /**
     * Constructor including CryptoBrokerIdentityExtraData as parameter.
     *
     * @param publicKey
     * @param alias
     * @param image
     * @param location
     * @param refreshInterval
     * @param accuracy
     * @param profileStatus
     * @param cryptoBrokerIdentityExtraData
     */
    public CryptoBrokerExposingData(
            String publicKey,
            String alias,
            byte[] image,
            Location location,
            long refreshInterval,
            long accuracy,
            ProfileStatus profileStatus,
            CryptoBrokerIdentityExtraData cryptoBrokerIdentityExtraData) {
        super(
                publicKey,
                alias,
                image,
                location,
                refreshInterval,
                accuracy,
                profileStatus);
        this.cryptoBrokerIdentityExtraData = cryptoBrokerIdentityExtraData;
    }

    public CryptoBrokerExposingData(
            CryptoBrokerIdentity cryptoBrokerIdentity,
            Location location,
            ProfileStatus profileStatus) {
        super(
                cryptoBrokerIdentity.getPublicKey(),
                cryptoBrokerIdentity.getAlias(),
                cryptoBrokerIdentity.getProfileImage(),
                location, cryptoBrokerIdentity.getFrequency().getRefreshInterval(),
                cryptoBrokerIdentity.getAccuracy(),
                profileStatus);
        this.cryptoBrokerIdentityExtraData =
                cryptoBrokerIdentity.getCryptoBrokerIdentityExtraData();
    }

    public CryptoBrokerIdentityExtraData getCryptoBrokerIdentityExtraData() {
        return this.cryptoBrokerIdentityExtraData;
    }
}
