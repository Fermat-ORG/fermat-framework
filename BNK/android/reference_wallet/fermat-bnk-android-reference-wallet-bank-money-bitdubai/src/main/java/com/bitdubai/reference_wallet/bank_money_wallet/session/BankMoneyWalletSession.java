package com.bitdubai.reference_wallet.bank_money_wallet.session;

import com.bitdubai.fermat_android_api.layer.definition.wallet.abstracts.AbstractFermatSession;
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.WalletSession;
import com.bitdubai.fermat_api.layer.dmp_module.wallet_manager.InstalledWallet;
import com.bitdubai.fermat_bnk_api.layer.bnk_wallet_module.interfaces.BankMoneyWalletModuleManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_settings.interfaces.WalletSettings;
import com.bitdubai.fermat_wpd_api.layer.wpd_network_service.wallet_resources.interfaces.WalletResourcesProviderManager;
import com.bitdubai.reference_wallet.bank_money_wallet.preference_settings.BankMoneyWalletPreferenceSettings;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by memo on 04/12/15.
 */
public class BankMoneyWalletSession extends AbstractFermatSession<InstalledWallet,BankMoneyWalletModuleManager,WalletResourcesProviderManager> implements WalletSession {
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
    private BankMoneyWalletModuleManager moduleManager;

    public BankMoneyWalletSession(InstalledWallet wallet, ErrorManager errorManager, WalletResourcesProviderManager resourcesProviderManager, BankMoneyWalletModuleManager moduleManager) {
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
        return new BankMoneyWalletPreferenceSettings();
    }

    /**
     * Return the Wallet Store Module
     *
     * @return reference to the Wallet Store Module
     */
    public BankMoneyWalletModuleManager getModuleManager() {
        return moduleManager;
    }

    public WalletResourcesProviderManager getResourcesProviderManager() {
        return resourcesProviderManager;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BankMoneyWalletSession that = (BankMoneyWalletSession) o;

        return wallet == that.wallet;
    }

    @Override
    public int hashCode() {
        return wallet.hashCode();
    }
}
