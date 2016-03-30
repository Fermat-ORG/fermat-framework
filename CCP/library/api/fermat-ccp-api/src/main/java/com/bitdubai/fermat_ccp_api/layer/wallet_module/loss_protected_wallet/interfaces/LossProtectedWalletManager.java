package com.bitdubai.fermat_ccp_api.layer.wallet_module.loss_protected_wallet.interfaces;

import com.bitdubai.fermat_api.layer.modules.common_classes.ActiveActorIdentityInformation;
import com.bitdubai.fermat_api.layer.modules.interfaces.ModuleManager;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.loss_protected_wallet.LossProtectedWalletSettings;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.loss_protected_wallet.exceptions.CantGetCryptoLossProtectedWalletException;

import java.util.UUID;


/**
 * The interface <code>com.bitdubai.fermat_dmp_plugin.layer.wallet_module.crypto_wallet.interfaces.CryptoWallet</code>
 * haves all consumable methods from the plugin
 *
 * Created by Natalia on 2016.03.04.
 * @version 1.0
 */
public interface LossProtectedWalletManager extends ModuleManager<LossProtectedWalletSettings, ActiveActorIdentityInformation> {

    LossProtectedWallet getCryptoWallet() throws CantGetCryptoLossProtectedWalletException;



}
