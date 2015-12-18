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
public class CashMoneyWalletSession extends AbstractFermatSession<InstalledWallet,CashMoneyWalletModuleManager,WalletResourcesProviderManager> implements WalletSession {
    InstalledWallet wallet;

    /**
     * Active objects in wallet session
     */
    Map<String, Object> data;

    /**
     * Wallet Resources
     */
    private WalletResourcesProviderManager resourcesProviderManager;

    /**
     * Error manager
     */
    private ErrorManager errorManager;

    /**
     * Wallet Store Module
     */
    private CashMoneyWalletModuleManager moduleManager;

    public CashMoneyWalletSession() {}

    public CashMoneyWalletSession(InstalledWallet wallet, ErrorManager errorManager, WalletResourcesProviderManager resourcesProviderManager, CashMoneyWalletModuleManager moduleManager) {
        super(wallet.getWalletPublicKey(), wallet, errorManager, moduleManager, resourcesProviderManager);
        this.wallet = wallet;
        data = new HashMap<>();
        this.resourcesProviderManager = resourcesProviderManager;
        this.errorManager = errorManager;
        this.moduleManager = moduleManager;
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

    @Override
    public String getIdentityConnection() {
        return null;
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
    public WalletSettings getWalletSettings() {
        return new CashMoneyWalletPreferenceSettings();
    }

    /**
     * Return the Wallet Store Module
     *
     * @return reference to the Wallet Store Module
     */
    public CashMoneyWalletModuleManager getModuleManager() {
        return moduleManager;
    }

    public WalletResourcesProviderManager getResourcesProviderManager() {
        return resourcesProviderManager;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CashMoneyWalletSession that = (CashMoneyWalletSession) o;

        return wallet == that.wallet;
    }

    @Override
    public int hashCode() {
        return wallet.hashCode();
    }
}
