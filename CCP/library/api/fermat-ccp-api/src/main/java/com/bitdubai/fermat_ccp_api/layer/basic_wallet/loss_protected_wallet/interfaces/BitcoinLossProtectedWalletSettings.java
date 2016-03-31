package com.bitdubai.fermat_ccp_api.layer.basic_wallet.loss_protected_wallet.interfaces;

import com.bitdubai.fermat_api.layer.modules.interfaces.FermatSettings;

import java.util.UUID;

/**
 * Created by natalia on 29/03/16.
 */
public interface BitcoinLossProtectedWalletSettings extends FermatSettings {

    void setExchangeProvider(UUID exchangeProvider);

    UUID getExchangeProvider();
}
