package com.bitdubai.reference_niche_wallet.loss_protected_wallet.common.utils;

import com.bitdubai.fermat_ccp_api.layer.wallet_module.loss_protected_wallet.interfaces.LossProtectedWalletIntraUserActor;

/**
 * Created by mati on 2015.12.29..
 */
public interface AddConnectionCallback {

    void addMenuEnabled();

    void addMenuDisabled();

    void setSelected(LossProtectedWalletIntraUserActor cryptoWalletIntraUserActor,boolean selected);
}
