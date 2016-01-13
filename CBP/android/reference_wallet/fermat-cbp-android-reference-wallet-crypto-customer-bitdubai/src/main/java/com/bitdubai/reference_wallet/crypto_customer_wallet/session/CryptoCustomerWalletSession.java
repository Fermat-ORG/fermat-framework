package com.bitdubai.reference_wallet.crypto_customer_wallet.session;

import com.bitdubai.fermat_android_api.layer.definition.wallet.abstracts.AbstractFermatSession;
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.WalletSession;
import com.bitdubai.fermat_api.layer.dmp_module.wallet_manager.InstalledWallet;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.crypto_customer.interfaces.CryptoCustomerWalletModuleManager;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_settings.interfaces.WalletSettings;
import com.bitdubai.fermat_wpd_api.layer.wpd_network_service.wallet_resources.interfaces.WalletResourcesProviderManager;
import com.bitdubai.reference_wallet.crypto_customer_wallet.preference_settings.CryptoCustomerWalletPreferenceSettings;

public class CryptoCustomerWalletSession
        extends AbstractFermatSession<InstalledWallet, CryptoCustomerWalletModuleManager, WalletResourcesProviderManager>
        {

    public static final String CONTRACT_DATA = "CONTRACT_DATA";
    public static final String NEGOTIATION_DATA = "NEGOTIATION_DATA";
    public static final String CONFIGURED_DATA = "CONFIGURED_DATA";
    public static final String LOCATION_LIST = "LOCATION_LIST";
    public static final String BANK_ACCOUNT_LIST = "BANK_ACCOUNT_LIST";


    public CryptoCustomerWalletSession() {
    }

}
