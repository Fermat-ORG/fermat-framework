package com.bitdubai.fermat_api.layer.dmp_module.wallet_publisher.interfaces;

/**
 * Created by eze on 2015.07.15..
 */
public interface PublishedWallet {

    /**
     * This method gives us the version of the represented wallet
     *
     * @return the wallet version represented as a String
     */
    public String getVersion();

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

    public int getNumberOfDownloads();


}
