package com.bitdubai.reference_wallet.cash_money_wallet.session;

import com.bitdubai.fermat_android_api.layer.definition.wallet.abstracts.AbstractFermatSession;
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.WalletSession;
import com.bitdubai.fermat_api.layer.dmp_module.wallet_manager.InstalledWallet;
import com.bitdubai.fermat_csh_api.layer.csh_wallet_module.interfaces.CashMoneyWalletModuleManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.interfaces.ErrorManager;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_settings.interfaces.WalletSettings;
import com.bitdubai.fermat_wpd_api.layer.wpd_network_service.wallet_resources.interfaces.WalletResourcesProviderManager;
import com.bitdubai.reference_wallet.cash_money_wallet.preference_settings.CashMoneyWalletPreferenceSettings;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Alejandro Bicelis on 12/9/2015.
 */
public class CashMoneyWalletSession extends AbstractFermatSession<InstalledWallet,CashMoneyWalletModuleManager, WalletResourcesProviderManager>  {

    public CashMoneyWalletSession() {}

}
