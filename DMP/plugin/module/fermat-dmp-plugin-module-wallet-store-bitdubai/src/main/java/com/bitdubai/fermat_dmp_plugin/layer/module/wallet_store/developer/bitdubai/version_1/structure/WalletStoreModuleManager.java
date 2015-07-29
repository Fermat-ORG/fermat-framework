package com.bitdubai.fermat_dmp_plugin.layer.module.wallet_store.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.enums.NicheWallet;
import com.bitdubai.fermat_api.layer.all_definition.enums.WalletCategory;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_store.interfaces.DealsWithWalletStoreMiddleware;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_store.interfaces.WalletStoreManager;
import com.bitdubai.fermat_api.layer.dmp_module.wallet_store.exceptions.CantGetRefinedCatalogException;
import com.bitdubai.fermat_api.layer.dmp_module.wallet_store.exceptions.CantStartInstallationException;
import com.bitdubai.fermat_api.layer.dmp_module.wallet_store.exceptions.CantStartLanguageInstallationException;
import com.bitdubai.fermat_api.layer.dmp_module.wallet_store.exceptions.CantStartSkinInstallationException;
import com.bitdubai.fermat_api.layer.dmp_module.wallet_store.exceptions.CantStartUninstallLanguageException;
import com.bitdubai.fermat_api.layer.dmp_module.wallet_store.exceptions.CantStartUninstallSkinException;
import com.bitdubai.fermat_api.layer.dmp_module.wallet_store.exceptions.CantStartUninstallWalletException;
import com.bitdubai.fermat_api.layer.dmp_module.wallet_store.interfaces.WalletStoreCatalogue;
import com.bitdubai.fermat_api.layer.dmp_network_service.wallet_store.interfaces.DealsWithWalletStoreNetworkService;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.DealsWithLogger;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogManager;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.DealsWithErrors;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.ErrorManager;

import java.util.UUID;

/**
 * Created by rodrigo on 7/29/15.
 */
public class WalletStoreModuleManager implements DealsWithErrors, DealsWithLogger, DealsWithWalletStoreMiddleware, DealsWithWalletStoreNetworkService{

    /**
     * DealsWithErrors interface member variables
     */
    ErrorManager errorManager;

    /**
     * DealsWithjLogger interface member variable
     */
    LogManager logManager;


    /**
     * DealsWithWalletStoreMiddleware interface member variable
     */
    WalletStoreManager walletStoreManagerMiddleware;

    /**
     * DealsWithWalletStoreNetworkService interface member variable
     */
    com.bitdubai.fermat_api.layer.dmp_network_service.wallet_store.interfaces.WalletStoreManager walletStoreManagerNetworkService;

    /**
     * DealsWithErrors interface implementation
     */
    @Override
    public void setErrorManager(ErrorManager errorManager) {
        this.errorManager = errorManager;
    }

    /**
     * DealsWithLogManager interface implementation
     */
    @Override
    public void setLogManager(LogManager logManager) {
        this.logManager = logManager;
    }

    /**
     * DealsWithWalletStoreMiddleware interface implementation
     */
    @Override
    public void setWalletStoreManager(WalletStoreManager walletStoreManager) {
        this.walletStoreManagerMiddleware = walletStoreManager;
    }


    /**
     * DealsWithWalletStoreNetworkService interface implementation
     */
    @Override
    public void setWalletStoreManager(com.bitdubai.fermat_api.layer.dmp_network_service.wallet_store.interfaces.WalletStoreManager walletStoreManager) {
        this.walletStoreManagerNetworkService = walletStoreManager;
    }


    public WalletStoreCatalogue getCatalogue() throws CantGetRefinedCatalogException {
        return null;
    }


    public void installLanguage(UUID walletCatalogueId, UUID languageId) throws CantStartLanguageInstallationException {

    }


    public void installSkin(UUID walletCatalogueId, UUID skinId) throws CantStartSkinInstallationException {

    }


    public void installWallet(WalletCategory walletCategory, NicheWallet nicheWallet, UUID skinId, UUID languageId, UUID walletCatalogueId, String version) throws CantStartInstallationException {

    }


    public void uninstallLanguage(UUID walletCatalogueId, UUID languageId) throws CantStartUninstallLanguageException {

    }


    public void uninstallSkin(UUID walletCatalogueId, UUID skinId) throws CantStartUninstallSkinException {

    }


    public void uninstallWallet(UUID walletCatalogueId) throws CantStartUninstallWalletException {

    }
}
