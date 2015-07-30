package com.bitdubai.fermat_dmp_plugin.layer.module.wallet_store.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.enums.NicheWallet;
import com.bitdubai.fermat_api.layer.all_definition.enums.WalletCategory;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_store.enums.CatalogItems;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_store.enums.InstallationStatus;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_store.exceptions.CantGetItemInformationException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_store.interfaces.DealsWithWalletStoreMiddleware;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_store.interfaces.WalletStoreManager;
import com.bitdubai.fermat_api.layer.dmp_module.wallet_store.exceptions.CantGetRefinedCatalogException;
import com.bitdubai.fermat_api.layer.dmp_module.wallet_store.exceptions.CantGetWalletsFromCatalogueException;
import com.bitdubai.fermat_api.layer.dmp_module.wallet_store.exceptions.CantStartInstallationException;
import com.bitdubai.fermat_api.layer.dmp_module.wallet_store.exceptions.CantStartLanguageInstallationException;
import com.bitdubai.fermat_api.layer.dmp_module.wallet_store.exceptions.CantStartSkinInstallationException;
import com.bitdubai.fermat_api.layer.dmp_module.wallet_store.exceptions.CantStartUninstallLanguageException;
import com.bitdubai.fermat_api.layer.dmp_module.wallet_store.exceptions.CantStartUninstallSkinException;
import com.bitdubai.fermat_api.layer.dmp_module.wallet_store.exceptions.CantStartUninstallWalletException;
import com.bitdubai.fermat_api.layer.dmp_module.wallet_store.exceptions.DatailedInformationNotFoundException;
import com.bitdubai.fermat_api.layer.dmp_module.wallet_store.interfaces.WalletCatalogueFilter;
import com.bitdubai.fermat_api.layer.dmp_module.wallet_store.interfaces.WalletStoreCatalogue;
import com.bitdubai.fermat_api.layer.dmp_module.wallet_store.interfaces.WalletStoreCatalogueItem;
import com.bitdubai.fermat_api.layer.dmp_module.wallet_store.interfaces.WalletStoreDetailedCatalogItem;
import com.bitdubai.fermat_api.layer.dmp_module.wallet_store.interfaces.WalletStoreLanguage;
import com.bitdubai.fermat_api.layer.dmp_module.wallet_store.interfaces.WalletStoreSkin;
import com.bitdubai.fermat_api.layer.dmp_network_service.wallet_store.exceptions.CantGetCatalogItemException;
import com.bitdubai.fermat_api.layer.dmp_network_service.wallet_store.exceptions.CantGetDeveloperException;
import com.bitdubai.fermat_api.layer.dmp_network_service.wallet_store.exceptions.CantGetLanguageException;
import com.bitdubai.fermat_api.layer.dmp_network_service.wallet_store.exceptions.CantGetLanguagesException;
import com.bitdubai.fermat_api.layer.dmp_network_service.wallet_store.exceptions.CantGetSkinException;
import com.bitdubai.fermat_api.layer.dmp_network_service.wallet_store.exceptions.CantGetSkinsException;
import com.bitdubai.fermat_api.layer.dmp_network_service.wallet_store.exceptions.CantGetWalletDetailsException;
import com.bitdubai.fermat_api.layer.dmp_network_service.wallet_store.exceptions.CantGetWalletIconException;
import com.bitdubai.fermat_api.layer.dmp_network_service.wallet_store.exceptions.CantGetWalletsCatalogException;
import com.bitdubai.fermat_api.layer.dmp_network_service.wallet_store.interfaces.CatalogItem;
import com.bitdubai.fermat_api.layer.dmp_network_service.wallet_store.interfaces.DealsWithWalletStoreNetworkService;
import com.bitdubai.fermat_api.layer.dmp_network_service.wallet_store.interfaces.DetailedCatalogItem;
import com.bitdubai.fermat_api.layer.dmp_network_service.wallet_store.interfaces.Developer;
import com.bitdubai.fermat_api.layer.dmp_network_service.wallet_store.interfaces.Language;
import com.bitdubai.fermat_api.layer.dmp_network_service.wallet_store.interfaces.Skin;
import com.bitdubai.fermat_api.layer.dmp_network_service.wallet_store.interfaces.WalletCatalog;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.DealsWithLogger;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogManager;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.DealsWithErrors;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.ErrorManager;

