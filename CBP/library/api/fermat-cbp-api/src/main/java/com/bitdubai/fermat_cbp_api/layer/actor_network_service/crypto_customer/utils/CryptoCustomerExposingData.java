package com.bitdubai.fermat_cbp_api.layer.actor_network_service.crypto_customer.utils;

import com.bitdubai.fermat_api.layer.osa_android.location_system.Location;

import java.util.Arrays;

/**
 * The interface <code>com.bitdubai.fermat_cbp_api.layer.actor_network_service.crypto_customer.utils.CryptoCustomerExposingData</code>
 * represents a crypto customer and exposes all the functionality of it.
 * <p>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 25/11/2015.
 */
public final class CryptoCustomerExposingData {

    private final String publicKey;
    private final String alias    ;
    private final byte[] image    ;
    private final Location location;
    private final long refreshInterval;
    private final long accuracy;

    public CryptoCustomerExposingData(final String publicKey,
                                      final String alias,
                                      final byte[] image,
                                      final Location location,
                                      final long refreshInterval,
                                      final long accuracy) {

        this.publicKey = publicKey;
        this.alias     = alias    ;
        this.image     = image    ;
        this.location  = location;
        this.refreshInterval = refreshInterval;
        this.accuracy = accuracy;
    }

    /**
     * @return a string representing the public key.
     */
    public final String getPublicKey() {
        return publicKey;
    }

    /**
     * @return a string representing the alias of the crypto customer.
     */
    public final String getAlias() {
        return alias;
    }

    /**
     * @return an array of bytes with the image exposed by the Crypto Customer.
     */
    public final byte[] getImage() {
        return image;
    }

    /**
     * @return an locarion  with the image exposed by the Crypto Customer.
     */
    public final Location getLocation() {
        return location;
    }

    /**
     * @return an long with interval refresh by the Crypto Customer.
     */
    public final long getRefreshInterval() {
        return refreshInterval;
    }

    /**
     * @return an long with accuracy refresh by the Crypto Customer.
     */
    public final long getAccuracy() {
        return accuracy;
    }

    @Override
    public String toString() {
        return "CryptoCustomerExposingData{" +
                "publicKey='" + publicKey + '\'' +
                ", alias='" + alias + '\'' +
                ", image=" + Arrays.toString(image) +
                '}';
    }

}
