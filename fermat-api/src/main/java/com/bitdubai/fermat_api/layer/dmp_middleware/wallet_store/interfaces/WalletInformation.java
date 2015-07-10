package com.bitdubai.fermat_api.layer.dmp_middleware.wallet_store.interfaces;

import com.bitdubai.fermat_api.layer.dmp_network_service.wallet_store.interfaces.WalletPublicationInformation;

import java.util.UUID;

/**
 * Created by eze on 2015.07.09..
 */
public interface WalletInformation extends WalletPublicationInformation {

    public long getNumberOfDownloads();

    public boolean isInstalled();

}
