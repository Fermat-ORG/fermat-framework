package com.bitdubai.reference_niche_wallet.bitcoin_wallet.common.utils;

import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.interfaces.CryptoWalletIntraUserActor;

/**
 * Created by mati on 2015.12.29..
 */
public interface AddConnectionCallback {

    void addMenuEnabled();

    void addMenuDisabled();

    void setSelected(CryptoWalletIntraUserActor cryptoWalletIntraUserActor,boolean selected);
}
