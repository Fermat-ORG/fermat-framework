package com.bitdubai.fermat_ccp_api.layer.wallet_module.fermat_wallet.interfaces;

import com.bitdubai.fermat_api.layer.modules.common_classes.ActiveActorIdentityInformation;
import com.bitdubai.fermat_api.layer.modules.interfaces.ModuleManager;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.fermat_wallet.FermatWalletSettings;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.fermat_wallet.exceptions.CantGetFermatWalletException;

/**
 * The interface <code>com.bitdubai.fermat_dmp_plugin.layer.wallet_module.crypto_wallet.interfaces.FermatWallet</code>
 * haves all consumable methods from the plugin
 *
 * Created by  10/06/16.
 * @version 1.0
 */
public interface FermatWalletModuleManager extends ModuleManager<FermatWalletSettings, ActiveActorIdentityInformation> {

    FermatWallet getCryptoWallet() throws CantGetFermatWalletException;

}
