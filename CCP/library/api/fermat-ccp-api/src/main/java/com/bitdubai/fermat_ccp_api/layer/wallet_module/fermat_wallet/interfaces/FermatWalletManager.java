package com.bitdubai.fermat_ccp_api.layer.wallet_module.fermat_wallet.interfaces;

import com.bitdubai.fermat_api.layer.modules.common_classes.ActiveActorIdentityInformation;
import com.bitdubai.fermat_api.layer.modules.interfaces.ModuleManager;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.fermat_wallet.FermatWalletSettings;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.fermat_wallet.exceptions.CantGetFermatWalletException;

/**
 * The interface <code>com.bitdubai.fermat_dmp_plugin.layer.wallet_module.crypto_wallet.interfaces.FermatWallet</code>
 * haves all consumable methods from the plugin
 *
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 10/06/15.
 * @version 1.0
 */
public interface FermatWalletManager extends ModuleManager<FermatWalletSettings, ActiveActorIdentityInformation> {

    FermatWallet getCryptoWallet() throws CantGetFermatWalletException;

}
