package com.bitdubai.fermat_api.layer.dmp_middleware.wallet_publisher.interfaces;

import java.util.UUID;

/**
 * The interface WalletPublishedInformation gives us the static information about a published wallet.
 *
 * @author Ezequiel Postan (ezequiel.postan@gmail.com)
 */
public interface WalletPublishedInformation {

    /**
     * This method gives us the version of the represented wallet
     *
     * @return the wallet version represented as a String
     */
    public String getVersion();

    /**
     * This method gives us the id of the wallet version published in the catalogue
     *
     * @return the identifier of the version of the wallet used in the wallet catalogue of the wallet store
     */
    public UUID getId();

    /**
     * This method gives us the timestamp of the publication of the first version of the wallet
     *
     * @return the timestamp represented as the result of System.currentTimeMillis()
     */
    public long getPublicationTimestamp();

    /**
     * This method gives us the timestamp of the publication of the version of wallet
     *
     * @return the timestamp represented as the result of System.currentTimeMillis()
     */
    public long getVersionTimestanp();
}
