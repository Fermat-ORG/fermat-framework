package com.bitdubai.fermat_ccp_api.layer.wallet_module.loss_protected_wallet.interfaces;

import com.bitdubai.fermat_api.layer.modules.common_classes.ActiveActorIdentityInformation;
import com.bitdubai.fermat_api.layer.modules.interfaces.ModuleManager;
import com.bitdubai.fermat_api.layer.modules.interfaces.FermatSettings;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.BitcoinWalletSettings;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.exceptions.CantGetCryptoWalletException;

/**
 * The interface <code>com.bitdubai.fermat_dmp_plugin.layer.wallet_module.crypto_wallet.interfaces.CryptoWallet</code>
 * haves all consumable methods from the plugin
 *
 * Created by Natalia on 2016.03.04.
 * @version 1.0
 */
public interface LossProtectedWalletManager extends ModuleManager<BitcoinWalletSettings, ActiveActorIdentityInformation> {

    LossProtectedWallet getCryptoWallet() throws CantGetCryptoWalletException;

}
