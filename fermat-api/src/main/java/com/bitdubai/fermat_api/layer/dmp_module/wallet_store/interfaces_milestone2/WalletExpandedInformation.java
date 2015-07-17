package com.bitdubai.fermat_api.layer.dmp_module.wallet_store.interfaces_milestone2;

import com.bitdubai.fermat_api.layer.dmp_network_service.wallet_store.interfaces.Skin;

import java.util.List;

/**
 * Created by eze on 2015.07.16..
 */
public interface WalletExpandedInformation {

    public List<Skin> getSkins();
    public List<String> getAvailableLanguages();
}