import java.util.ArrayList;
import java.util.List;
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
     * Constructor
     * @param errorManager
     * @param logManager
     * @param walletStoreManagerMiddleware
     * @param walletStoreManagerNetworkService
     */
    public WalletStoreModuleManager(ErrorManager errorManager, LogManager logManager, WalletStoreManager walletStoreManagerMiddleware, com.bitdubai.fermat_api.layer.dmp_network_service.wallet_store.interfaces.WalletStoreManager walletStoreManagerNetworkService) {
        this.errorManager = errorManager;
        this.logManager = logManager;
        this.walletStoreManagerMiddleware = walletStoreManagerMiddleware;
        this.walletStoreManagerNetworkService = walletStoreManagerNetworkService;
    }

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
        try {
            final List<WalletStoreCatalogueItem> walletStoreCatalogueItemList = new ArrayList<WalletStoreCatalogueItem>();
            WalletCatalog walletCatalog = walletStoreManagerNetworkService.getWalletCatalogue();
            for (CatalogItem catalogItem : walletCatalog.getWalletCatalog(0, 0)){
                InstallationStatus installationStatus = getInstallationStatus(catalogItem);
                WalletStoreCatalogueItem walletStoreCatalogueItem = getWalletCatalogueItem(catalogItem, installationStatus);
                walletStoreCatalogueItemList.add(walletStoreCatalogueItem);
            }

            WalletStoreCatalogue walletStoreCatalogue = new WalletStoreCatalogue() {
                @Override
                public List<WalletStoreCatalogueItem> getWalletCatalogue(int offset, int top) throws CantGetWalletsFromCatalogueException {
                    return walletStoreCatalogueItemList;
                }

                @Override
                public void addFilter(WalletCatalogueFilter walletFilter) {

                }

                @Override
                public void clearFilters() {

                }
            };

            return walletStoreCatalogue;
        } catch (Exception exception) {
            throw new CantGetRefinedCatalogException(CantGetRefinedCatalogException.DEFAULT_MESSAGE, exception, null, null);
        }

    }

    private InstallationStatus getInstallationStatus(CatalogItem catalogItem) throws CantGetItemInformationException {
        return walletStoreManagerMiddleware.getInstallationStatus(CatalogItems.WALLET, catalogItem.getId());
    }

    private WalletStoreCatalogueItem getWalletCatalogueItem(final CatalogItem catalogItem, final InstallationStatus installationStatus){
        WalletStoreCatalogueItem walletStoreCatalogueItem = new WalletStoreCatalogueItem() {
            @Override
            public InstallationStatus getInstallationStatus() {
                return installationStatus;
            }

            @Override
            public WalletStoreDetailedCatalogItem getWalletDetailedCatalogItem() throws DatailedInformationNotFoundException {
                return null;
            }

            @Override
            public UUID getId() {
                return catalogItem.getId();
            }

            @Override
            public String getName() {
                return catalogItem.getName();
            }

            @Override
            public WalletCategory getCategory() {
                return catalogItem.getCategory();
            }

            @Override
            public String getDescription() {
                return catalogItem.getDescription();
            }

            @Override
            public int getDefaultSizeInBytes() {
                return catalogItem.getDefaultSizeInBytes();
            }

            @Override
            public byte[] getIcon() throws CantGetWalletIconException {
                return catalogItem.getIcon();
            }

            @Override
            public DetailedCatalogItem getDetailedCatalogItem() throws CantGetWalletDetailsException {
                return catalogItem.getDetailedCatalogItem();
            }
        };

        return walletStoreCatalogueItem;
    }


    public void installLanguage(UUID walletCatalogueId, UUID languageId) throws CantStartLanguageInstallationException {

    }


    public void installSkin(UUID walletCatalogueId, UUID skinId) throws CantStartSkinInstallationException {

    }


    public void installWallet(WalletCategory walletCategory, NicheWallet nicheWallet, UUID skinId, UUID languageId, UUID walletCatalogueId, Version version) throws CantStartInstallationException {

    }


    public void uninstallLanguage(UUID walletCatalogueId, UUID languageId) throws CantStartUninstallLanguageException {

    }


    public void uninstallSkin(UUID walletCatalogueId, UUID skinId) throws CantStartUninstallSkinException {

    }


    public void uninstallWallet(UUID walletCatalogueId) throws CantStartUninstallWalletException {

    }


    /**
     * Gets the WalletStoreDetailed CAtalogItem object for the passes wallet
     * @param walletCatalogId
     * @return
     * @throws CantGetWalletsCatalogException
     */
    public WalletStoreDetailedCatalogItem getCatalogItemDetails(UUID walletCatalogId) throws CantGetWalletsCatalogException {
        try {
            return getWalletStoreDetailedCatalogItem(walletStoreManagerNetworkService.getDetailedCatalogItem(walletCatalogId));
        } catch (Exception exception) {
            throw new CantGetWalletsCatalogException(CantGetWalletsCatalogException.DEFAULT_MESSAGE, exception, null, null);
        }
    }

    private WalletStoreDetailedCatalogItem getWalletStoreDetailedCatalogItem (final DetailedCatalogItem detailedCatalogItem) throws CantGetDeveloperException {

        WalletStoreDetailedCatalogItem walletStoreDetailedCatalogItem = new WalletStoreDetailedCatalogItem() {
            @Override
            public String getDeveloperName() throws CantGetDeveloperException {
                return getDeveloper(detailedCatalogItem.getDeveloperId()).getName();
            }

            @Override
            public String getDeveloperPublicKey() throws CantGetDeveloperException {
                return getDeveloper(detailedCatalogItem.getDeveloperId()).getPublicKey();
            }

            @Override
            public WalletStoreSkin getSkin(UUID skinId) {
                //todo complete
                return null;
            }

            @Override
            public WalletStoreLanguage getLanguage(UUID languageId) {
                //todo complete
                return null;
            }

            @Override
            public Language getDefaultLanguage() throws CantGetLanguageException {
                return detailedCatalogItem.getDefaultLanguage();
            }

            @Override
            public List<Language> getLanguages() throws CantGetLanguagesException {
                return detailedCatalogItem.getLanguages();
            }

            @Override
            public Skin getDefaultSkin() throws CantGetSkinException {
                return detailedCatalogItem.getDefaultSkin();
            }

            @Override
            public List<Skin> getSkins() throws CantGetSkinsException {
                return detailedCatalogItem.getSkins();
            }

            @Override
            public Version getVersion() {
                return detailedCatalogItem.getVersion();
            }

            @Override
            public Version getPlatformInitialVersion() {
                return detailedCatalogItem.getPlatformInitialVersion();
            }

            @Override
            public Version getPlatformFinalVersion() {
                return detailedCatalogItem.getPlatformFinalVersion();
            }

            @Override
            public UUID getDeveloperId() {
                return detailedCatalogItem.getDeveloperId();
            }
        };

        return walletStoreDetailedCatalogItem;
    }

    private Developer getDeveloper (UUID developerId) throws CantGetDeveloperException {
        return walletStoreManagerNetworkService.getDeveloper(developerId);
    }
}
