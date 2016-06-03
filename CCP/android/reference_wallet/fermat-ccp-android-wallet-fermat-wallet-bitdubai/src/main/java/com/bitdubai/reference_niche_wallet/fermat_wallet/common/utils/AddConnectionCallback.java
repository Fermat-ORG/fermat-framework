package com.bitdubai.reference_niche_wallet.fermat_wallet.common.utils;

import com.bitdubai.fermat_ccp_api.layer.wallet_module.fermat_wallet.interfaces.FermatWalletIntraUserActor;

/**
 * Created by mati on 2015.12.29..
 */
public interface AddConnectionCallback {

    void addMenuEnabled();

    void addMenuDisabled();

    void setSelected(FermatWalletIntraUserActor fermatWalletIntraUserActor,boolean selected);
}
