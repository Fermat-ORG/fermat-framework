package com.bitdubai.reference_wallet.crypto_customer_wallet.session;

import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.WalletSession;
import com.bitdubai.fermat_cbp_api.layer.cbp_wallet_module.crypto_customer.interfaces.CryptoCustomerWalletModuleManager;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_settings.interfaces.WalletSettings;
import com.bitdubai.fermat_api.layer.dmp_module.wallet_manager.InstalledWallet;
import com.bitdubai.fermat_wpd_api.layer.wpd_network_service.wallet_resources.interfaces.WalletResourcesProviderManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.ErrorManager;
import com.bitdubai.reference_wallet.crypto_customer_wallet.preference_settings.CryptoCustomerWalletPreferenceSettings;

import java.util.HashMap;
import java.util.Map;

public class CryptoCustomerWalletSession implements WalletSession {

    private final WalletResourcesProviderManager providerManager;
    /**
     * SubApps type
     */
    private final InstalledWallet wallet;

    /**
     * Active objects in wallet session
     */
    private final Map<String, Object> data;

    /**
     * Error manager
     */
    private final ErrorManager errorManager;

    /**
     * Crypto Customer Wallet Module
     */
    private final CryptoCustomerWalletModuleManager moduleManager;




    public CryptoCustomerWalletSession(InstalledWallet wallet, ErrorManager errorManager, WalletResourcesProviderManager providerManager, CryptoCustomerWalletModuleManager moduleManager) {
        this.wallet = wallet;
        data = new HashMap<String, Object>();
        this.errorManager = errorManager;
        this.moduleManager = moduleManager;
        this.providerManager = providerManager;
    }


    @Override
    public InstalledWallet getWalletSessionType() {
        return null;
    }

    /**
     * Store any data you need to hold between the fragments of the sub app
     *
     * @param key    key to reference the object
     * @param object the object yo want to store
     */
    @Override
    public void setData(String key, Object object) {
        data.put(key, object);
    }

    /**
     * Return the data referenced by the key
     *
     * @param key the key to access de data
     * @return the data you want
     */
    @Override
    public Object getData(String key) {
        return data.get(key);
    }

    /**
     * Return the Error Manager
     *
     * @return reference to the Error Manager
     */
    @Override
    public ErrorManager getErrorManager() {
        return errorManager;
    }

    @Override
    public WalletResourcesProviderManager getWalletResourcesProviderManager() {
        return providerManager;
    }

    @Override
    public WalletSettings getWalletSettings() {
        return new CryptoCustomerWalletPreferenceSettings();
    }

    /**
     * Return the Wallet Store Module
     *
     * @return reference to the Wallet Store Module
     */
    public CryptoCustomerWalletModuleManager getModuleManager() {
        return moduleManager;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CryptoCustomerWalletSession that = (CryptoCustomerWalletSession) o;

        return wallet == that.wallet;

    }

    @Override
    public int hashCode() {
        return wallet.hashCode();
    }
}
