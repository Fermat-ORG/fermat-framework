package com.bitdubai.fermat_cbp_api.layer.wallet_module.crypto_broker.interfaces;

import com.bitdubai.fermat_api.layer.world.interfaces.Currency;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_manager.interfaces.InstalledWallet;

/**
 * Created by Yordin Alayn on 26.05.16.
 */
public interface CBPInstalledWallet extends InstalledWallet {

    Currency getCurrency();

    void setCurrency(Currency currency);

}
